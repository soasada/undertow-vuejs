package com.popokis.web_app_demo;

import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.util.HttpString;

public final class Route {

  private final HttpString method;
  private final String endpoint;
  private final HttpHandler handler;

  private Route(HttpString method, String endpoint, HttpHandler handler) {
    this.method = method;
    this.endpoint = endpoint;
    this.handler = new BlockingHandler(handler);
  }

  public HttpString method() {
    return method;
  }

  public String endpoint() {
    return endpoint;
  }

  public HttpHandler handler() {
    return handler;
  }

  public static Route of(HttpString method, String endpoint, HttpHandler handler) {
    return new Route(method, endpoint, handler);
  }
}
