package com.popokis.web_app_demo.http.api;

import com.popokis.web_app_demo.http.server.Responses;
import com.popokis.web_app_demo.mapper.json.JsonMapper;
import com.popokis.web_app_demo.repository.UserRepository;
import io.undertow.server.HttpHandler;
import io.undertow.util.PathTemplateMatch;

public final class UserHandler {

  private UserHandler() {}

  public static HttpHandler all() {
    return exchange -> {
      String response = JsonMapper.getInstance().mapper().writeValueAsString(UserRepository.all());
      Responses.asJson(exchange, response);
    };
  }

  public static HttpHandler remove() {
    return exchange -> {
      PathTemplateMatch pathMatch = exchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY);
      long id = Long.parseLong(pathMatch.getParameters().get("id"));
      String response = JsonMapper.getInstance().mapper().writeValueAsString(UserRepository.delete(id));
      Responses.asJson(exchange, response);
    };
  }
}
