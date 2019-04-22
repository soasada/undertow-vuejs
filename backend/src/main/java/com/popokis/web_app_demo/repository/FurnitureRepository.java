package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.Application;
import com.popokis.web_app_demo.db.Database;
import com.popokis.web_app_demo.db.JdbcMapper;
import com.popokis.web_app_demo.db.ListMapper;
import com.popokis.web_app_demo.db.Query;
import com.popokis.web_app_demo.entity.Furniture;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class FurnitureRepository {

  private static final JdbcMapper<Furniture> mapper = Application.getMapper(Furniture.class);

  private FurnitureRepository() {}

  public static List<Furniture> all(long houseId) {
    Query query = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM furniture WHERE f_house_id = ? ORDER BY f_id ASC LIMIT 1000";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setLong(1, houseId);
      }
    };

    return Database.executeQuery(query, ListMapper.of(mapper)).orElseGet(List::of);
  }

  public static long create(Furniture furniture) {
    Query query = new Query() {
      @Override
      public String query() {
        return "INSERT INTO furniture (f_name, f_type, f_house_id) VALUES (?, ?, ?)";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setString(1, furniture.getName());
        stm.setString(2, furniture.getType());
        stm.setLong(3, furniture.getHouseId());
      }
    };

    return Database.executeInsert(query);
  }

  public static Furniture read(long id) {
    Query query = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM furniture WHERE f_id = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setLong(1, id);
      }
    };

    return Database.executeQuery(query, mapper).get();
  }

  public static int update(Furniture furniture) {
    Query query = new Query() {
      @Override
      public String query() {
        return "UPDATE furniture SET f_name = ?, f_type = ?, f_house_id = ? WHERE f_id = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setString(1, furniture.getName());
        stm.setString(2, furniture.getType());
        stm.setLong(3, furniture.getHouseId());
        stm.setLong(4, furniture.getId());
      }
    };

    return Database.executeDML(query);
  }

  public static int delete(long id) {
    Query query = new Query() {
      @Override
      public String query() {
        return "DELETE FROM furniture WHERE f_id = ? LIMIT 1";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setLong(1, id);
      }
    };

    return Database.executeDML(query);
  }
}
