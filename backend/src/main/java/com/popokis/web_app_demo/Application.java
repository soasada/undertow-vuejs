package com.popokis.web_app_demo;

import com.popokis.web_app_demo.http.SimpleServer;

public final class Application {

  public static void main(String[] args) {
    SimpleServer server = new SimpleServer();
    server.start();
  }
}
