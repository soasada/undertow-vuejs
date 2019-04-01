package com.popokis.web_app_demo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public final class Database {

  private Database() {}

  public static long executeInsert(Query query) {
    try (Connection connection = HikariConnectionPool.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query.query(), Statement.RETURN_GENERATED_KEYS)) {
      query.parameters(preparedStatement);

      if (preparedStatement.executeUpdate() == 0) throw new RuntimeException("No rows affected");

      try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
        return generateId(resultSet);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static int executeDML(Query query) {
    try (Connection connection = HikariConnectionPool.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query.query())) {
      query.parameters(preparedStatement);
      return preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> Optional<T> executeQuery(Query query, JdbcMapper<T> mapper) {
    try (Connection connection = HikariConnectionPool.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query.query())) {
      query.parameters(preparedStatement);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.isBeforeFirst()) {
          // here we move the cursor one step
          resultSet.next();
          if (resultSet.getLong(1) == 0) return Optional.empty();
          return mapper.map(resultSet);
        } else {
          return Optional.empty();
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private static long generateId(ResultSet generatedKeys) throws SQLException {
    long generatedId;

    if (generatedKeys.next()) {
      generatedId = generatedKeys.getLong(1);
    } else {
      throw new RuntimeException("DML failed, no ID obtained.");
    }

    return generatedId;
  }
}
