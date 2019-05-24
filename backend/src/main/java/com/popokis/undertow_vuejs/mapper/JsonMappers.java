package com.popokis.undertow_vuejs.mapper;

import com.fasterxml.jackson.databind.JavaType;

import java.io.IOException;
import java.util.List;

public final class JsonMappers {

  private JsonMappers() {}

  public static <T> List<T> list(String listJson, Class<T> type) {
    try {
      JavaType listType = JsonMapper.getInstance().mapper().getTypeFactory().constructCollectionType(List.class, type);
      return JsonMapper.getInstance().mapper().readValue(listJson, listType);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T model(String jsonModel, Class<T> type) {
    try {
      return JsonMapper.getInstance().mapper().readValue(jsonModel, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
