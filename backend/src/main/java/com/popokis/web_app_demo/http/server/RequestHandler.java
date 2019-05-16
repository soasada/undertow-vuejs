package com.popokis.web_app_demo.http.server;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Stopwatch;
import com.popokis.web_app_demo.exception.Exceptions;
import com.popokis.web_app_demo.mapper.json.JsonMapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.AttachmentKey;
import io.undertow.util.HttpString;
import io.undertow.util.StatusCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.message.Message;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
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
    String correlationId = UUID.randomUUID().toString();
    exchange.getRequestReceiver().receiveFullString((fullExchange, data) -> {
      try {
        fullExchange.getRequestHeaders().add(new HttpString("X-Correlation-Id"), correlationId);
        ThreadContext.put("requested-url", exchange.getRequestURL());
        ThreadContext.put("message-type", "REQUEST");
        ThreadContext.put("id", correlationId);
        logger.info(data);
        fullExchange.putAttachment(REQUEST_PAYLOAD, data);
        next.handleRequest(fullExchange);
      } catch (Exception e) {
        exceptionTermination(fullExchange, e, correlationId);
      }
    }, (errorExchange, exception) -> exceptionTermination(errorExchange, exception, correlationId));
  }

  private void exceptionTermination(HttpServerExchange exchange, Throwable exception, String correlationId) {
    Writer stackTraceStringWriter = new StringWriter();
    PrintWriter stackTracePrintWriter = new PrintWriter(stackTraceStringWriter);
    String exceptionMessage = Exceptions.rootCause(exception).getMessage();

    exception.printStackTrace(stackTracePrintWriter);
    logger.error(jsonErrorMessage(correlationId, stackTraceStringWriter.toString()));

    exchange.setStatusCode(StatusCodes.BAD_REQUEST);
    exchange.getResponseSender().send(exceptionMessage);
  }

  private Message jsonErrorMessage(String correlationId, String exceptionMessage) {
    JsonNodeFactory jsonNodeFactory = new JsonNodeFactory(false);
    ObjectNode jsonError = new ObjectNode(jsonNodeFactory);
    jsonError.set("id", jsonError.textNode(correlationId));
    jsonError.set("msg", jsonError.textNode(exceptionMessage));
    return new JsonMessageError(jsonError);
  }

  private static class JsonMessageError implements Message {

    private final Object object;

    public JsonMessageError(final Object object) {
      this.object = object;
    }

    @Override
    public String getFormattedMessage() {
      return JsonMapper.getInstance().toJson(object);
    }

    @Override
    public String getFormat() {
      return object.toString();
    }

    @Override
    public Object[] getParameters() {
      return new Object[]{object};
    }

    @Override
    public Throwable getThrowable() {
      return null;
    }
  }
}
