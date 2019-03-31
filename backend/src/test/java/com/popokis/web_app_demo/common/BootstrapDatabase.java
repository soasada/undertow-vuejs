package com.popokis.web_app_demo.common;

import com.popokis.web_app_demo.db.Database;
import com.popokis.web_app_demo.db.Query;

import java.sql.PreparedStatement;

public final class BootstrapDatabase {

  private BootstrapDatabase() {}

  public static void setUp() {
    Database.executeInsert(query("INSERT INTO user (u_id, u_username, u_password) VALUES (1, 'soasada', 'test')"));
    Database.executeInsert(query("INSERT INTO house (h_id, h_name, h_user_id) VALUES (1, 'soasadaHouse', 1)"));
    Database.executeInsert(query("INSERT INTO house (h_id, h_name, h_user_id) VALUES (2, 'soasadaHouse2', 1)"));
    Database.executeInsert(query("INSERT INTO furniture (f_id, f_name, f_type, f_house_id) VALUES (1, 'table', 'wood', 1)"));
    Database.executeInsert(query("INSERT INTO furniture (f_id, f_name, f_type, f_house_id) VALUES (2, 'chair', 'wood', 1)"));
    Database.executeInsert(query("INSERT INTO furniture (f_id, f_name, f_type, f_house_id) VALUES (3, 'TV', 'electronic', 2)"));
    Database.executeInsert(query("INSERT INTO furniture (f_id, f_name, f_type, f_house_id) VALUES (4, 'sofa', 'misc', 2)"));
  }

  public static void setDown() {
    Database.executeDML(query("DELETE FROM furniture"));
    Database.executeDML(query("DELETE FROM house"));
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
