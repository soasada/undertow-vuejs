package com.popokis.web_app_demo.http.server;

import com.google.common.base.Stopwatch;
import com.popokis.web_app_demo.exception.Exceptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.AttachmentKey;
import io.undertow.util.HttpString;
import io.undertow.util.StatusCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.UUID;

public final class RequestHandler implements HttpHandler {

  public static final AttachmentKey<String> REQUEST_PAYLOAD = AttachmentKey.create(String.class);

  private final Logger logger;
  private final HttpHandler next;
  private final Stopwatch stopwatch;

  public RequestHandler(String loggerName, HttpHandler next, Stopwatch stopwatch) {
    this.logger = LogManager.getLogger(loggerName);
    this.next = next;
    this.stopwatch = stopwatch;
  }

  @Override
  public void handleRequest(HttpServerExchange exchange) throws Exception {
    stopwatch.start();
    exchange.getRequestReceiver().receiveFullString((fullExchange, data) -> {
      try {
        String correlationId = UUID.randomUUID().toString();
        fullExchange.getRequestHeaders().add(new HttpString("X-Correlation-Id"), correlationId);
        ThreadContext.put("requested-url", exchange.getRequestURL());
        ThreadContext.put("message-type", "REQUEST");
        ThreadContext.put("id", correlationId);
        logger.info(data);
        fullExchange.putAttachment(REQUEST_PAYLOAD, data);
        next.handleRequest(fullExchange);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }, (errorExchange, exception) -> {
      logger.error(Exceptions.rootCause(exception)); // TODO: enhance and log correctly exceptions
      errorExchange.setStatusCode(StatusCodes.BAD_REQUEST);
      errorExchange.getResponseSender().send("");
    });
  }
}
