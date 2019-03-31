package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.db.Database;
import com.popokis.web_app_demo.db.Query;
import com.popokis.web_app_demo.entity.User;
import com.popokis.web_app_demo.mapper.db.UserMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class UserRepository {

  private UserRepository() {}

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
}
