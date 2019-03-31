package com.popokis.web_app_demo.mapper.db;

import com.popokis.web_app_demo.db.JdbcMapper;
import com.popokis.web_app_demo.entity.House;
import com.popokis.web_app_demo.entity.ImmutableHouse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class HouseMapper implements JdbcMapper<House> {

  @Override
  public House map(ResultSet resultSet) throws SQLException {
    if (resultSet.getLong("h_id") == 0) return null;

    return ImmutableHouse.of(
        resultSet.getLong("h_id"),
        resultSet.getString("h_name"),
        resultSet.getLong("h_user_id"),
        resultSet.getTimestamp("h_created_at").toLocalDateTime(),
        resultSet.getTimestamp("h_updated_at").toLocalDateTime(),
        List.of()
    );
  }
}
