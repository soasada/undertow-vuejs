package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.common.DatabaseTest;
import com.popokis.web_app_demo.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepositoryTest extends DatabaseTest {

  @Test
  void findUser() {
    User user = UserRepository.read(1L);
    assertEquals(1L, user.getId());
    assertEquals("soasada", user.getUsername());
    assertEquals(0, user.getHouses().size());
  }

  @Test
  void findUserHouses() {
    User user = UserRepository.findUserHouses(1L);
    assertEquals(1L, user.getId());
    assertEquals("soasada", user.getUsername());
    assertEquals(2, user.getHouses().size());
    assertEquals(3, user.getHouses().get(0).getFurniture().size());
    assertEquals(2, user.getHouses().get(1).getFurniture().size());
  }

  @Test
  void findUserHousesWithoutHouses() {
    User user = UserRepository.findUserHouses(2L);
    assertEquals(2L, user.getId());
    assertEquals("zyonx", user.getUsername());
    assertEquals(0, user.getHouses().size());
  }

  @Test
  void findAllUsers() {
    List<User> users = UserRepository.all();
    assertEquals(3, users.size());
    assertEquals("soasada", users.get(0).getUsername());
    assertEquals("zyonx", users.get(1).getUsername());
    assertEquals("delete_house", users.get(2).getUsername());
  }

  @Test
  void insertUser() {
    User createdUser = User.builder().username("TEST").password("TEST").build();
    long newId = UserRepository.create(createdUser);
    assertEquals(4L, newId);
  }

  @Test
  void updateUser() {
    User updatedUser = User.builder().id(1L).username("TEST").password("TEST").build();
    int rowsAffected = UserRepository.update(updatedUser);
    assertTrue(rowsAffected > 0);
  }

  @Test
  void deleteUser() {
    int rowsAffected = UserRepository.delete(2L);
    assertTrue(rowsAffected > 0);
  }

  @Test
  void loginUser() {
    User createdUser = User.builder().username("TEST").password("TEST").build();
    long newId = UserRepository.create(createdUser);
    User loggedUser = UserRepository.login(createdUser);
    assertEquals(newId, loggedUser.getId());
  }
}