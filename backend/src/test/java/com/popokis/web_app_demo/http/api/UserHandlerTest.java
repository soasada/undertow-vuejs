package com.popokis.web_app_demo.http.api;

import com.popokis.web_app_demo.common.BootstrapDatabase;
import com.popokis.web_app_demo.entity.User;
import com.popokis.web_app_demo.http.client.SimpleClient;
import com.popokis.web_app_demo.http.server.SimpleServer;
import com.popokis.web_app_demo.mapper.json.JsonMappers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
  void findAllUsers() {
    String jsonResponse = SimpleClient.getInstance().get("http://localhost:8080/api/v1/users");
    List<User> users = JsonMappers.list(jsonResponse, User.class);
    assertEquals(3, users.size());
    assertEquals("soasada", users.get(0).getUsername());
    assertEquals("zyonx", users.get(1).getUsername());
    assertEquals("delete_house", users.get(2).getUsername());
  }

  @Test
  void deleteUser() {
    String response = SimpleClient.getInstance().delete("http://localhost:8080/api/v1/users/2");
    int rowsAffected = Integer.parseInt(response);
    assertTrue(rowsAffected > 0);
  }
}