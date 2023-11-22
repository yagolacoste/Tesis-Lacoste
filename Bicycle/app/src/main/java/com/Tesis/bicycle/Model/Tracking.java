package com.Tesis.bicycle.Model;

import android.location.Location;

import com.Tesis.bicycle.Constants;

import org.apache.commons.lang3.RandomStringUtils;
import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tracking implements Serializable {

    //Parametros de configuracion de filtros
    private static final long serialVersionUID = 1L;
    private static final float MAX_SPEED_THRESHOLD =8F ; //8 m/s //segun google son 30 k/h con toda la furia
    private static final float MAX_ACCURACY_THRESHOLD =16F ; //15 metros inclusive es un promedio de los valores calculados
    private static final float MIN_ALTITUDE_THRESHOLD = -450F; // 5 metros
    private static final double MAX_ALTITUDE_THRESHOLD = 5200F;

    // atributos de la ruta con estadisticas generada
    private String id;
    private String title="";
    private String description="";
    private float distance=0;
    private float avgSpeed=0;
    private float avgSpeedFromSUVAT = 0;
    private Date timeCreated=null;
    private Date timeStarted=null;
    private Date timeStopped=null;
    private long timeElapsedBetweenStartStops = 0;
    private float timeElapsedBetweenTrkPoints = 0;
    private float totalSpeedForRunningAverage = 0;
    private int totalTrkPointsWithSpeedForRunningAverage = 0;
    private long timeInMilliseconds=0;
    private boolean repeat=false;
    private Long battle;
    private boolean deviation =false;
    private Location lastValidLocation ;

    //buffer y captador de puntos
    private List<Location> buffer=new ArrayList<>();//buffer de analisis de movilidad
    private List<Location> waysPoints=new ArrayList<>();//obtener coordenadas almacendas
    private transient List<Location> points=new ArrayList<>();//coordendas para el postprocesamiento
    private transient List<GeoPoint> routeReplay=new ArrayList<>();//ruta grabada para repetir



    private List<Location> filteredPoints=new ArrayList<>();
    private List<Location> unfilteredPoints=new ArrayList<>();



    

    public Tracking() {
        this.id=RandomStringUtils.random(Constants.MAX_CARACTER_ID,true,true);
        this.battle=null;
        this.repeat=false;
    }

    public Tracking(Tracking tracking){
        id=tracking.getId();
        title=tracking.getTitle();
        description=tracking.getDescription();
        routeReplay=tracking.getRouteReplay();
        battle=tracking.getBattle();
        repeat=tracking.isRepeat();
    }

    public void addTracking(Location currentLocation) {

        unfilteredPoints.add(currentLocation);
            if (isCoordinateValid(currentLocation)) {// filtros de altitud,velocidad,altura
                filteredPoints.add(currentLocation);
                checkMobility(currentLocation);
                ////////////Repeat route////////////
//                if(isRepeat() && !isDeviation()){ //Reviso si es repetido el camino y si no se desvio
//                    this.setDeviation(this.checkingNearestNewPointAndNextPointIndex(lastValidLocation));//Revisa y setea si se devio entre el proximo punto de mi listado almacenado y el nuevo punto
////                    if (this.isDeviation() && !notificationDisplayed) {
////                        notificationDisplayed = true;
//////                        this.setDeviation(true);
////                    }
//                }

            }

    }



    private void checkMobility(Location currentLocation) {
        if(buffer.isEmpty()) {
            buffer.add(currentLocation);
           addCoordinateToHistory(currentLocation);
        }else if(!buffer.isEmpty() && buffer.size()==1){//para detectar si la nueva posicion no esta mas atras
            Location correctLocation=buffer.get(0); //p1
            float distance =correctLocation.distanceTo(currentLocation);
            if(distance>(MAX_ACCURACY_THRESHOLD/2))/// me han llegado datos con distancia mayor a 8 deje este condicion porque tengo que tomar distancias mayor a 8 a partir del punto que entra
                buffer.add(currentLocation);
        }else if(!buffer.isEmpty() && buffer.size()==2){
            Location correctLocation=buffer.get(0);
            Location problematicLocation=buffer.get(1);
            float correctDistance = correctLocation.distanceTo(currentLocation);
            float distanceToCorrectAndProblematic = correctLocation.distanceTo(problematicLocation);//Di
          if((correctDistance< distanceToCorrectAndProblematic)){
                this.buffer.remove(problematicLocation);
          }else{
                this.buffer.remove(correctLocation);//remuevo la primera
                addCoordinateToHistory(correctLocation);
                if(isRepeat() && !isDeviation()){ //Reviso si es repetido el camino y si no se desvio
                  this.setDeviation(this.checkingNearestNewPointAndNextPointIndex(correctLocation));//Revisa y setea si se devio entre el proximo punto de mi listado almacenado y el nuevo punto
                }
            }
           this.checkMobility(currentLocation);
        }

    }



    private void addCoordinateToHistory(Location currentLocation) {
        if (waysPoints.isEmpty()) {
            avgSpeed = currentLocation.getSpeed();
        } else {
            Location lastPoint = waysPoints.get(waysPoints.size() - 1);
            distance += lastPoint.distanceTo(currentLocation);
            timeElapsedBetweenTrkPoints += Math.abs(lastPoint.getTime() - currentLocation.getTime());
            avgSpeedFromSUVAT = ((distance / (float) 1000) / (getCurrentTimeMillis() / (float) 3600000));
        }
            if (currentLocation.getSpeed() != 0) {
                totalSpeedForRunningAverage += currentLocation.getSpeed();
                totalTrkPointsWithSpeedForRunningAverage += 1;
                avgSpeed = totalSpeedForRunningAverage / totalTrkPointsWithSpeedForRunningAverage;
            }
            waysPoints.add(currentLocation);
            this.lastValidLocation = currentLocation;
    }

    private boolean isCoordinateValid(Location currentLocation) {
        if(waysPoints.isEmpty()){
            return true;
        }

        if((float)currentLocation.getAltitude()<MIN_ALTITUDE_THRESHOLD || (float)currentLocation.getAltitude()>MAX_ALTITUDE_THRESHOLD)
            return false;

        if (currentLocation.getSpeed() >  MAX_SPEED_THRESHOLD)
            return false;

        if(currentLocation.getAccuracy()>MAX_ACCURACY_THRESHOLD)
            return false;
        else
            return isDistanceFilterValid(currentLocation);
    }

    private boolean isDistanceFilterValid(Location location) {
            float distance = lastValidLocation.distanceTo(location);
                if(distance > (MAX_ACCURACY_THRESHOLD/2F))
                return true;
            return false;
    }


    public boolean checkingNearestNewPointAndNextPointIndex(Location location){
        List<Location>points=convertRouteReplay();

        int nearestIndex = findNearestPointIndex(location);
        if (nearestIndex != -1) {
            // Verificar si la posición actual se encuentra dentro del umbral de proximidad
            //double distance = calculateDistance(location, points.get(nearestIndex));
            float distance =location.distanceTo( points.get(nearestIndex));
            if (distance > 30.0F) { //Verifica si la distancia es menor a 30 metros entre la nueva localizacion y el proximo punto de proximidad
                return true; // El objeto está siguiendo la ruta correctamente
            }
        }

        return false;
    }

    private int findNearestPointIndex(Location currentLocation) {
        float minDistance = Float.MAX_VALUE;
        int nearestIndex = -1;

        for (int i = 0; i < convertRouteReplay().size(); i++) {
            float distance=currentLocation.distanceTo(convertRouteReplay().get(i));
            if (distance < minDistance) {
                minDistance = distance;
                nearestIndex = i;
            }
        }

        return nearestIndex;
    }



    public void stopTrackingActivity(){
        analyzedLastPoints();
        postProcessingPoints();
        calculateStatisticsFinished();

//        compareRoutes();
    }



    private void postProcessingPoints() {
//        ArrayList<Location> exits= new ArrayList<Location>();
        Location pivot = waysPoints.get(0);
        int i =1;
        ArrayList<Location> aux= new ArrayList<Location>();
        while (i<waysPoints.size() ) {
            Location nextPoint = waysPoints.get(i);
            float distanceBetweenPivotAndNextPoint=pivot.distanceTo(nextPoint);
            if ( distanceBetweenPivotAndNextPoint < MAX_ACCURACY_THRESHOLD){
                aux.add(nextPoint);
                i++;
            } else {
                if (aux.size()==0) {
                    points.add(pivot);
                    pivot = nextPoint;
                    i++;
                    aux.clear();
                } else {
                    //TENGO ALGO EN AUX
                    aux.add(pivot);
                    Location avg = getAveragePoints(aux);
                    pivot = avg;
                    aux.clear();
                }

            }

        }
//QUEDO EL PIVOT Y POSIBLEMENTE EL ARRAY
        aux.add(pivot);
        Location avg = getAveragePoints(aux);
        points.add(avg);
//        return exits;
    }

    private Location getAveragePoints(List<Location> averagePoints){
        if (averagePoints == null || averagePoints.isEmpty()) {
            return null;
        }

        double sumLatitud = 0;
        double sumLongitud = 0;

        for (Location location : averagePoints) {
            sumLatitud += location.getLatitude();
            sumLongitud += location.getLongitude();
        }

        double averageLatitude = sumLatitud / averagePoints.size();
        double averageLongitude = sumLongitud / averagePoints.size();

        Location location=new Location("New point");
        location.setLatitude(averageLatitude);
        location.setLongitude(averageLongitude);

        return location;
    }

    private void calculateStatisticsFinished(){
        timeStopped=new Date(System.currentTimeMillis());
        timeElapsedBetweenStartStops = timeStopped.getTime() - timeStarted.getTime();

        //Calculate the average speed based on the distance and the time between stopping and starting
        //the session
        avgSpeedFromSUVAT = distance / (timeElapsedBetweenStartStops / 1000);//m/s
        avgSpeedFromSUVAT=(avgSpeedFromSUVAT*3600)/1000;
//        avgSpeedFromSUVAT = ((distance / (float) 1000) / (getCurrentTimeMillis() / (float) 3600000));
    }

    private void analyzedLastPoints(){
        if(!buffer.isEmpty()){
            if (buffer.size()==1 && waysPoints.size()!=buffer.size())
                addCoordinateToHistory(buffer.get(0));
            else if(buffer.size() == 2){
                Location correctLocation=buffer.get(0);
                Location problematicLocation=buffer.get(1);
                float correctDistance = lastValidLocation.distanceTo(correctLocation);
                float problematicDistance= lastValidLocation.distanceTo(problematicLocation);
                addCoordinateToHistory(correctLocation);
                if((correctDistance< problematicDistance)){
                    addCoordinateToHistory(problematicLocation);
                }

            }
        }
    }


    private List<Location> convertRouteReplay() {
        List<Location> result=new ArrayList<>();
        for(GeoPoint g:getRouteReplay()){
            Location location=new Location("");
            double latitude = g.getLatitudeE6() / 1E6;
            double longitude = g.getLongitudeE6() / 1E6;
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            result.add(location);
        }
        return result;
    }


    //Revisa la distancia entre ambas rutas sea la misma o proxima con diferencia de 15 metros
    public boolean equalsBetweenNewRouteAndReplay(){
        float totalDistance =0;
        List<Location> replay=convertRouteReplay();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            totalDistance = (float) replay.stream()
                    .mapToDouble(location -> {
                        int index = replay.indexOf(location);
                        if (index < replay.size() - 1) {
                            Location next = replay.get(index + 1);
                            return location.distanceTo(next);
                        }
                        return 0.0;
                    })
                    .sum();
        }
            float result=totalDistance-distance;// no se si es necesario puede dar distinto en base a la sumatoria de desvios pequeños
        //CHARLAR CON LUIS
        if(result<30.0F && replay.get(replay.size()-1).distanceTo(points.get(points.size()-1))<MAX_ACCURACY_THRESHOLD/2) //reviso que la distancia total sea emnor 40 metros x el error de coordendas// y los ultimos puntos esten cerca entre ellos
            return true;
        return false;
    }

    public Location getLastPoint(){
        return lastValidLocation;
    }

    public boolean isCreated(){
        return !(timeCreated==null);
    }

    public void startTrackingActivity(){
        timeCreated=new Date(System.currentTimeMillis());
        timeStarted=new Date(System.currentTimeMillis());
    }

    public String getDuration() {
        return timeToString(timeElapsedBetweenStartStops);
    }


    public long getCurrentTimeMillis(){
       return (System.currentTimeMillis()-timeStarted.getTime());
    }


    public LocalTime millsToLocalTime() {
        long timeSwapBuff = 0L;
        long updateTime = timeSwapBuff + timeElapsedBetweenStartStops;
        int secs = (int) (updateTime / 1000);
        int mins = secs / 60;
        secs %= 60;
        int hrs = mins / 60;  // Cálculo corregido
        mins %= 60;
        int milliseconds = (int) timeElapsedBetweenStartStops % 1000;
        int centisecs = milliseconds / 10;
        LocalTime time = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            time = LocalTime.of(hrs, mins, secs);
        }
        return time;
    }

    public String getTimeString(){
        timeElapsedBetweenStartStops=getCurrentTimeMillis();
        return timeToString(timeElapsedBetweenStartStops);
    }

    public static String timeToString(long timeInMilliseconds) {
        long timeSwapBuff = 0L;
        long updateTime = timeSwapBuff + timeInMilliseconds;
        int secs = (int) (updateTime / 1000);
        int mins = secs / 60;
        secs %= 60;
        int hrs = mins / 60;
        mins %= 60;
        int milliseconds = (int) timeInMilliseconds % 1000;
        int centisecs = milliseconds / 10;

        if (hrs == 0) {
            if (mins == 0) {
                return String.format(Locale.US, "%d.%02ds", secs, centisecs);
            } else {
                return String.format(Locale.US, "%dm %02ds", mins, secs, centisecs);
            }
        }
        return String.format(Locale.US, "%dh %02dm", hrs, mins);
    }

    public String getDistanceString(){return distanceToString(distance,true);}

    public static String distanceToString(float distance, boolean b) {
        int metres = Math.round(distance);
        int km = metres / 1000;
        metres = metres % 1000;
        String format = "%d";//buscar

        if (km == 0) {
            if (b) {
                format += "m";
            }
            return String.format(Locale.UK, format, metres);
        }

        metres = metres/10;
        format = "%d.%02d";
        if (b) {
            format += "km";
        }
        return String.format(Locale.UK, format, km, metres);
    }

    public String getAvgSpeedString(){return String.format(Locale.UK, "%.2f", avgSpeed);}

    public String getAvgSpeedFromSUVATToString() {
         return String.format(Locale.UK, "%.2f", avgSpeedFromSUVAT);
    }


    public void setAvgSpeedFromSUVAT(float avgSpeedFromSUVAT) {
        this.avgSpeedFromSUVAT = avgSpeedFromSUVAT;
    }

    public  List<Location> getPoints() {
        return points;
    }


    public List<GeoPoint> getGeoPoints(){
        List<GeoPoint>geoPoints=new ArrayList<>();
        for (Location l:points){
            GeoPoint g=new GeoPoint(l.getLatitude(),l.getLongitude());
            geoPoints.add(g);
            }
            return geoPoints;
    }

    public  void setPoints(List<Location> points){
        this.points = points;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getAvgSpeedFromSUVAT() {
        //return avgSpeed;
        return avgSpeedFromSUVAT;
    }

    public float getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(Date timeStarted) {
        this.timeStarted = timeStarted;
    }

    public Date getTimeStopped() {
        return timeStopped;
    }

    public void setTimeStopped(Date timeStopped) {
        this.timeStopped = timeStopped;
    }


    public long getTimeElapsedBetweenStartStops() {
        return timeElapsedBetweenStartStops;
    }

    public void setTimeElapsedBetweenStartStops(long timeElapsedBetweenStartStops) {
        this.timeElapsedBetweenStartStops = timeElapsedBetweenStartStops;
    }

    public float getTimeElapsedBetweenTrkPoints() {
        return timeElapsedBetweenTrkPoints;
    }

    public void setTimeElapsedBetweenTrkPoints(float timeElapsedBetweenTrkPoints) {
        this.timeElapsedBetweenTrkPoints = timeElapsedBetweenTrkPoints;
    }

    public float getTotalSpeedForRunningAverage() {
        return totalSpeedForRunningAverage;
    }

    public void setTotalSpeedForRunningAverage(float totalSpeedForRunningAverage) {
        this.totalSpeedForRunningAverage = totalSpeedForRunningAverage;
    }

    public int getTotalTrkPointsWithSpeedForRunningAverage() {
        return totalTrkPointsWithSpeedForRunningAverage;
    }

    public void setTotalTrkPointsWithSpeedForRunningAverage(int totalTrkPointsWithSpeedForRunningAverage) {
        this.totalTrkPointsWithSpeedForRunningAverage = totalTrkPointsWithSpeedForRunningAverage;
    }

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public void setTimeInMilliseconds(long timeInMilliseconds) {
        this.timeInMilliseconds = timeInMilliseconds;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public List<GeoPoint> getRouteReplay() {
        return routeReplay;
    }

    public void setRouteReplay(List<GeoPoint> routeReplay) {
        this.routeReplay = routeReplay;
    }

    public Long getBattle() {
        return battle;
    }

    public void setBattle(Long battle) {
        this.battle = battle;
    }


    public boolean isDeviation() {
        return deviation;
    }

    public void setDeviation(boolean deviation) {
        this.deviation = deviation;
    }

    public Location getCurrentLocation() {
        return lastValidLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.lastValidLocation = currentLocation;
    }

    public List<Location> getUnfilteredPoints() {
        return unfilteredPoints;
    }

    public List<Location> getFilteredPoints() {
        return filteredPoints;
    }

    public void setFilteredPoints(List<Location> filteredPoints) {
        this.filteredPoints = filteredPoints;
    }



    @Override
    public String toString() {
        return "Tracking{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", distance=" + distance +
                ", avgSpeed=" + avgSpeed +
                ", timeCreated=" + timeCreated +
                ", timeStarted=" + timeStarted +
                ", timeStopped=" + timeStopped +
                ", timeElapsedBetweenStartStops=" + timeElapsedBetweenStartStops +
                ", timeElapsedBetweenTrkPoints=" + timeElapsedBetweenTrkPoints +
                ", totalSpeedForRunningAverage=" + totalSpeedForRunningAverage +
                ", totalTrkPointsWithSpeedForRunningAverage=" + totalTrkPointsWithSpeedForRunningAverage +
                ", timeInMilliseconds=" + timeInMilliseconds +
                ", points=" + points +
                ", repeat=" + repeat +
                '}';
    }


}
