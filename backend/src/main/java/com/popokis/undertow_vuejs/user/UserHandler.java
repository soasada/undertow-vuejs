package com.popokis.undertow_vuejs.user;

import com.auth0.jwt.JWT;
import com.popokis.undertow_vuejs.Application;
import com.popokis.undertow_vuejs.http.server.Handlers;
import com.popokis.undertow_vuejs.login.Token;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.sse.ServerSentEventHandler;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public final class UserHandler {

  private UserHandler() {}

  public static HttpHandler all() {
    return Handlers.listBased((Void v) -> UserRepository.all());
  }

  public static HttpHandler create() {
    return Handlers.bodyBased(User.class, UserRepository::create);
  }

  public static HttpHandler read() {
    return Handlers.idBased(UserRepository::read);
  }

  public static HttpHandler update() {
    return Handlers.bodyBased(User.class, UserRepository::update);
  }

  public static HttpHandler remove() {
    return Handlers.idBased(UserRepository::delete);
  }

  public static HttpHandler findUserHouses() {
    return Handlers.idBased(UserRepository::findUserHouses);
  }

  public static HttpHandler login() {
    return Handlers.bodyBased(User.class, (userWithoutHash) -> {
      User loggedUser = UserRepository.login(userWithoutHash);
      return Token.create(
          JWT.create()
              .withSubject(loggedUser.getId() + "")
              .withExpiresAt(new Date(System.currentTimeMillis() + 900000)) // 15 minutes
              .sign(HMAC512(Application.SECRET.getBytes()))
      );
    });
  }

  public static HttpHandler streamUsers(ServerSentEventHandler sseHandler) {
    return Handlers.streamUsers((Void v) -> UserRepository.all(), sseHandler);
  }
}
