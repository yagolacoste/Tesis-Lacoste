package com.Tesis.bicycle.Model;

import android.location.Location;
import android.util.Log;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.nio.Buffer;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tracking implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final float MAX_SPEED_THRESHOLD =10.0F ; //10 m/s //segun google son 30 k/h con toda la furia
    private static final float MIN_SPEED_THRESHOLD =0.01F ; //0.01 m/s //minimo no va
    private static final float MAX_ACCURACY_THRESHOLD =16.0F ; //16 metros es un promedio de los valores calculados

    private static final float MIN_ALTITUDE_THRESHOLD = -450F; // 5 metros
    private static final double MAX_ALTITUDE_THRESHOLD = 5200F;
    private String id;
    private String title="";
    private String description="";
    private float distance=0;
    private float avgSpeed=0;
    private float avgSpeedFromSUVAT = 0;
    private Date timeCreated=null;
    private Date timeStarted=null;
    private Date timeStopped=null;
    private int rating=5;
    private int activityType;
    private long timeElapsedBetweenStartStops = 0;
    private float timeElapsedBetweenTrkPoints = 0;
    private float totalSpeedForRunningAverage = 0;
    private int totalTrkPointsWithSpeedForRunningAverage = 0;
    private long timeInMilliseconds=0;
    private transient List<Location> points=new ArrayList<>();//puntos que captura el gps
    private transient List<GeoPoint> routeReplay=new ArrayList<>();

    private boolean repeat=false;

    private Long battle;

    private boolean deviation =true;

    private Location lastValidLocation ;//ultima location valida (VER COMO MUESTRA EN MAPA)


    private boolean notificationDisplayed = false;

    private List<Location> buffer=new ArrayList<>();

    private List<Location> filteredPoints=new ArrayList<>();

    private List<Location> unfilteredPoints=new ArrayList<>();

//    private static final float CONFIDENCE_THRESHOLD=30F;// 30 metros


    

    public Tracking() {

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
                if(isRepeat()){ //Reviso si es repetido el camino
                    this.setDeviation(this.checkingNearestNewPointAndNextPointIndex(currentLocation));//Revisa y setea si se devio entre el proximo punto de mi listado almacenado y el nuevo punto
                    if (!this.isDeviation() && !notificationDisplayed) {
                        notificationDisplayed = true;
                        this.setDeviation(true);
                    }
                }

            }

    }

    private void checkMobility(Location currentLocation) {
        if(buffer.isEmpty()) {
            buffer.add(currentLocation);
            //addCoordinateToHistory(currentLocation);//guardo la primer coordenada en el buffer y en el historial  por estar vacios
        }else if(!buffer.isEmpty() && buffer.size()==1){//detecta si esta detenido o no
//            float distance = buffer.get(0).distanceTo(currentLocation);
//             if(distance<=CONFIDENCE_THRESHOLD){
//                buffer.clear();
//                addCoordinateToHistory(currentLocation);
//              }
            buffer.add(currentLocation);

        }else if(!buffer.isEmpty() && buffer.size()==2){
            Location correctLocation=buffer.get(0);
            Location problematicLocation=buffer.get(1);
            float correctDistance = correctLocation.distanceTo(currentLocation);
            float problematicDistance= problematicLocation.distanceTo(currentLocation);
            float distanceToCorrectAndProblematic = correctLocation.distanceTo(problematicLocation);//Di
          if((correctDistance< distanceToCorrectAndProblematic)) {
                this.buffer.remove(problematicLocation);//remuevo segunda /// nunca entra aca porque si es un desvio el filtro por distancia lo saca
          }else{
                this.buffer.remove(correctLocation);//remuevo la primera
                addCoordinateToHistory(correctLocation);
            }
           this.checkMobility(currentLocation);
        }

    }


    private void addCoordinateToHistory(Location currentLocation) {
        if (points.isEmpty()) {
            avgSpeed = currentLocation.getSpeed();
        } else {
            Location lastPoint = points.get(points.size() - 1);
            distance += lastPoint.distanceTo(currentLocation);
            timeElapsedBetweenTrkPoints += Math.abs(lastPoint.getTime() - currentLocation.getTime());
            avgSpeedFromSUVAT = ((distance / (float) 1000) / (getCurrentTimeMillis() / (float) 3600000));
        }
            if (currentLocation.getSpeed() != 0) {
                totalSpeedForRunningAverage += currentLocation.getSpeed();
                totalTrkPointsWithSpeedForRunningAverage += 1;
                avgSpeed = totalSpeedForRunningAverage / totalTrkPointsWithSpeedForRunningAverage;
            }
            points.add(currentLocation);
            this.lastValidLocation = currentLocation;
    }

    private boolean isCoordinateValid(Location currentLocation) {
        if(points.isEmpty()){
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
            Log.i("DISTANCE BETWEEN LASTPOINT NEW LOCATION","Su DISTANCE es : "+distance);
            if(distance >MAX_ACCURACY_THRESHOLD/2)
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
            if (distance <= MAX_ACCURACY_THRESHOLD) { //Verifica si la distancia es menor a 16 metros entre la nueva localizacion y el proximo punto de proximidad
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

    public void stopTrackingActivity(){
        if(!buffer.isEmpty()){
            for(Location location:buffer)
                addCoordinateToHistory(location);
        }
        timeStopped=new Date(System.currentTimeMillis());
        timeElapsedBetweenStartStops = timeStopped.getTime() - timeStarted.getTime();

        //Calculate the average speed based on the distance and the time between stopping and starting
        //the session
        avgSpeedFromSUVAT = distance / (timeElapsedBetweenStartStops / 1000);//m/s
        avgSpeedFromSUVAT=(avgSpeedFromSUVAT*3600)/1000;
//        avgSpeedFromSUVAT = ((distance / (float) 1000) / (getCurrentTimeMillis() / (float) 3600000));

    }

    public String getDuration() {
        return timeToString(timeElapsedBetweenStartStops);
    }


    public long getCurrentTimeMillis(){
       return (System.currentTimeMillis()-timeStarted.getTime());
    }

    //return activity recognicion with determinate avgspeed
    public static int getPredictedActivityType(float avgSpeed){
        if(avgSpeed<8) {
            return 2;
        }else if(avgSpeed<15){
            return 1;
        }else return 3;
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
        if(!isRepeat()){
            List<GeoPoint>geoPoints=new ArrayList<>();
            for (Location l:points){
                GeoPoint g=new GeoPoint(l.getLatitude(),l.getLongitude());
                geoPoints.add(g);
                }
                return geoPoints;
        }
        return routeReplay;
    }

    //Revisa la distancia entre ambas rutas sea la misma o proxima con diferencia de 15 metros
    public float getDistancesRoutes(){
        float totalDistance =0;
        List<Location> route=convertRouteReplay();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
             totalDistance = (float) route.stream()
                    .mapToDouble(location -> {
                        int index = route.indexOf(location);
                        if (index < route.size() - 1) {
                            Location next = route.get(index + 1);
                            return location.distanceTo(next);
                        }
                        return 0.0;
                    })
                    .sum();
        }
        if(isRepeat()){
            float result=totalDistance-distance;
            return result;
        }else return distance;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
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

    public boolean isNotificationDisplayed() {
        return notificationDisplayed;
    }

    public void setNotificationDisplayed(boolean notificationDisplayed) {
        this.notificationDisplayed = notificationDisplayed;
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
                ", rating=" + rating +
                ", activityType=" + activityType +
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
