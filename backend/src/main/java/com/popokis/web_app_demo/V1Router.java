package com.popokis.web_app_demo;

import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.util.Headers;
import io.undertow.util.Methods;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

public final class V1Router implements Router {

  @Override
  public List<Route> routes() {
    List<Route> staticFileRoutes = staticFileRoutes();
    List<Route> normalRoutes = new ArrayList<>(List.of(
        Route.of(Methods.GET, "/", new BlockingHandler(fileHandler("/index.html")))
    ));

    normalRoutes.addAll(staticFileRoutes);

    return normalRoutes;
  }

  @Override
  public String version() {
    return "/api/v1";
  }

  private List<Route> staticFileRoutes() {
    String projectPath = System.getProperty("user.dir") + "/backend/target/classes/public";

    try {
      return Files.walk(Paths.get(V1Router.class.getResource("/public").toURI()))
          .filter(Files::isRegularFile)
          .filter(p -> !p.toString().contains("index.html"))
          .collect(toList()).stream()
          .map(p -> Route.of(Methods.GET, p.toString().replace(projectPath, ""), new BlockingHandler(fileHandler(p.toString().replace(projectPath, "")))))
          .collect(toList());
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private HttpHandler fileHandler(String filename) {
    return exchange -> {
      InputStream inputStream  = Application.class.getResourceAsStream(File.separator + "public" + filename);
      Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
      String html = scanner.hasNext() ? scanner.next() : "";
      exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, contentType(filename));
      exchange.getResponseSender().send(html);
    };
  }

  private String contentType(String filename) {
    if (filename.contains("favicon.ico") || filename.contains(".png")) {
      return "image/png";
    } else if (filename.contains(".css")) {
      return "text/css";
    } else if (filename.contains(".js")) {
      return "text/javascript";
    } else {
      return "text/html";
    }
  }
}
