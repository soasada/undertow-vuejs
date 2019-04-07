package com.popokis.web_app_demo;

import com.popokis.web_app_demo.db.JdbcMapper;
import com.popokis.web_app_demo.http.SimpleServer;

import java.util.Map;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toConcurrentMap;

public final class Application {

  private static final Map<String, JdbcMapper> JDBC_MAPPERS = ServiceLoader.load(JdbcMapper.class).stream()
      .collect(toConcurrentMap(m -> m.type().getSimpleName(), ServiceLoader.Provider::get));

  public static void main(String[] args) {
    SimpleServer server = new SimpleServer();
    server.start();
  }

  public static <T> JdbcMapper<T> getMapper(Class<T> type) {
    return JDBC_MAPPERS.get(type.getSimpleName() + "Mapper");
  }
}
