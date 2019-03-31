package com.popokis.web_app_demo.db;

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
    String env = System.getenv("APP_ENV");
    Properties properties = new Properties();
    String filename;

    if (Objects.isNull(env) || env.equalsIgnoreCase("test")) {
      filename = File.separator + "db_test_pool.properties";
    } else if (env.equalsIgnoreCase("prod") || env.equalsIgnoreCase("production")) {
      filename = File.separator + "db_prod_pool.properties";
    } else {
      throw new RuntimeException("Please indicate APP_ENV var: 'prod' or 'test'");
    }

    try (InputStream fi = HikariConnectionPool.class.getResourceAsStream(filename)) {
      properties.load(fi);
    } catch (IOException e) {
      throw new RuntimeException(filename + " not found. Please create it inside resources folder.");
    }

    this.dataSource = new HikariDataSource(new HikariConfig(properties));
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
