package com.popokis.undertow_vuejs.common;

import com.popokis.undertow_vuejs.http.server.Router;
import com.popokis.undertow_vuejs.http.server.SimpleServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class HttpTest extends DatabaseTest {

  private static SimpleServer server = new SimpleServer(Router.withHttpsRedirect(Router.router()));
  protected String address = "http://localhost:8080/api/v1";

  @BeforeEach
  protected void setUp() {
    super.setUp();
    server.start();
  }

  @AfterEach
  protected void tearDown() {
    super.tearDown();
    server.stop();
  }
}
