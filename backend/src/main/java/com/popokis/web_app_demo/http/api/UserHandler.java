package com.popokis.web_app_demo.http.api;

import com.auth0.jwt.JWT;
import com.popokis.web_app_demo.Application;
import com.popokis.web_app_demo.entity.User;
import com.popokis.web_app_demo.http.api.model.Token;
import com.popokis.web_app_demo.http.server.Handlers;
import com.popokis.web_app_demo.repository.UserRepository;
import io.undertow.server.HttpHandler;

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
}
