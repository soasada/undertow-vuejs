package com.popokis.web_app_demo;

import java.util.List;

public interface Router {
  List<Route> routes();

  String version();
}
