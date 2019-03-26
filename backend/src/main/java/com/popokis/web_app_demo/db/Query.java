package com.popokis.web_app_demo.db;

import java.sql.PreparedStatement;

public interface Query {
  String query();
  void parameters(PreparedStatement stm);
}
