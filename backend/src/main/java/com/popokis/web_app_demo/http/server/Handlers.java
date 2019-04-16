package com.popokis.web_app_demo.http.server;

import com.popokis.web_app_demo.mapper.json.JsonMapper;
import com.popokis.web_app_demo.mapper.json.JsonMappers;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.util.PathTemplateMatch;

import java.util.List;
import java.util.function.Function;

public final class Handlers {

  private Handlers() {}

  public static <R, S> HttpHandler bodyBased(Class<R> requestType, Function<R, S> f) {
    return new BlockingHandler(
        exchange -> {
          String jsonBody = Requests.body(exchange);
          R request = JsonMappers.model(jsonBody, requestType);
          S response = f.apply(request);
          Responses.asJson(exchange, JsonMapper.getInstance().toJson(response));
        }
    );
  }

  public static <S> HttpHandler idBased(Function<Long, S> f) {
    return new BlockingHandler(
        exchange -> {
          PathTemplateMatch pathMatch = exchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY);
          long id = Long.parseLong(pathMatch.getParameters().get("id"));
          S response = f.apply(id);
          Responses.asJson(exchange, JsonMapper.getInstance().toJson(response));
        }
    );
  }

  public static <S> HttpHandler listBased(Function<Void, List<S>> f) {
    return new BlockingHandler(
        exchange -> {
          List<S> response = f.apply(null);
          Responses.asJson(exchange, JsonMapper.getInstance().toJson(response));
        }
    );
  }
}
