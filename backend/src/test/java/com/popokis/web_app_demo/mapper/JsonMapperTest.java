package com.popokis.web_app_demo.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.popokis.web_app_demo.entity.ImmutableUser;
import com.popokis.web_app_demo.entity.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JsonMapperTest {

  @Test
  void jsonSerialization() throws JsonProcessingException {
    User user = ImmutableUser.of(1L, "soasada", "TEST", LocalDateTime.now(), LocalDateTime.now(), null);
    String jsonUser = JsonMapper.getInstance().mapper().writeValueAsString(user);
    assertFalse(jsonUser.contains("houses"));
  }

  @Test
  void jsonDeserialization() throws IOException {
    String jsonUser = "{\"id\":1,\"username\":\"soasada\",\"password\":\"TEST\",\"createdAt\":\"2019-03-31T13:24:07.23841\",\"updatedAt\":\"2019-03-31T13:24:07.239629\"}";
    User user = JsonMapper.getInstance().mapper().readValue(jsonUser, User.class);
    assertEquals(1L, user.uId());
    assertEquals("soasada", user.uUsername());
  }
}