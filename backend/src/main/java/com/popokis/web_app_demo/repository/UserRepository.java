package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.db.Database;
import com.popokis.web_app_demo.db.Query;
import com.popokis.web_app_demo.entity.User;
import org.simpleflatmapper.jdbc.JdbcMapper;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;

import javax.sql.rowset.CachedRowSet;
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

    CachedRowSet rowSet = Database.executeQuery(findQuery);
    JdbcMapper<User> mapper = JdbcMapperFactory.newInstance()
        .addAlias("u_id", "id")
        .addAlias("u_username", "username")
        .addAlias("u_password", "password")
        .addAlias("u_created_at", "createdAt")
        .addAlias("u_updated_at", "updatedAt")
        .newMapper(User.class);
    try {
      rowSet.next();
      return mapper.map(rowSet);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
