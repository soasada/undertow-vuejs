package com.popokis.web_app_demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public final class HikariConnectionPool {

  private final HikariDataSource dataSource;

  private HikariConnectionPool() {
    HikariConfig hikariConfig;
    String env = System.getenv("APP_ENV");
    Properties properties = new Properties();

    if (Objects.isNull(env) || env.equals("test")) {
      try (InputStream fi = HikariConnectionPool.class.getResourceAsStream(File.separator + "test_pool.properties")) {
        properties.load(fi);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      hikariConfig = new HikariConfig(properties);
    } else {
      try (InputStream fi = HikariConnectionPool.class.getResourceAsStream(File.separator + "prod_pool.properties")) {
        properties.load(fi);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      hikariConfig = new HikariConfig(properties);
    }

    this.dataSource = new HikariDataSource(hikariConfig);
  }

  private static class Holder {
    private static final HikariConnectionPool INSTANCE = new HikariConnectionPool();
  }

  public static HikariConnectionPool getInstance() {
    return Holder.INSTANCE;
  }

  public Connection getConnection() {
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
