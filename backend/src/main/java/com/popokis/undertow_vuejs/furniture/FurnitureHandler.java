package com.popokis.undertow_vuejs.furniture;

import com.popokis.undertow_vuejs.http.server.Handlers;
import io.undertow.server.HttpHandler;

public final class FurnitureHandler {

  private FurnitureHandler() {}

  public static HttpHandler all() {
    return Handlers.idBased(FurnitureRepository::all);
  }

  public static HttpHandler create() {
    return Handlers.bodyBased(Furniture.class, FurnitureRepository::create);
  }

  public static HttpHandler read() {
    return Handlers.idBased(FurnitureRepository::read);
  }

  public static HttpHandler update() {
    return Handlers.bodyBased(Furniture.class, FurnitureRepository::update);
  }

  public static HttpHandler remove() {
    return Handlers.idBased(FurnitureRepository::delete);
  }
}
