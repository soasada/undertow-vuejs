package com.popokis.web_app_demo.http.server;

import com.popokis.web_app_demo.http.api.UserHandler;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.encoding.EncodingHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;

public final class Router {

  private Router() {}

  public static HttpHandler router() {
    return Handlers.path()
        // HTTP based API
        .addPrefixPath("/api/v1", Handlers.routing()
            // User resources
            .get("/users", UserHandler.all())
            .post("/users", UserHandler.create())
            .get("/users/{id}", UserHandler.read())
            .put("/users", UserHandler.update())
            .delete("/users/{id}", UserHandler.remove())
            .get("/users/{id}/houses", UserHandler.findUserHouses())
            .post("/login", UserHandler.login())

            // Health Checking
            .get("/health", Responses::ok)
            .setFallbackHandler(Responses::notFound))

        // Redirect /about to root path to serve the index.html where the SPA lives
        .addExactPath("/about", Handlers.redirect("/"))

        // Serve all static files from a folder
        .addPrefixPath("/", new EncodingHandler.Builder().build(null).wrap(new ResourceHandler(
            new ClassPathResourceManager(Thread.currentThread().getContextClassLoader(), "public"))
            .setDirectoryListingEnabled(true)));
  }
}
