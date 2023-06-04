package com.Tesis.admin.Convert;


import com.Tesis.admin.Dto.Route.GeoPoint;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GeoPointListDeserializer extends JsonDeserializer<List<GeoPoint>> {
  @Override
  public List<GeoPoint> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    List<GeoPoint> geoPoints = new ArrayList<>();

    JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
    Iterator<JsonNode> iterator = jsonNode.elements();
    while (iterator.hasNext()) {
      JsonNode coordinateNode = iterator.next();
      double latitude = coordinateNode.get(0).doubleValue();
      double longitude = coordinateNode.get(1).doubleValue();
      GeoPoint geoPoint = new GeoPoint(latitude, longitude);
      geoPoints.add(geoPoint);
    }
    return geoPoints;
  }
}
//    List<GeoPoint> geoPoints = new ArrayList<>();
//    JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
//    Iterator<JsonNode> iterator = jsonNode.elements();
//    while (iterator.hasNext()) {
//      JsonNode coordinateNode = iterator.next();
//      double latitude = coordinateNode.get(0).doubleValue();
//      double longitude = coordinateNode.get(1).doubleValue();
//      GeoPoint geoPoint = new GeoPoint(latitude, longitude);
//      geoPoints.add(geoPoint);
//    }
//    return geoPoints;
//  }

