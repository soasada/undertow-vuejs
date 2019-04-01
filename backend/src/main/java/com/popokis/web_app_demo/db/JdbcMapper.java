package com.popokis.web_app_demo.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface JdbcMapper<T> {
  Optional<T> map(ResultSet resultSet) throws SQLException;
}
