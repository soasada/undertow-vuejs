package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.common.BootstrapDatabase;
import com.popokis.web_app_demo.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    assertEquals(1L, user.id());
    assertEquals("soasada", user.username());
    assertEquals(0, user.houses().size());
  }

  @Test
  void findUserHouses() {
    User user = UserRepository.findUserHouses(1L);
    assertEquals(1L, user.id());
    assertEquals("soasada", user.username());
    assertEquals(2, user.houses().size());
    assertEquals(2, user.houses().get(0).furniture().size());
    assertEquals(2, user.houses().get(1).furniture().size());
  }

  @Test
  void findUserHousesWithoutHouses() {
    User user = UserRepository.findUserHouses(2L);
    assertEquals(2L, user.id());
    assertEquals("zyonx", user.username());
    assertEquals(0, user.houses().size());
  }
}