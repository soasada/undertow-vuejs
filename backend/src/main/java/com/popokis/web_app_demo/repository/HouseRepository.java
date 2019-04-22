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
        return "SELECT * FROM house WHERE h_user_id = ? ORDER BY h_id ASC LIMIT 1000";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setLong(1, userId);
      }
    };

    return Database.executeQuery(query, ListMapper.of(mapper)).orElseGet(List::of);
  }

  public static long create(House house) {
    Query query = new Query() {
      @Override
      public String query() {
        return "INSERT INTO house (h_name, h_user_id) VALUES (?, ?)";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setString(1, house.getName());
        stm.setLong(2, house.getUserId());
      }
    };

    return Database.executeInsert(query);
  }

  public static House read(long id) {
    Query query = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM house WHERE h_id = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setLong(1, id);
      }
    };

    return Database.executeQuery(query, mapper).get();
  }

  public static int update(House house) {
    Query query = new Query() {
      @Override
      public String query() {
        return "UPDATE house SET h_name = ?, h_user_id = ? WHERE h_id = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setString(1, house.getName());
        stm.setLong(2, house.getUserId());
        stm.setLong(3, house.getId());
      }
    };

    return Database.executeDML(query);
  }

  public static int delete(long id) {
    Query query = new Query() {
      @Override
      public String query() {
        return "DELETE FROM house WHERE h_id = ? LIMIT 1";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setLong(1, id);
      }
    };

    return Database.executeDML(query);
  }
}
