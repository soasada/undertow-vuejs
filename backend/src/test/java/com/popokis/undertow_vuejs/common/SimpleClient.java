package com.popokis.undertow_vuejs.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.popokis.undertow_vuejs.login.Token;
import com.popokis.undertow_vuejs.mapper.JsonMapper;
import com.popokis.undertow_vuejs.mapper.JsonMappers;
import com.popokis.undertow_vuejs.user.User;

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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

// HTTP Client for testing purpose
public final class SimpleClient {

  private static final Map<String, String> TOKEN_CACHE = new ConcurrentHashMap<>();
  private static final String LOGIN_ENDPOINT = "http://localhost:8080/api/login";
  private static final String USERNAME = "admin";
  private static final String PASSWORD = "admin";

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
        .header("Authorization", "Bearer " + loadToken())
        .GET()
        .build();

    return httpRequest(request);
  }

  public String post(String url, String jsonBody) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(timeout)
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + loadToken())
        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
        .build();

    return httpRequest(request);
  }

  public String unsecurePost(String url, String jsonBody) {
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
        .header("Authorization", "Bearer " + loadToken())
        .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
        .build();

    return httpRequest(request);
  }

  public String delete(String url) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(timeout)
        .header("Authorization", "Bearer " + loadToken())
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

  private String loadToken() {
    String TOKEN_KEY = USERNAME + PASSWORD;
    if (TOKEN_CACHE.containsKey(TOKEN_KEY)) {
      String token = TOKEN_CACHE.get(TOKEN_KEY);
      DecodedJWT decodedJWT = JWT.decode(token);
      LocalDateTime expirationDate = decodedJWT.getExpiresAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
      if (expirationDate.isAfter(LocalDateTime.now())) {
        return TOKEN_CACHE.get(TOKEN_KEY);
      } else {
        TOKEN_CACHE.put(TOKEN_KEY, login());
        return TOKEN_CACHE.get(TOKEN_KEY);
      }
    } else {
      TOKEN_CACHE.put(TOKEN_KEY, login());
      return TOKEN_CACHE.get(TOKEN_KEY);
    }
  }

  private String login() {
    User userLogin = User.builder().username(USERNAME).password(PASSWORD).build();
    String loginBody = JsonMapper.getInstance().toJson(userLogin);
    String jsonResponse = unsecurePost(LOGIN_ENDPOINT, loginBody);
    return JsonMappers.model(jsonResponse, Token.class).getToken();
  }
}
