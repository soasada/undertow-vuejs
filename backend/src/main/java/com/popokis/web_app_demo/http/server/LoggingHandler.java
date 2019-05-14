package com.popokis.web_app_demo.http.server;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggingHandler implements HttpHandler {

  private final Logger logger;
  private final HttpHandler next;

  public LoggingHandler(String loggerName, HttpHandler next) {
    this.logger = LoggerFactory.getLogger(loggerName);
    this.next = next;
  }

  @Override
  public void handleRequest(HttpServerExchange exchange) throws Exception {
    exchange.getRequestReceiver().receiveFullString((fullExchange, data) -> {
      try {
        logger.info(data);
        next.handleRequest(fullExchange);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }, (errorExchange, exception) -> {
      errorExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
      errorExchange.getResponseSender().send(exception.getMessage());
    });
  }
}
