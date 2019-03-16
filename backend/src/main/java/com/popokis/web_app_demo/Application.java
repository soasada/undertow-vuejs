package com.popokis.web_app_demo;

public final class Application {

  public static void main(String[] args) {
    SimpleServer simpleServer = new SimpleServer(8080, "localhost", new V1Router());
    simpleServer.start();
  }
}
