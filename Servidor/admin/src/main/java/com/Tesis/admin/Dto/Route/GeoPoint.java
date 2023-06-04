package com.Tesis.admin.Dto.Route;


import com.Tesis.admin.Convert.GeoPointListDeserializer;
import com.Tesis.admin.Convert.GeoPointListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;


public class GeoPoint implements Serializable {

  private double Longitude;
  private double Latitude;

  public GeoPoint(final double aLatitudeE6, final double aLongitudeE6) {
    this.Latitude = aLatitudeE6 ;
    this.Longitude = aLongitudeE6 ;
  }

  public GeoPoint(Object r) {
  }


  public double getLongitude() {

    return Longitude;
  }



  public void setLongitude(double longitude) {

    Longitude = longitude;
  }



  public double getLatitude() {

    return Latitude;
  }



  public void setLatitude(double latitude) {

    Latitude = latitude;
  }

  @Override
  public String toString() {
    return "GeoPoint{" + Longitude +
            "," + Latitude +
            '}';
  }
}
