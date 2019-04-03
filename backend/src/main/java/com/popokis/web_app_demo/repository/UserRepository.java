package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.db.Database;
import com.popokis.web_app_demo.db.ListMapper;
import com.popokis.web_app_demo.db.Query;
import com.popokis.web_app_demo.entity.User;
import com.popokis.web_app_demo.mapper.db.FindUserHousesMapper;
import com.popokis.web_app_demo.mapper.db.UserMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class UserRepository {

  private UserRepository() {}

  public static User findUserHouses(long id) {
    Query findQuery = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM user " +
            "LEFT JOIN house ON u_id = h_user_id " +
            "LEFT JOIN furniture ON h_id = f_house_id " +
            "WHERE u_id = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) {
        try {
          stm.setLong(1, id);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    };

    return Database.executeQuery(findQuery, new FindUserHousesMapper()).get();
  }

  public static User find(long id) {
    Query findQuery = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM user WHERE u_id = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) {
        try {
          stm.setLong(1, id);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    };

    return Database.executeQuery(findQuery, new UserMapper()).get();
  }

  public static List<User> all() {
    Query findQuery = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM user LIMIT 100";
      }

      @Override
      public void parameters(PreparedStatement stm) {}
    };

    return Database.executeQuery(findQuery, ListMapper.of(new UserMapper())).get();
  }
}
