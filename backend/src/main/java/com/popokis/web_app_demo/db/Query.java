package com.popokis.web_app_demo.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Query {
  String query();
  void parameters(PreparedStatement stm) throws SQLException;
}
