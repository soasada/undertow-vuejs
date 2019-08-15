package com.popokis.undertow_vuejs.furniture;

import com.popokis.popok.sql_db.JdbcMapper;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.util.Optional;

public final class FurnitureMapper implements JdbcMapper<Furniture> {

  @Override
  public Optional<Furniture> map(ResultSetWrappingSqlRowSet resultSet) {
    if (resultSet.getLong("f_id") == 0) return Optional.empty();

    return Optional.of(
        Furniture.create(
            resultSet.getLong("f_id"),
            resultSet.getString("f_name"),
            resultSet.getString("f_type"),
            resultSet.getLong("f_house_id"),
            resultSet.getTimestamp("f_created_at").toLocalDateTime(),
            resultSet.getTimestamp("f_updated_at").toLocalDateTime()
        )
    );
  }
}
