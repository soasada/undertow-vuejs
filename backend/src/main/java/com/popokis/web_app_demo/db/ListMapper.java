package com.popokis.web_app_demo.db;

import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ListMapper<T> implements JdbcMapper<List<T>> {

  private final JdbcMapper<T> modelMapper;

  private ListMapper(JdbcMapper<T> modelMapper) {
    this.modelMapper = modelMapper;
  }

  public static <T> ListMapper<T> of(JdbcMapper<T> modelMapper) {
    return new ListMapper<>(modelMapper);
  }

  @Override
  public Optional<List<T>> map(ResultSetWrappingSqlRowSet rowSet) {
    List<T> resultList = new ArrayList<>();

    do {
      Optional<T> optional = modelMapper.map(rowSet);
      optional.ifPresent(resultList::add);
    } while (rowSet.next());

    return Optional.of(resultList);
  }
}
