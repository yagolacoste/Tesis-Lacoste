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
    private transient List<GeoPoint> pointsDraw=new ArrayList<>();

    private List<Location> geoReference=new ArrayList<>();
    private boolean repeat=false;

    private Long battle;

    private double tolerance=0.2;


    public Tracking() {

    }

    public Tracking(Tracking tracking){
        id=tracking.getId();
        title=tracking.getTitle();
        description=tracking.getDescription();
        pointsDraw=tracking.getPointsDraw();
        battle=tracking.getBattle();
        repeat=true;
    }



    public void addTracking(Location currentLocation) {
        if(points.isEmpty()) {
            avgSpeed=currentLocation.getSpeed();
        }else{
            Location lastPoint = points.get(points.size() - 1);
//            if(lastPoint.distanceTo(currentLocation)>1.5f)//preguntar por el umbral
                distance += lastPoint.distanceTo(currentLocation);
            timeElapsedBetweenTrkPoints +=Math.abs(this.getLastPoint().getTime()-currentLocation.getTime());
        }
        if(currentLocation.hasSpeed()){
            totalSpeedForRunningAverage+=currentLocation.getSpeed();
            totalTrkPointsWithSpeedForRunningAverage+=1;
            avgSpeed=totalSpeedForRunningAverage/totalTrkPointsWithSpeedForRunningAverage;
        }
        points.add(currentLocation);
    }

    public boolean checkRoute(){
        boolean sigueCamino = true;

        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate[] coordenadas = new Coordinate[points.size()];

        for (int i = 0; i < points.size(); i++) {
            Location ubicacion = points.get(i);
            coordenadas[i] = new Coordinate(ubicacion.getLatitude(), ubicacion.getLongitude());
        }

        LineString trayectoriaOriginal = geometryFactory.createLineString(coordenadas);

        double tolerance = 0.001; // Valor de tolerancia para la simplificación

        DouglasPeuckerSimplifier simplifier = new DouglasPeuckerSimplifier(trayectoriaOriginal);
        simplifier.setDistanceTolerance(tolerance);

        LineString trayectoriaSimplificada = (LineString) simplifier.getResultGeometry();

        Coordinate[] puntosSimplificados = trayectoriaSimplificada.getCoordinates();

        ArrayList<Coordinate> trayectoriaSimplificadaLista = new ArrayList<>();
        for (Coordinate punto : puntosSimplificados) {
            trayectoriaSimplificadaLista.add(new Coordinate(punto.x, punto.y));
        }


        if (trayectoriaSimplificadaLista.size() <= pointsDraw.size() ||trayectoriaSimplificadaLista.size() > pointsDraw.size() ) {
            for (int i = 0; i < trayectoriaSimplificadaLista.size(); i++) {
                Coordinate puntoSimplificado = trayectoriaSimplificadaLista.get(i);
                Coordinate puntoEsperado = new Coordinate(pointsDraw.get(i).getLatitude(),pointsDraw.get(i).getLongitude());

                // Calcular la distancia entre los puntos
                float distancia = calcularDistanciaEntrePuntos(puntoSimplificado, puntoEsperado);

                // Verificar si la distancia supera un umbral permitido
                if (distancia > 0.15f) {
                    sigueCamino = false;
                    break;
                }
            }
        } else {
            sigueCamino = false;
        }
//
//        if (sigueCamino) {
//            // El usuario está siguiendo el camino esperado
//            // Resto de tu lógica aquí...
//        } else {
//            // El usuario se ha desviado del camino esperado
//            // Mostrar una notificación, enviar una alerta, etc.
//        }
        return sigueCamino;
    }

    private float calcularDistanciaEntrePuntos(Coordinate puntoSimplificado, Coordinate puntoEsperado) {
        return (float) puntoSimplificado.distance(puntoEsperado);
    }

    public Location getLastPoint(){
        if(!points.isEmpty())
            return points.get(points.size()-1);
        return null;
    }

    public boolean isCreated(){
        return !(timeCreated==null);
    }

    public void startTrackingActivity(){
        timeCreated=new Date(System.currentTimeMillis());
        timeStarted=new Date(System.currentTimeMillis());
        if(id==null)
            id= RandomStringUtils.random(Constants.MAX_CARACTER_ID,true,true);
    }

    public void stopTrackingActivity(){
        timeStopped=new Date(System.currentTimeMillis());

    }

    public long getCurrentTimeMillis(){
        return timeElapsedBetweenStartStops+(System.currentTimeMillis()-timeStarted.getTime());
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
//        Instant instant = Instant.ofEpochMilli(timeInMilliseconds);
        long timeSwapBuff = 0L;
        long updateTime = timeSwapBuff + timeInMilliseconds;
        int secs = (int) (updateTime / 1000);
        int mins = secs / 60;
        secs %= 60;
        int hrs = mins / 60;
        mins %= 60;
        int milliseconds = (int) timeInMilliseconds % 1000;
        int centisecs = milliseconds / 10;
        LocalTime date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalTime.of(hrs, mins, secs);
        }
        return date;
    }

    public String getTimeString(){
        timeInMilliseconds=getCurrentTimeMillis();
        return timeToString(timeInMilliseconds);
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
        return pointsDraw;
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

    public List<GeoPoint> getPointsDraw() {
        return pointsDraw;
    }

    public void setPointsDraw(List<GeoPoint> pointsDraw) {
        this.pointsDraw = pointsDraw;
    }

    public Long getBattle() {
        return battle;
    }

    public void setBattle(Long battle) {
        this.battle = battle;
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
