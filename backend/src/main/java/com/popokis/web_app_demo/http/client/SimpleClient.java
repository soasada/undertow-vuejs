package com.popokis.web_app_demo.http.client;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Properties;

public final class SimpleClient {

  private final Duration timeout;
  private final HttpClient httpClient;

  private SimpleClient() {
    try {
      // FIXME: This should not be used in production.
      final Properties props = System.getProperties();
      props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());
      this.timeout = Duration.ofMinutes(2);
      SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
      sslContext.init(null, trustAllCerts, new SecureRandom());
      this.httpClient = HttpClient.newBuilder()
          .connectTimeout(timeout)
          .sslContext(sslContext)
          .followRedirects(HttpClient.Redirect.ALWAYS)
          .build();
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      throw new RuntimeException(e);
    }
  }

  private static class Holder {
    private static final SimpleClient INSTANCE = new SimpleClient();
  }

  public static SimpleClient getInstance() {
    return Holder.INSTANCE;
  }

  public String get(String url) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(timeout)
        .GET()
        .build();

    return httpRequest(request);
  }

  public String post(String url, String jsonBody) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(timeout)
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
        .build();

    return httpRequest(request);
  }

  public String put(String url, String jsonBody) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(timeout)
        .header("Content-Type", "application/json")
        .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
        .build();

    return httpRequest(request);
  }

  public String delete(String url) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(timeout)
        .DELETE()
        .build();

    return httpRequest(request);
  }

  private String httpRequest(HttpRequest request) {
    try {
      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      return response.body();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  // FIXME: This should not be used in production.
  private static TrustManager[] trustAllCerts = new TrustManager[]{
      new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
          return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }
      }
  };
}
