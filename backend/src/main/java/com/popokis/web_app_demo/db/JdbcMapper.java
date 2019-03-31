package com.popokis.web_app_demo.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface JdbcMapper<T> {
  T map(ResultSet resultSet) throws SQLException;;
}
