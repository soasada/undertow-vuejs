package com.popokis.web_app_demo.http;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.attribute.ExchangeAttributes;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;

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
import java.util.Objects;
import java.util.Properties;

import static io.undertow.Handlers.predicate;
import static io.undertow.predicate.Predicates.secure;

public final class SimpleServer {

  private static final char[] STORE_PASSWORD = "password".toCharArray();

  private final Undertow server;

  public SimpleServer() {
    Properties appProps = new Properties();

    try (InputStream fi = SimpleServer.class.getResourceAsStream(File.separator + "app.properties")) {
      appProps.load(fi);
    } catch (IOException e) {
      throw new RuntimeException(File.separator + "app.properties not found, please create it inside resources folder.");
    }

    String httpPort = appProps.getProperty("server.http.port");
    if (Objects.isNull(httpPort)) throw new RuntimeException("server.http.port property not found.");
    String httpsPort = appProps.getProperty("server.https.port");
    if (Objects.isNull(httpsPort)) throw new RuntimeException("server.https.port property not found.");
    String address = appProps.getProperty("server.address");
    if (Objects.isNull(address)) throw new RuntimeException("server.address property not found.");

    this.server = Undertow.builder()
        .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
        .addHttpListener(Integer.parseInt(httpPort), address)
        .addHttpsListener(Integer.parseInt(httpsPort), address, createSSLContext(loadKeyStore("certificate/client.jks"), loadKeyStore("certificate/clienttrust.jks")))
        .setHandler(Handlers.header(predicate(secure(), Router.router(), (exchange) -> {
              exchange.getResponseHeaders().add(Headers.LOCATION, "https://" + exchange.getHostName() + ":" + (exchange.getHostPort() + 363) + exchange.getRelativePath());
              exchange.setStatusCode(StatusCodes.TEMPORARY_REDIRECT);
            }), "x-undertow-transport", ExchangeAttributes.transportProtocol())
        ).build();
  }

  public void start() {
    server.start();
  }

  public void stop() {
    server.stop();
  }

  private KeyStore loadKeyStore(String name) {
    try (InputStream is = SimpleServer.class.getResourceAsStream(File.separator + name)) {
      KeyStore loadedKeystore = KeyStore.getInstance("JKS");
      loadedKeystore.load(is, STORE_PASSWORD);
      return loadedKeystore;
    } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
      throw new RuntimeException(e);
    }
  }

  private SSLContext createSSLContext(final KeyStore keyStore, final KeyStore trustStore) {
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
