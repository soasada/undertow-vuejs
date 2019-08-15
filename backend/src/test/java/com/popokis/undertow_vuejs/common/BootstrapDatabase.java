package com.popokis.undertow_vuejs.common;

import com.popokis.popok.sql_db.Database;
import com.popokis.popok.sql_db.Query;
import com.popokis.undertow_vuejs.db.HikariConnectionPool;
import com.popokis.undertow_vuejs.user.User;
import com.popokis.undertow_vuejs.user.UserRepository;

import java.sql.PreparedStatement;

public final class BootstrapDatabase {

  private static final Database db = Database.create(HikariConnectionPool.getInstance().getDataSource());

  private BootstrapDatabase() {}

  public static void setUp() {
    db.executeInsert(query("INSERT INTO user (u_id, u_username, u_password) VALUES (1, 'soasada', 'test')"));
    db.executeInsert(query("INSERT INTO house (h_id, h_name, h_user_id) VALUES (1, 'soasadaHouseá', 1)"));
    db.executeInsert(query("INSERT INTO house (h_id, h_name, h_user_id) VALUES (2, 'soasadaHouse2', 1)"));
    db.executeInsert(query("INSERT INTO furniture (f_id, f_name, f_type, f_house_id) VALUES (1, 'table', 'wood', 1)"));
    db.executeInsert(query("INSERT INTO furniture (f_id, f_name, f_type, f_house_id) VALUES (2, 'chair', 'wood', 1)"));
    db.executeInsert(query("INSERT INTO furniture (f_id, f_name, f_type, f_house_id) VALUES (3, 'TV', 'electronic', 2)"));
    db.executeInsert(query("INSERT INTO furniture (f_id, f_name, f_type, f_house_id) VALUES (4, 'sofa', 'misc', 2)"));

    db.executeInsert(query("INSERT INTO user (u_id, u_username, u_password) VALUES (2, 'zyonx', 'test2')"));
    db.executeInsert(query("INSERT INTO user (u_id, u_username, u_password) VALUES (3, 'delete_house', 'test3')"));
    db.executeInsert(query("INSERT INTO house (h_id, h_name, h_user_id) VALUES (3, 'delete_house', 3)"));
    db.executeInsert(query("INSERT INTO furniture (f_id, f_name, f_type, f_house_id) VALUES (5, 'lamp', 'electronic', 2)"));

    UserRepository.create(User.builder().username("admin").password("admin").build());
  }

  public static void setDown() {
    db.executeDML(query("DELETE FROM furniture"));
    db.executeDML(query("ALTER TABLE furniture AUTO_INCREMENT = 1"));
    db.executeDML(query("DELETE FROM house"));
    db.executeDML(query("ALTER TABLE house AUTO_INCREMENT = 1"));
    db.executeDML(query("DELETE FROM user"));
    db.executeDML(query("ALTER TABLE user AUTO_INCREMENT = 1"));
  }

  private static Query query(String queryString) {
    return new Query() {
      @Override
      public String query() {
        return queryString;
      }

      @Override
      public void parameters(PreparedStatement stm) {}
    };
  }
}
