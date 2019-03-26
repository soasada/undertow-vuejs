package com.popokis.web_app_demo.common;

import com.popokis.web_app_demo.db.Database;
import com.popokis.web_app_demo.db.Query;

import java.sql.PreparedStatement;

public final class BootstrapDatabase {

  private BootstrapDatabase() {}

  public static void setUp() {
    Database.executeInsert(query("INSERT INTO user (u_id, u_username, u_password) VALUES (1, 'soasada', 'test')"));
  }

  public static void setDown() {
    Database.executeDML(query("DELETE FROM user"));
  }

  private static Query query(String queryString) {
    return new Query() {
      @Override
      public String query() {
        return queryString;
      }

      @Override
      public void parameters(PreparedStatement stm) {

      }
    };
  }
}
