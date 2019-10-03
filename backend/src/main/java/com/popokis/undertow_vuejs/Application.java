package com.popokis.undertow_vuejs;

import com.popokis.popok.http.Server;
import com.popokis.undertow_vuejs.http.server.Router;
import com.popokis.undertow_vuejs.user.User;
import com.popokis.undertow_vuejs.user.UserRepository;
import io.undertow.util.StatusCodes;

import java.util.UUID;

import static java.util.stream.Collectors.toList;

public final class Application {

  public static final String SECRET = "P44$w0rDS3cret";

  public static void main(String[] args) {
    // This should be in other class.
    UserRepository.all().stream()
        .map(u -> UserRepository.delete(u.getId()))
        .collect(toList()).stream()
        .map(i -> UserRepository.create(User.builder().username(UUID.randomUUID().toString()).password(UUID.randomUUID().toString()).build()))
        .collect(toList());
    UserRepository.create(User.builder().username("admin").password("admin").build());

    Server.builder(Router.router())
        .enableHttps("certificate/client.jks")
        .redirectToHttps(StatusCodes.TEMPORARY_REDIRECT)
        .enableHttp2()
        .build()
        .start();
  }
}
