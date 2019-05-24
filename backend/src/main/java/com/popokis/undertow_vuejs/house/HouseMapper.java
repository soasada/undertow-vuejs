package com.popokis.undertow_vuejs.house;

import com.popokis.undertow_vuejs.db.JdbcMapper;
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
