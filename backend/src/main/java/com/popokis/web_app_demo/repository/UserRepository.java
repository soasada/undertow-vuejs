package com.popokis.web_app_demo.repository;

import com.popokis.web_app_demo.Application;
import com.popokis.web_app_demo.db.Database;
import com.popokis.web_app_demo.db.JdbcMapper;
import com.popokis.web_app_demo.db.ListMapper;
import com.popokis.web_app_demo.db.Query;
import com.popokis.web_app_demo.entity.User;
import com.popokis.web_app_demo.mapper.db.FindUserHousesMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class UserRepository {

  private static final JdbcMapper<User> mapper = Application.getMapper("UserMapper", User.class);

  private UserRepository() {}

  public static List<User> all() {
    Query query = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM user ORDER BY u_id ASC LIMIT 100";
      }

      @Override
      public void parameters(PreparedStatement stm) {}
    };

    return Database.executeQuery(query, ListMapper.of(mapper)).get();
  }

  public static long create(User user) {
    Query query = new Query() {
      @Override
      public String query() {
        return "INSERT INTO user (u_username, u_password) VALUES (?, ?)";
      }

      @Override
      public void parameters(PreparedStatement stm) {
        try {
          stm.setString(1, user.getUsername());
          stm.setString(2, user.hashPassword());
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    };

    return Database.executeInsert(query);
  }

  public static User read(long id) {
    Query query = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM user WHERE u_id = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) {
        try {
          stm.setLong(1, id);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    };

    return Database.executeQuery(query, mapper).get();
  }

  public static int update(User user) {
    Query query = new Query() {
      @Override
      public String query() {
        return "UPDATE user SET u_username = ?, u_password = ? WHERE u_id = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) {
        try {
          stm.setString(1, user.getUsername());
          stm.setString(2, user.getPassword());
          stm.setLong(3, user.getId());
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    };

    return Database.executeDML(query);
  }

  public static int delete(long id) {
    Query query = new Query() {
      @Override
      public String query() {
        return "DELETE FROM user WHERE u_id = ? LIMIT 1";
      }

      @Override
      public void parameters(PreparedStatement stm) {
        try {
          stm.setLong(1, id);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    };

    return Database.executeDML(query);
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
      public void parameters(PreparedStatement stm) {
        try {
          stm.setLong(1, id);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    };

    return Database.executeQuery(query, new FindUserHousesMapper()).get();
  }

  public static User login(User userWithoutHash) {
    Query query = new Query() {
      @Override
      public String query() {
        return "SELECT * FROM user WHERE u_username = ? AND u_password = ?";
      }

      @Override
      public void parameters(PreparedStatement stm) {
        try {
          stm.setString(1, userWithoutHash.getUsername());
          stm.setString(2, userWithoutHash.hashPassword());
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    };

    return Database.executeQuery(query, mapper).get();
  }
}
