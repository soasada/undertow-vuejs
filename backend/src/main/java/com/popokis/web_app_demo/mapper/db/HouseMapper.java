package com.popokis.web_app_demo.mapper.db;

import com.popokis.web_app_demo.db.JdbcMapper;
import com.popokis.web_app_demo.entity.House;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.util.List;
import java.util.Optional;

public final class HouseMapper implements JdbcMapper<House> {

  @Override
  public Optional<House> map(ResultSetWrappingSqlRowSet resultSet) {
    if (resultSet.getLong("h_id") == 0) return Optional.empty();

    return Optional.of(
        House.create(
            resultSet.getLong("h_id"),
            resultSet.getString("h_name"),
            resultSet.getLong("h_user_id"),
            resultSet.getTimestamp("h_created_at").toLocalDateTime(),
            resultSet.getTimestamp("h_updated_at").toLocalDateTime(),
            List.of()
        )
    );
  }
}
