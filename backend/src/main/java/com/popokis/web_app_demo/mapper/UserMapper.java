package com.popokis.web_app_demo.mapper;

import com.popokis.web_app_demo.db.JdbcMapper;
import com.popokis.web_app_demo.entity.ImmutableUser;
import com.popokis.web_app_demo.entity.User;
import org.simpleflatmapper.jdbc.JdbcMapperBuilder;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;

import java.sql.ResultSet;

public final class UserMapper implements JdbcMapper<User> {
  @Override
  public User map(ResultSet resultSet) {
    try {
      JdbcMapperBuilder<ImmutableUser.Builder> builder = JdbcMapperFactory.newInstance()
          .newBuilder(ImmutableUser.Builder.class);
      resultSet.next();
      return builder
          .addKey("u_id")
          .addKey("u_username")
          .addKey("u_password")
          .addKey("u_created_at")
          .addKey("u_updated_at")
          .mapper()
          .map(resultSet)
          .build();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
