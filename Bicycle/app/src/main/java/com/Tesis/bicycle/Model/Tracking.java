package com.Tesis.bicycle.Model;

import android.location.Location;
import android.os.Build;

import com.google.gson.annotations.SerializedName;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kotlin.jvm.Transient;

public class Tracking implements Serializable {
    private static final long serialVersionUID = 1L;
    private Double distance=0.0;
    private Double speed=0.0;
    private Double timeSpeed=0.0;
    private LocalDate timeSession=null;


    private transient List<Location> points;

    public Tracking() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timeSession=LocalDate.now();
        }
        points=new ArrayList<>();
    }


    public void addTracking(Location currentLocation) {
        if(!points.isEmpty()) {
            Location lastPoint = points.get(points.size() - 1);
            distance += Double.valueOf((lastPoint.distanceTo(currentLocation)) / 1000);
            if (currentLocation.hasSpeed())
                speed += Double.valueOf(currentLocation.getSpeed());
            timeSpeed +=Double.valueOf( currentLocation.getTime());
        }
        else{
             if (currentLocation.hasSpeed())
                 speed += Double.valueOf(currentLocation.getSpeed());
             timeSpeed += Double.valueOf(currentLocation.getTime());
        }
        points.add(currentLocation);

    }

    public  List<Location> getPoints() {
        return points;
    }

    public  void setPoints(List<Location> points){
        this.points = points;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(Double timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    public LocalDate getTimeSession() {
        return timeSession;
    }

    public void setTimeSession(LocalDate timeSession) {
        this.timeSession = timeSession;
    }

    //    private double getSpeed(){
//        double speed=0;
//        int j=0;
//        //
//        for(int i=0;i<savedlocations.size();i++){
//
//            Location p1=savedlocations.get(i);
//            if(p1.hasSpeed()){
//                speed=speed+p1.getSpeed();
//                j++;//--->sumo j para contar los puntos que tuvieorn velocidad
//            }
//        }
//        return speed/j;
//    }


    //
//    private double getTimeSpeed() {//acomodar esto size-getTime(0); para calcular el tiempo que tardo en hacerlo
//        double time=0;
//        for(int i=0;i<savedlocations.size();i++){
//            time=time+savedlocations.get(i).getTime();
//        }
//        return time;
//    }
//
//    //obtengo la velocidad promedio(metros por segundo)----->getSpeedAcuraccyMetersPerSecond()
//    private double getSpeed(){
//        double speed=0;
//        int j=0;
//        //
//        for(int i=0;i<savedlocations.size();i++){
//
//            Location p1=savedlocations.get(i);
//            if(p1.hasSpeed()){
//            speed=speed+p1.getSpeed();
//            j++;//--->sumo j para contar los puntos que tuvieorn velocidad
//            }
//        }
//        return speed/j;
//    }
//
//    //Obtengo la distancia entre los puntos
//    private double getDistance() {
//        double distance=0;
//        int i=0;
//        Location p1=null;
//        while(i<savedlocations.size()){
//            p1=savedlocations.get(i);
//            if(i+1<savedlocations.size()){
//                Location p2=savedlocations.get(i+1);
//                distance=distance+p1.distanceTo(p2);
//            }
//            else
//                break;
//            i++;
//        }
//        return distance/1000;//lo da en metros
//    }

}
