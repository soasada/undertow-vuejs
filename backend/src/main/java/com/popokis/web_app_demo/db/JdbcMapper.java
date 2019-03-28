package com.popokis.web_app_demo.db;

import java.sql.ResultSet;

public interface JdbcMapper<T> {
  T map(ResultSet resultSet);
}
