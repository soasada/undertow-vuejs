package com.popokis.web_app_demo.http.server;

import io.undertow.server.HttpServerExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Requests {

  private Requests() {}

  public static String body(HttpServerExchange exchange) {
    StringBuilder stringBuilder = new StringBuilder();

    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exchange.getInputStream()))) {
      String line;

      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }

    return stringBuilder.toString();
  }
}
