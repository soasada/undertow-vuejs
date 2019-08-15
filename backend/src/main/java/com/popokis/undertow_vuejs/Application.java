package com.popokis.undertow_vuejs;

import com.popokis.popok.http.Server;
import com.popokis.popok.sql_db.JdbcMapper;
import com.popokis.undertow_vuejs.http.server.Router;
import com.popokis.undertow_vuejs.user.User;
import com.popokis.undertow_vuejs.user.UserRepository;
import io.undertow.util.StatusCodes;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.UUID;

import static java.util.stream.Collectors.toConcurrentMap;

public final class Application {

  public static final String SECRET = "P44$w0rDS3cret";

  private static final Map<String, JdbcMapper> JDBC_MAPPERS = ServiceLoader.load(JdbcMapper.class).stream()
      .collect(toConcurrentMap(m -> m.type().getSimpleName(), ServiceLoader.Provider::get));

  public static void main(String[] args) {
    // This should be in other class.
    UserRepository.create(User.builder().username("admin").password("admin").build());
    for (int i = 0; i < 10; i++) {
      UserRepository.create(User.builder().username(UUID.randomUUID().toString()).password(UUID.randomUUID().toString()).build());
    }

    Server.builder(Router.router())
        .enableHttps()
        .keyStorePath("certificate/client.jks")
        .redirectToHttps(StatusCodes.TEMPORARY_REDIRECT)
        .enableHttp2()
        .build()
        .start();
  }

  public static <T> JdbcMapper<T> getMapper(Class<T> type) {
    return JDBC_MAPPERS.get(type.getSimpleName() + "Mapper");
  }
}
