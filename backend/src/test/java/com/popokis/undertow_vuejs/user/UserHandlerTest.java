package com.popokis.undertow_vuejs.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.popokis.undertow_vuejs.Application;
import com.popokis.undertow_vuejs.common.HttpTest;
import com.popokis.undertow_vuejs.common.SimpleClient;
import com.popokis.undertow_vuejs.login.Token;
import com.popokis.undertow_vuejs.mapper.JsonMapper;
import com.popokis.undertow_vuejs.mapper.JsonMappers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserHandlerTest extends HttpTest {

  @Test
  void findAllUsers() {
    String jsonResponse = SimpleClient.getInstance().get(address + "/users");
    List<User> users = JsonMappers.list(jsonResponse, User.class);
    assertEquals(4, users.size());
    assertEquals("soasada", users.get(0).getUsername());
    assertEquals("zyonx", users.get(1).getUsername());
    assertEquals("delete_house", users.get(2).getUsername());
  }

  @Test
  void createUser() {
    User createdUser = User.builder().username("TEST").password("TEST").build();
    String response = SimpleClient.getInstance().post(address + "/users", JsonMapper.getInstance().toJson(createdUser));
    long newId = Long.parseLong(response);
    assertTrue(newId > 0);
  }

  @Test
  void readUser() {
    String jsonResponse = SimpleClient.getInstance().get(address + "/users/1");
    User user = JsonMappers.model(jsonResponse, User.class);
    assertEquals(1L, user.getId());
    assertEquals("soasada", user.getUsername());
    assertEquals(0, user.getHouses().size());
  }

  @Test
  void updateUser() {
    User updatedUser = User.builder().id(1L).username("TEST").password("TEST").build();
    String response = SimpleClient.getInstance().put(address + "/users", JsonMapper.getInstance().toJson(updatedUser));
    int rowsAffected = Integer.parseInt(response);
    assertTrue(rowsAffected > 0);
  }

  @Test
  void deleteUser() {
    String response = SimpleClient.getInstance().delete(address + "/users/2");
    int rowsAffected = Integer.parseInt(response);
    assertTrue(rowsAffected > 0);
  }

  @Test
  void findUserHouses() {
    String jsonResponse = SimpleClient.getInstance().get(address + "/users/1/houses");
    User user = JsonMappers.model(jsonResponse, User.class);
    assertEquals(1L, user.getId());
    assertEquals("soasada", user.getUsername());
    assertEquals(2, user.getHouses().size());
    assertEquals(3, user.getHouses().get(0).getFurniture().size());
    assertEquals(2, user.getHouses().get(1).getFurniture().size());
  }

  @Test
  void correctLogin() {
    User userLogin = User.builder().username("TEST").password("TEST").build();
    String loginBody = JsonMapper.getInstance().toJson(userLogin);
    SimpleClient.getInstance().post(address + "/users", loginBody);
    String jsonResponse = SimpleClient.getInstance().unsecurePost("http://localhost:8081/api/login", loginBody);
    Token token = JsonMappers.model(jsonResponse, Token.class);
    JWT.require(Algorithm.HMAC512(Application.SECRET.getBytes())).build().verify(token.getToken());
  }

  @Test
  void incorrectLogin() {
    User userLogin = User.builder().username("TEST").password("TEST").build();
    String loginBody = JsonMapper.getInstance().toJson(userLogin);
    String jsonResponse = SimpleClient.getInstance().unsecurePost("http://localhost:8081/api/login", loginBody);
    assertEquals("Invalid username or password", jsonResponse);
  }
}