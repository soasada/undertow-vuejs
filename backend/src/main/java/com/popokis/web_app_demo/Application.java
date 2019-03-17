package com.popokis.web_app_demo;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.PathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public final class Application {

  public static void main(String[] args) throws URISyntaxException {
    Undertow.builder().addHttpListener(8080, "localhost")
        .setHandler(Handlers.path()
            // REST API path
//            .addPrefixPath("/api", Handlers.routing()
//                .get("/customers", exchange -> {...})
//                .delete("/customers/{customerId}", exchange -> {...})
//                .setFallbackHandler(exchange -> {...}))

            // Redirect root path to /static to serve the index.html by default
//            .addExactPath("/", Handlers.redirect("/static"))

            // Serve all static files from a folder
            .addPrefixPath("/", new ResourceHandler(
                new PathResourceManager(
                    Paths.get(Application.class.getResource("/public").toURI()),
                    100))
                .setWelcomeFiles("index.html"))
        ).build().start();
  }
}
