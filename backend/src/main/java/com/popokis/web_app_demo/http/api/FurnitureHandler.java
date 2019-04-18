package com.popokis.web_app_demo.http.api;

import com.popokis.web_app_demo.entity.Furniture;
import com.popokis.web_app_demo.http.server.Handlers;
import com.popokis.web_app_demo.repository.FurnitureRepository;
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
