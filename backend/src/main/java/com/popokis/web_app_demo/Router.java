package com.popokis.web_app_demo;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;

public final class Router {

  private Router() {}

  public static HttpHandler router() {
    return Handlers.path()
        // REST API path
//        .addPrefixPath("/api", Handlers.routing()
//            .get("/customers", exchange -> {...})
//            .delete("/customers/{customerId}", exchange -> {...})
//            .setFallbackHandler(exchange -> {...}))

        // Redirect /about to root path to serve the index.html where the SPA lives
        .addExactPath("/about", Handlers.redirect("/"))

        // Serve all static files from a folder
        .addPrefixPath("/", new ResourceHandler(
            new ClassPathResourceManager(Thread.currentThread().getContextClassLoader(), "public"))
            .setDirectoryListingEnabled(true));
  }
}
