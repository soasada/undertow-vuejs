package com.popokis.web_app_demo.http.server;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.async.NonBlockingInputFeeder;
import com.google.common.base.Stopwatch;
import com.popokis.web_app_demo.exception.Exceptions;
import com.popokis.web_app_demo.mapper.json.JsonMapper;
import com.popokis.web_app_demo.mapper.json.JsonMappers;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.util.PathTemplateMatch;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public final class Handlers {

  private Handlers() {}

  public static <R, S> HttpHandler bodyBased(Class<R> requestType, Function<R, S> f, Logger logger) {
    return new BlockingHandler(
        exchange -> {
          try {
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser parser = jsonFactory.createNonBlockingByteArrayParser();
            NonBlockingInputFeeder feeder = parser.getNonBlockingInputFeeder();
            String jsonBody = Requests.body(exchange);
            R request = JsonMappers.model(jsonBody, requestType);
            S response = f.apply(request);
            String jsonResponse = JsonMapper.getInstance().toJson(response);
            Responses.asJson(exchange, jsonResponse);
          } catch (Exception e) {
            long elapsedTime = requestStopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            Responses.asJson(exchange, Exceptions.rootCause(e).getMessage(), logger, elapsedTime);
          }
        }
    );
  }

  public static <S> HttpHandler idBased(Function<Long, S> f, Logger logger) {
    return new BlockingHandler(
        exchange -> {
          Stopwatch requestStopwatch = Stopwatch.createStarted();
          try {
            PathTemplateMatch pathMatch = exchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY);
            long id = Long.parseLong(pathMatch.getParameters().get("id"));
            S response = f.apply(id);
            long elapsedTime = requestStopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            String jsonResponse = JsonMapper.getInstance().toJson(response);
            Responses.asJson(exchange, jsonResponse, logger, elapsedTime);
          } catch (Exception e) {
            long elapsedTime = requestStopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            Responses.asJson(exchange, Exceptions.rootCause(e).getMessage(), logger, elapsedTime);
          }
        }
    );
  }

  public static <S> HttpHandler listBased(Function<Void, List<S>> f, Logger logger) {
    return new BlockingHandler(
        exchange -> {
          Stopwatch requestStopwatch = Stopwatch.createStarted();
          try {
            List<S> response = f.apply(null);
            long elapsedTime = requestStopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            String jsonResponse = JsonMapper.getInstance().toJson(response);
            Responses.asJson(exchange, jsonResponse, logger, elapsedTime);
          } catch (Exception e) {
            long elapsedTime = requestStopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            Responses.asJson(exchange, Exceptions.rootCause(e).getMessage(), logger, elapsedTime);
          }
        }
    );
  }
}
