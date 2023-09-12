package com.Tesis.bicycle.Model;

import android.location.Location;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.Tesis.bicycle.Activity.TrackingActivity;
import com.Tesis.bicycle.Constants;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.RandomStringUtils;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.simplify.DouglasPeuckerSimplifier;
import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import kotlin.jvm.Transient;

public class Tracking implements Serializable {

    private static final long serialVersionUID = 1L;
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

    private boolean deviation=true;

    private Location currentLocation;

    private Long image;

    private static final double MIN_DISTANCE_THRESHOLD = 10.0; // Establece un valor apropiado en metros

    private static final double MAX_ANGLE_THRESHOLD = 270.0; // Establece un valor apropiado en grados

    public Tracking() {

    }

    public Tracking(Tracking tracking){
        id=tracking.getId();
        title=tracking.getTitle();
        description=tracking.getDescription();
        routeReplay=tracking.getRouteReplay();
        battle=tracking.getBattle();
        repeat=true;
    }

    public void addTracking(Location currentLocation) {
        if (isCoordinateValid(currentLocation)) {
            // La nueva coordenada es válida, agrégala al historial
            addCoordinateToHistory(currentLocation);
        }
        this.currentLocation=currentLocation;
    }

    private void addCoordinateToHistory(Location currentLocation) {
        if(points.isEmpty()) {
            avgSpeed=currentLocation.getSpeed();
        }else{
            Location lastPoint = points.get(points.size() - 1);
                distance += lastPoint.distanceTo(currentLocation);
            timeElapsedBetweenTrkPoints +=Math.abs(this.getLastPoint().getTime()-currentLocation.getTime());
            avgSpeedFromSUVAT = ((distance / (float) 1000) / (getCurrentTimeMillis() / (float) 3600000));
        }
        if(currentLocation.getSpeed()!=0){
            totalSpeedForRunningAverage+=currentLocation.getSpeed();
            totalTrkPointsWithSpeedForRunningAverage+=1;
            avgSpeed=totalSpeedForRunningAverage/totalTrkPointsWithSpeedForRunningAverage;
        }
        points.add(currentLocation);
        //this.currentLocation=currentLocation;

    }

    private boolean isCoordinateValid(Location currentLocation) {
        // Obtén la última coordenada válida en tu historial
        Location lastValidLocation = getLastValidCoordinate();

        if (lastValidLocation == null) {
//            this.currentLocation=currentLocation;
            // No hay coordenadas válidas anteriores, considera la nueva coordenada válida
            return true;
        }

        // Calcula la distancia entre la última coordenada válida y la nueva coordenada
        double distance = calculateDistance(
                lastValidLocation.getLatitude(), lastValidLocation.getLongitude(),
                currentLocation.getLatitude(), currentLocation.getLongitude()
        );

        // Calcula el ángulo entre la dirección actual y la dirección de movimiento desde la última coordenada válida
        double angle = calculateAngle(lastValidLocation, currentLocation);

        // Comprueba si la distancia es mayor o igual que MIN_DISTANCE_THRESHOLD
        // y el ángulo es menor o igual que MAX_ANGLE_THRESHOLD
        return distance >= MIN_DISTANCE_THRESHOLD && angle <= MAX_ANGLE_THRESHOLD;
    }

    private Location getLastValidCoordinate() {
        if (!points.isEmpty()) {
            return points.get(points.size() - 1);
        }
        return null;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Radio de la Tierra en metros
        final double R = 6371e3; // metros

        // Convierte las coordenadas de grados a radianes
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Diferencia de latitud y longitud
        double dlat = lat2Rad - lat1Rad;
        double dlon = lon2Rad - lon1Rad;

        // Fórmula de Haversine para calcular la distancia
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }
    private double calculateAngle(Location location1, Location location2) {
        double lat1 = location1.getLatitude();
        double lon1 = location1.getLongitude();
        double lat2 = location2.getLatitude();
        double lon2 = location2.getLongitude();

        // Convierte las coordenadas de grados a radianes
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Diferencia de longitud entre las coordenadas
        double dlon = lon2Rad - lon1Rad;

        // Fórmula del ángulo entre direcciones (azimuth) en radianes
        double y = Math.sin(dlon) * Math.cos(lat2Rad);
        double x = Math.cos(lat1Rad) * Math.sin(lat2Rad) -
                Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(dlon);
        double azimuthRad = Math.atan2(y, x);

        // Convierte el ángulo de radianes a grados
        double azimuthDegrees = Math.toDegrees(azimuthRad);

        // Asegura que el ángulo esté en el rango [0, 360)
        azimuthDegrees = (azimuthDegrees + 360) % 360;

        return azimuthDegrees;
    }




//    public void addTracking(Location currentLocation) {
//        if(points.isEmpty()) {
//            avgSpeed=currentLocation.getSpeed();
//        }else{
//            Location lastPoint = points.get(points.size() - 1);
////            if(lastPoint.distanceTo(currentLocation)>1.5f)//preguntar por el umbral
//                distance += lastPoint.distanceTo(currentLocation);
//            timeElapsedBetweenTrkPoints +=Math.abs(this.getLastPoint().getTime()-currentLocation.getTime());
//            avgSpeedFromSUVAT = ((distance / (float) 1000) / (getCurrentTimeMillis() / (float) 3600000));
//        }
//        if(currentLocation.getSpeed()!=0){
//            totalSpeedForRunningAverage+=currentLocation.getSpeed();
//            totalTrkPointsWithSpeedForRunningAverage+=1;
//            avgSpeed=totalSpeedForRunningAverage/totalTrkPointsWithSpeedForRunningAverage;
//        }
//        points.add(currentLocation);
//        this.currentLocation=currentLocation;
//    }

    public boolean trackingRoute(Location location){
        List<Location>points=convertRouteReplay();

        int nearestIndex = findNearestPointIndex(location);
        if (nearestIndex != -1) {
            // Verificar si la posición actual se encuentra dentro del umbral de proximidad
            //double distance = calculateDistance(location, points.get(nearestIndex));
            float distance =location.distanceTo( points.get(nearestIndex));
            if (distance <= 45.0f) {
                return true; // El objeto está siguiendo la ruta correctamente
            }
        }

        return false;
    }

    private int findNearestPointIndex(Location currentLocation) {
        float minDistance = Float.MAX_VALUE;
        int nearestIndex = -1;

        for (int i = 0; i < convertRouteReplay().size(); i++) {
            //double distance = calculateDistance(currentLocation, convertGeoPoint().get(i));
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
        return currentLocation;
    }

    public boolean isCreated(){
        return !(timeCreated==null);
    }

    public void startTrackingActivity(){
        timeCreated=new Date(System.currentTimeMillis());
        timeStarted=new Date(System.currentTimeMillis());
        }

    public void stopTrackingActivity(){//probar con el celu
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

    public String getAvgSpeedFromSUVAT() {
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

    public float getAvgSpeed() {
        return avgSpeedFromSUVAT;
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
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
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
