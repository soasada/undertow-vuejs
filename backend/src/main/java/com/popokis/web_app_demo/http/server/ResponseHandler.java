package com.popokis.web_app_demo.http.server;

import com.google.common.base.Stopwatch;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.AttachmentKey;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.concurrent.TimeUnit;

public final class ResponseHandler implements HttpHandler {

  public static final AttachmentKey<String> RESPONSE_PAYLOAD = AttachmentKey.create(String.class);

  private final Stopwatch stopwatch;
  private final Logger logger;

  public ResponseHandler(Stopwatch stopwatch, Logger logger) {
    this.stopwatch = stopwatch;
    this.logger = logger;
  }

  @Override
  public void handleRequest(HttpServerExchange exchange) throws Exception {
    long elapsedTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
    ThreadContext.put("message-type", "RESPONSE");
    ThreadContext.put("elapsed-time", String.valueOf((double) elapsedTime / 1000));
    logger.info(exchange.getAttachment(RESPONSE_PAYLOAD));
    ThreadContext.clearMap();
    Responses.asJson(exchange, exchange.getAttachment(RESPONSE_PAYLOAD));
  }
}
