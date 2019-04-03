package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.common.BootstrapDatabase;
import com.popokis.web_app_demo.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRepositoryTest {

  @BeforeEach
  void setUp() {
    BootstrapDatabase.setUp();
  }

  @AfterEach
  void tearDown() {
    BootstrapDatabase.setDown();
  }

  @Test
  void findUser() {
    User user = UserRepository.find(1L);
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
    assertEquals(2, user.getHouses().get(0).getFurniture().size());
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
    assertEquals(2, users.size());
    assertEquals("soasada", users.get(0).getUsername());
    assertEquals("zyonx", users.get(1).getUsername());
  }
}