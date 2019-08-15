package com.popokis.undertow_vuejs.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public final class HikariConnectionPool {

  private final DataSource dataSource;

  private HikariConnectionPool() {
    String env = System.getenv("APP_ENV");
    Properties properties = new Properties();
    String filename;

    if (Objects.isNull(env) || env.equalsIgnoreCase("test")) {
      filename = File.separator + "database/db_test_pool.properties";
    } else if (env.equalsIgnoreCase("prod") || env.equalsIgnoreCase("production")) {
      filename = File.separator + "database/db_prod_pool.properties";
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

  public DataSource getDataSource() {
    return dataSource;
  }
}
