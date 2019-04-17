package com.popokis.web_app_demo.http.api;

import com.popokis.web_app_demo.entity.User;
import com.popokis.web_app_demo.http.server.Handlers;
import com.popokis.web_app_demo.repository.UserRepository;
import io.undertow.server.HttpHandler;

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
    return exchange -> {
      // TODO:
    };
  }
}
