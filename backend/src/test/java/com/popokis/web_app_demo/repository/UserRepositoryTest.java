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
  void getUser() {
    User user = UserRepository.find(1L);
    assertEquals(1L, user.getId());
    assertEquals("soasada", user.getUsername());
  }
}