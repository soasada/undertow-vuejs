package com.popokis.web_app_demo;

import io.undertow.Undertow;
import io.undertow.util.Headers;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

public final class Application {

  public static void main(String[] args) {
    InputStream inputStream  = Application.class.getResourceAsStream(File.separator + "public" + File.separator + "index.html");
    Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
    String html = scanner.hasNext() ? scanner.next() : "";
    System.out.println(html);

    Undertow server = Undertow.builder()
        .addHttpListener(8080, "localhost")
        .setHandler(exchange -> {
          exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
          exchange.getResponseSender().send(html);
        }).build();
    server.start();
  }
}
