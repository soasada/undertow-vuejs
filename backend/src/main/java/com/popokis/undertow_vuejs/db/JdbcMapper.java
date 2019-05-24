package com.popokis.undertow_vuejs.db;

import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.util.Optional;

public interface JdbcMapper<T> {
  Optional<T> map(ResultSetWrappingSqlRowSet resultSet);
}
