package com.popokis.web_app_demo;

import com.popokis.web_app_demo.db.JdbcMapper;
import com.popokis.web_app_demo.entity.User;
import com.popokis.web_app_demo.http.server.SimpleServer;
import com.popokis.web_app_demo.repository.UserRepository;

import java.util.Map;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toConcurrentMap;

public final class Application {

  public static final String SECRET = "P44$w0rDS3cret";

  private static final Map<String, JdbcMapper> JDBC_MAPPERS = ServiceLoader.load(JdbcMapper.class).stream()
      .collect(toConcurrentMap(m -> m.type().getSimpleName(), ServiceLoader.Provider::get));

  public static void main(String[] args) {
    // This should be in other class.
    UserRepository.create(User.builder().username("admin").password("admin").build());

    SimpleServer server = new SimpleServer();
    server.start();
  }

  public static <T> JdbcMapper<T> getMapper(Class<T> type) {
    return JDBC_MAPPERS.get(type.getSimpleName() + "Mapper");
  }
}
