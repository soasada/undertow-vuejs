package com.popokis.undertow_vuejs.http.server;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;

public final class Responses {

  private Responses() {}

  public static void asJson(HttpServerExchange exchange, String response) {
    exchange.setStatusCode(StatusCodes.OK);
    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json;charset=UTF-8");
    exchange.getResponseSender().send(response);
  }

  public static void ok(HttpServerExchange exchange) {
    exchange.setStatusCode(StatusCodes.OK);
    exchange.getResponseSender().send("OK");
  }

  public static void notFound(HttpServerExchange exchange) {
    exchange.setStatusCode(StatusCodes.NOT_FOUND);
    exchange.getResponseSender().send("NOT FOUND");
  }

  public static void serverError(HttpServerExchange exchange, String response) {
    exchange.setStatusCode(StatusCodes.INTERNAL_SERVER_ERROR);
    exchange.getResponseSender().send(response);
  }
}
