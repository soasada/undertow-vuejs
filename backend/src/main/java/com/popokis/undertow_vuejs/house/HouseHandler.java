package com.popokis.undertow_vuejs.house;

import com.popokis.undertow_vuejs.http.server.Handlers;
import io.undertow.server.HttpHandler;

public final class HouseHandler {

  private HouseHandler() {}

  public static HttpHandler all() {
    return Handlers.idBased(HouseRepository::all);
  }

  public static HttpHandler create() {
    return Handlers.bodyBased(House.class, HouseRepository::create);
  }

  public static HttpHandler read() {
    return Handlers.idBased(HouseRepository::read);
  }

  public static HttpHandler update() {
    return Handlers.bodyBased(House.class, HouseRepository::update);
  }

  public static HttpHandler remove() {
    return Handlers.idBased(HouseRepository::delete);
  }
}
