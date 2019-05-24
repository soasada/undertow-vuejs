package com.popokis.undertow_vuejs.http;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.popokis.undertow_vuejs.Application;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderValues;
import io.undertow.util.StatusCodes;

public final class AuthorizationHandler implements HttpHandler {

  private final HttpHandler next;

  public AuthorizationHandler(HttpHandler next) {
    this.next = next;
  }

  @Override
  public void handleRequest(HttpServerExchange exchange) throws Exception {
    try {
      HeaderValues authorizationHeader = exchange.getRequestHeaders().get("Authorization");

      if (authorizationHeader == null || authorizationHeader.isEmpty()) {
        exchange.setStatusCode(StatusCodes.BAD_REQUEST);
        exchange.getResponseSender().send("");
      }

      String token = authorizationHeader.getFirst().replace("Bearer ", "");
      JWT.require(Algorithm.HMAC512(Application.SECRET.getBytes())).build().verify(token);
      next.handleRequest(exchange);
    } catch (JWTVerificationException e) {
      exchange.setStatusCode(StatusCodes.UNAUTHORIZED);
      exchange.getResponseSender().send("");
    }
  }
}
