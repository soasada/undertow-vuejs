package com.popokis.web_app_demo.mapper.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public final class JsonMapper {

  private final ObjectMapper mapper;

  private JsonMapper() {
    mapper = new ObjectMapper();

    mapper.registerModule(new JavaTimeModule());
    mapper.findAndRegisterModules();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  private static class Holder {
    private static final JsonMapper INSTANCE = new JsonMapper();
  }

  public static JsonMapper getInstance() {
    return Holder.INSTANCE;
  }

  public ObjectMapper mapper() {
    return mapper;
  }

  public String toJson(Object o) {
    try {
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public boolean isValidJson(String json) {
    boolean valid = true;

    try {
      mapper.readTree(json);
    } catch (IOException e) {
      valid = false;
    }

    return valid;
  }
}
