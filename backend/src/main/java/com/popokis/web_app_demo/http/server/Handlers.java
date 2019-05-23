package com.popokis.web_app_demo.http.server;

import com.popokis.web_app_demo.exception.Exceptions;
import com.popokis.web_app_demo.mapper.json.JsonMapper;
import com.popokis.web_app_demo.mapper.json.JsonMappers;
import io.undertow.server.HttpHandler;
import io.undertow.util.PathTemplateMatch;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public final class Handlers {

  private Handlers() {}

  public static <R, S> HttpHandler bodyBased(Class<R> requestType, Function<R, S> f) {
    return exchange -> exchange.getRequestReceiver().receiveFullString((nextExchange, jsonBody) -> {
      nextExchange.dispatch(() -> CompletableFuture
          .supplyAsync(() -> JsonMappers.model(jsonBody, requestType))
          .thenApplyAsync(f)
          .whenCompleteAsync((response, e) -> {
                if (Objects.isNull(response)) {
                  Responses.serverError(nextExchange, Exceptions.rootCause(e).getMessage());
                } else {
                  Responses.asJson(nextExchange, JsonMapper.getInstance().toJson(response));
                }
              }
          )
      );
    });
  }

  public static <S> HttpHandler idBased(Function<Long, S> f) {
    return exchange -> exchange.dispatch(() -> CompletableFuture
        .supplyAsync(() -> exchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY))
        .thenApplyAsync(pathMatch -> Long.parseLong(pathMatch.getParameters().get("id")))
        .thenApplyAsync(f)
        .whenCompleteAsync((response, e) -> {
              if (Objects.isNull(response)) {
                Responses.serverError(exchange, Exceptions.rootCause(e).getMessage());
              } else {
                Responses.asJson(exchange, JsonMapper.getInstance().toJson(response));
              }
            }
        )
    );
  }

  public static <S> HttpHandler listBased(Function<Void, List<S>> f) {
    return exchange -> exchange.dispatch(() -> CompletableFuture
        .supplyAsync(() -> f.apply(null))
        .whenCompleteAsync((response, e) -> {
              if (Objects.isNull(response)) {
                Responses.serverError(exchange, Exceptions.rootCause(e).getMessage());
              } else {
                Responses.asJson(exchange, JsonMapper.getInstance().toJson(response));
              }
            }
        )
    );
  }
}
