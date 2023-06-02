package com.Tesis.admin.Convert;


import com.Tesis.admin.Dto.Route.GeoPoint;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GeoPointListDeserializer extends JsonDeserializer<List<GeoPoint>> {
  @Override
  public List<GeoPoint> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    List<GeoPoint> geoPoints = new ArrayList<>();
    while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
      double latitude = jsonParser.getDoubleValue();
      jsonParser.nextToken();
      double longitude = jsonParser.getDoubleValue();
      GeoPoint geoPoint = new GeoPoint(latitude, longitude);
      geoPoints.add(geoPoint);
    }
    return geoPoints;
  }
}
