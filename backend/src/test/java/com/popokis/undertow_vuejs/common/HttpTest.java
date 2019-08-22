package com.popokis.undertow_vuejs.common;

import com.popokis.popok.http.Server;
import com.popokis.undertow_vuejs.http.server.Router;
import io.undertow.util.StatusCodes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class HttpTest extends DatabaseTest {

  private static Server server = Server.builder(Router.router())
      .enableHttps("certificate/client.jks")
      .enableHttp2()
      .redirectToHttps(StatusCodes.TEMPORARY_REDIRECT)
      .build();
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
