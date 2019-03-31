package com.popokis.web_app_demo.mapper.db;

import com.popokis.web_app_demo.db.JdbcMapper;
import com.popokis.web_app_demo.entity.Furniture;
import com.popokis.web_app_demo.entity.ImmutableFurniture;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class FurnitureMapper implements JdbcMapper<Furniture> {

  @Override
  public Furniture map(ResultSet resultSet) throws SQLException {
    if (resultSet.getLong("f_id") == 0) return null;

    return ImmutableFurniture.of(
        resultSet.getLong("f_id"),
        resultSet.getString("f_name"),
        resultSet.getString("f_type"),
        resultSet.getLong("f_house_id"),
        resultSet.getTimestamp("f_created_at").toLocalDateTime(),
        resultSet.getTimestamp("f_updated_at").toLocalDateTime()
    );
  }
}
