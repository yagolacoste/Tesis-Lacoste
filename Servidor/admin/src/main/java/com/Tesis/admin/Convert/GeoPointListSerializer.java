package com.Tesis.admin.Convert;


import com.Tesis.admin.Dto.Route.GeoPoint;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;


public class GeoPointListSerializer extends JsonSerializer<List<GeoPoint>> {
  @Override
  public void serialize(List<GeoPoint> geoPoints, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException, IOException {
    jsonGenerator.writeStartArray();
    for (GeoPoint geoPoint : geoPoints) {
      jsonGenerator.writeStartArray();
      jsonGenerator.writeNumber(geoPoint.getLatitude());
      jsonGenerator.writeNumber(geoPoint.getLongitude());
      jsonGenerator.writeEndArray();
    }
    jsonGenerator.writeEndArray();
  }
}
