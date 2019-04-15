package com.popokis.web_app_demo.http.api;

import com.fasterxml.jackson.databind.JavaType;
import com.popokis.web_app_demo.common.BootstrapDatabase;
import com.popokis.web_app_demo.entity.User;
import com.popokis.web_app_demo.http.SimpleClient;
import com.popokis.web_app_demo.http.SimpleServer;
import com.popokis.web_app_demo.mapper.json.JsonMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserHandlerTest {

  private static SimpleServer server = new SimpleServer();

  @BeforeEach
  void setUp() {
    BootstrapDatabase.setUp();
    server.start();
  }

  @AfterEach
  void tearDown() {
    BootstrapDatabase.setDown();
    server.stop();
  }

  @Test
  void findAllUsers() throws IOException {
    String jsonResponse = SimpleClient.getInstance().get("https://localhost:8443/api/v1/users");
    JavaType listType = JsonMapper.getInstance().mapper().getTypeFactory().constructCollectionType(List.class, User.class);
    List<User> users = JsonMapper.getInstance().mapper().readValue(jsonResponse, listType);
    assertEquals(3, users.size());
    assertEquals("soasada", users.get(0).getUsername());
    assertEquals("zyonx", users.get(1).getUsername());
    assertEquals("delete_house", users.get(2).getUsername());
  }
}