package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.db.Database;
import com.popokis.web_app_demo.db.Query;
import com.popokis.web_app_demo.entity.ImmutableUser;
import com.popokis.web_app_demo.entity.User;
import org.simpleflatmapper.jdbc.JdbcMapperBuilder;
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
    try {
      JdbcMapperBuilder<ImmutableUser.Builder> builder = JdbcMapperFactory.newInstance()
          .newBuilder(ImmutableUser.Builder.class);
      rowSet.next();
      return builder
          .addKey("u_id")
          .addKey("u_username")
          .addKey("u_password")
          .addKey("u_created_at")
          .addKey("u_updated_at")
          .mapper()
          .map(rowSet)
          .build();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
