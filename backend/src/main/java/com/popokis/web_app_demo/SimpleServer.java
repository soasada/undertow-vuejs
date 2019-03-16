package com.popokis.web_app_demo;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;

public final class SimpleServer {

  private final int port;
  private final String host;
  private final Router router;
  private final Undertow server;

  public SimpleServer(int port, String host, Router router) {
    this.port = port;
    this.host = host;
    this.router = router;

    RoutingHandler tempHandler = new RoutingHandler();
    for (var route : router.routes()) {
      tempHandler.add(route.method(), route.endpoint(), route.handler());
    }

    server = Undertow.builder()
        .addHttpListener(port, host, Handlers.routing().addAll(tempHandler))
        .build();
  }

  public void start() {
    server.start();
  }

  public void stop() {
    server.stop();
  }

  public String url() {
    return "http://" + host + ":" + port + router.version();
  }
}
