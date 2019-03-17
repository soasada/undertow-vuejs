package com.popokis.web_app_demo;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public final class Application {

  private static final char[] STORE_PASSWORD = "password".toCharArray();

  public static void main(String[] args) {
    Undertow.builder()
        .addHttpListener(8080, "localhost")
//        .addHttpsListener(8443, "localhost", createSSLContext(loadKeyStore("certificate/client.jks"), loadKeyStore("certificate/clienttrust.jks")))
        .setHandler(Handlers.path()
                // REST API path
//            .addPrefixPath("/api", Handlers.routing()
//                .get("/customers", exchange -> {...})
//                .delete("/customers/{customerId}", exchange -> {...})
//                .setFallbackHandler(exchange -> {...}))

                // Redirect /about to root path to serve the index.html where the SPA lives
                .addExactPath("/about", Handlers.redirect("/"))

                // Serve all static files from a folder
                .addPrefixPath("/", new ResourceHandler(
                    new ClassPathResourceManager(Thread.currentThread().getContextClassLoader(), "public"))
                    .setDirectoryListingEnabled(true))
        ).build().start();
  }

  private static KeyStore loadKeyStore(String name) {
    try (InputStream is = Application.class.getResourceAsStream(File.separator + name)) {
      KeyStore loadedKeystore = KeyStore.getInstance("JKS");
      loadedKeystore.load(is, STORE_PASSWORD);
      return loadedKeystore;
    } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
      throw new RuntimeException(e);
    }
  }

  private static SSLContext createSSLContext(final KeyStore keyStore, final KeyStore trustStore) {
    try {
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
    } catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
      throw new RuntimeException(e);
    }
  }
}
