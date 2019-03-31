package com.popokis.web_app_demo.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.popokis.web_app_demo.common.BootstrapDatabase;
import com.popokis.web_app_demo.entity.User;
import com.popokis.web_app_demo.mapper.JsonMapper;
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
  void getUser() throws JsonProcessingException {
    User user = UserRepository.find(1L);
    System.out.println(JsonMapper.getInstance().mapper().writeValueAsString(user));
    assertEquals(1L, user.uId());
    assertEquals("soasada", user.uUsername());
  }
}