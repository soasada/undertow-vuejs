package com.popokis.web_app_demo;

import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.util.Headers;
import io.undertow.util.Methods;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public final class V1Router implements Router {

  @Override
  public List<Route> routes() {
    return List.of(
        Route.of(Methods.GET, "/", new BlockingHandler(fileHandler("index.html"))),
        Route.of(Methods.GET, "/app.js", new BlockingHandler(fileHandler("app.js"))),
        Route.of(Methods.GET, "/about.js", new BlockingHandler(fileHandler("about.js"))),
        Route.of(Methods.GET, "/favicon.ico", new BlockingHandler(fileHandler("favicon.ico"))),
        Route.of(Methods.GET, "/static/img/logo.82b9c7a5.png", new BlockingHandler(fileHandler("/static/img/logo.82b9c7a5.png")))
    );
  }

  @Override
  public String version() {
    return "/api/v1";
  }

  private HttpHandler fileHandler(String filename) {
    return exchange -> {
      InputStream inputStream  = Application.class.getResourceAsStream(File.separator + "public" + File.separator + filename);
      Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
      String html = scanner.hasNext() ? scanner.next() : "";
      exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
      exchange.getResponseSender().send(html);
    };
  }
}
