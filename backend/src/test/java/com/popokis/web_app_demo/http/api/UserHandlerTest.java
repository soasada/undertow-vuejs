package com.popokis.web_app_demo.http.api;

import com.popokis.web_app_demo.common.HttpTest;
import com.popokis.web_app_demo.entity.User;
import com.popokis.web_app_demo.http.client.SimpleClient;
import com.popokis.web_app_demo.mapper.json.JsonMapper;
import com.popokis.web_app_demo.mapper.json.JsonMappers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserHandlerTest extends HttpTest {

  @Test
  void findAllUsers() {
    String jsonResponse = SimpleClient.getInstance().get(address + "/users");
    List<User> users = JsonMappers.list(jsonResponse, User.class);
    assertEquals(3, users.size());
    assertEquals("soasada", users.get(0).getUsername());
    assertEquals("zyonx", users.get(1).getUsername());
    assertEquals("delete_house", users.get(2).getUsername());
  }

  @Test
  void createUser() {
    User createdUser = User.builder().username("TEST").password("TEST").build();
    String response = SimpleClient.getInstance().post(address + "/users", JsonMapper.getInstance().toJson(createdUser));
    long newId = Long.parseLong(response);
    assertEquals(4L, newId);
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
  void login() {
    User userLogin = User.builder().username("TEST").password("TEST").build();
    String loginBody = JsonMapper.getInstance().toJson(userLogin);
    String response = SimpleClient.getInstance().post(address + "/users", loginBody);
    long newId = Long.parseLong(response);
    String jsonResponse = SimpleClient.getInstance().post(address + "/login", loginBody);
    User loggedUser = JsonMappers.model(jsonResponse, User.class);
    assertEquals(newId, loggedUser.getId());
    assertEquals(userLogin.hashPassword(), loggedUser.getPassword());
  }
}