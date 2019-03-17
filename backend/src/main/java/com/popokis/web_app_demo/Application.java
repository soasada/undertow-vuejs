package com.popokis.web_app_demo;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.PathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;

public final class Application {

  private static final char[] STORE_PASSWORD = "password".toCharArray();

  public static void main(String[] args) throws Exception {
    Undertow.builder()
        .addHttpListener(8080, "localhost")
        .addHttpsListener(8443, "localhost", createSSLContext(loadKeyStore("certificate/client.jks"), loadKeyStore("certificate/clienttrust.jks")))
        .setHandler(Handlers.path()
            // REST API path
//            .addPrefixPath("/api", Handlers.routing()
//                .get("/customers", exchange -> {...})
//                .delete("/customers/{customerId}", exchange -> {...})
//                .setFallbackHandler(exchange -> {...}))

            // Redirect root path to /static to serve the index.html by default
            .addExactPath("/about", Handlers.redirect("/"))

            // Serve all static files from a folder
            .addPrefixPath("/", new ResourceHandler(
                new PathResourceManager(
                    Paths.get(Application.class.getResource("/public").toURI()),
                    100))
                .setWelcomeFiles("index.html")
                .setDirectoryListingEnabled(true))
        ).build().start();
  }

  private static KeyStore loadKeyStore(String name) throws Exception {
    String storeLoc = System.getProperty(name);
    final InputStream stream;
    if(storeLoc == null) {
      stream = Application.class.getResourceAsStream(File.separator + name);
    } else {
      stream = Files.newInputStream(Paths.get(storeLoc));
    }

    if(stream == null) {
      throw new RuntimeException("Could not load keystore");
    }
    try(InputStream is = stream) {
      KeyStore loadedKeystore = KeyStore.getInstance("JKS");
      loadedKeystore.load(is, STORE_PASSWORD);
      return loadedKeystore;
    }
  }

  private static SSLContext createSSLContext(final KeyStore keyStore, final KeyStore trustStore) throws Exception {
    KeyManager[] keyManagers;
    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    keyManagerFactory.init(keyStore, STORE_PASSWORD);
    keyManagers = keyManagerFactory.getKeyManagers();

    TrustManager[] trustManagers;
    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    trustManagerFactory.init(trustStore);
    trustManagers = trustManagerFactory.getTrustManagers();

    SSLContext sslContext;
    sslContext = SSLContext.getInstance("TLSv1.3");
    sslContext.init(keyManagers, trustManagers, null);

    return sslContext;
  }
}
