package com.popokis.web_app_demo.mapper.db;

import com.popokis.web_app_demo.db.JdbcMapper;
import com.popokis.web_app_demo.entity.ImmutableUser;
import com.popokis.web_app_demo.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class UserMapper implements JdbcMapper<User> {

  @Override
  public Optional<User> map(ResultSet resultSet) throws SQLException {
    if (resultSet.getLong("u_id") == 0) return Optional.empty();

    return Optional.of(
        ImmutableUser.of(
            resultSet.getLong("u_id"),
            resultSet.getString("u_username"),
            resultSet.getString("u_password"),
            resultSet.getTimestamp("u_created_at").toLocalDateTime(),
            resultSet.getTimestamp("u_updated_at").toLocalDateTime(),
            List.of()
        )
    );
  }
}
