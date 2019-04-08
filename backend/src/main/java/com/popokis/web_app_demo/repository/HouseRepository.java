package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.Application;
import com.popokis.web_app_demo.db.Database;
import com.popokis.web_app_demo.db.JdbcMapper;
import com.popokis.web_app_demo.db.ListMapper;
import com.popokis.web_app_demo.db.Query;
import com.popokis.web_app_demo.entity.House;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class HouseRepository {

  private static final JdbcMapper<House> mapper = Application.getMapper(House.class);

  private HouseRepository() {}

  public static List<House> all(long userId) {
    Query query = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM house WHERE h_user_id = ? ORDER BY h_id ASC LIMIT 100";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setLong(1, userId);
      }
    };

    return Database.executeQuery(query, ListMapper.of(mapper)).get();
  }
}
