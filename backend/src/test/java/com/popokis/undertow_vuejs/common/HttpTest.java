package com.popokis.undertow_vuejs.common;

import com.popokis.popok.http.Server;
import com.popokis.undertow_vuejs.http.server.Router;
import io.undertow.util.StatusCodes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class HttpTest extends DatabaseTest {

  private static Server server = Server.builder(Router.router())
      .build();
  protected String address = "http://localhost:8081/api/v1";

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
