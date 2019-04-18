package com.popokis.web_app_demo.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class DatabaseTest {

  @BeforeEach
  protected void setUp() {
    BootstrapDatabase.setUp();
  }

  @AfterEach
  protected void tearDown() {
    BootstrapDatabase.setDown();
  }
}
