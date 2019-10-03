package com.popokis.undertow_vuejs.user;

import com.popokis.popok.sql_db.Database;
import com.popokis.popok.sql_db.JdbcMapper;
import com.popokis.popok.sql_db.ListMapper;
import com.popokis.popok.sql_db.Query;
import com.popokis.undertow_vuejs.db.HikariConnectionPool;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class UserRepository {

  private static final JdbcMapper<User> mapper = User.mapper();
  private static final Database db = Database.create(HikariConnectionPool.getInstance().getDataSource());

  private UserRepository() {}

  public static List<User> all() {
    Query query = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM user ORDER BY u_id ASC LIMIT 1000";
      }

      @Override
      public void parameters(PreparedStatement stm) {}
    };

    return db.executeQuery(query, ListMapper.of(mapper)).orElseGet(List::of);
  }

  public static long create(User user) {
    Query query = new Query() {
      @Override
      public String query() {
        return "INSERT INTO user (u_username, u_password) VALUES (?, ?)";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setString(1, user.getUsername());
        stm.setString(2, user.hashPassword());
      }
    };

    return db.executeInsert(query);
  }

  public static User read(long id) {
    Query query = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM user WHERE u_id = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setLong(1, id);
      }
    };

    return db.executeQuery(query, mapper).get();
  }

  public static int update(User user) {
    Query query = new Query() {
      @Override
      public String query() {
        return "UPDATE user SET u_username = ?, u_password = ? WHERE u_id = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setString(1, user.getUsername());
        stm.setString(2, user.hashPassword());
        stm.setLong(3, user.getId());
      }
    };

    return db.executeDML(query);
  }

  public static int delete(long id) {
    Query query = new Query() {
      @Override
      public String query() {
        return "DELETE FROM user WHERE u_id = ? LIMIT 1";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setLong(1, id);
      }
    };

    return db.executeDML(query);
  }

  public static User findUserHouses(long id) {
    Query query = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM user " +
            "LEFT JOIN house ON u_id = h_user_id " +
            "LEFT JOIN furniture ON h_id = f_house_id " +
            "WHERE u_id = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setLong(1, id);
      }
    };

    return db.executeQuery(query, new FindUserHousesMapper()).get();
  }

  public static User login(User userWithoutHash) {
    Query query = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM user WHERE u_username = ? AND u_password = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) throws SQLException {
        stm.setString(1, userWithoutHash.getUsername());
        stm.setString(2, userWithoutHash.hashPassword());
      }
    };

    return db.executeQuery(query, mapper).orElseThrow(() -> new RuntimeException("Invalid username or password"));
  }
}
