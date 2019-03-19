package com.popokis.web_app_demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigLoader {

  private final Properties appProps;

  private ConfigLoader() {
    try (InputStream fi = ConfigLoader.class.getResourceAsStream(File.separator + "app.properties")) {
      this.appProps = new Properties();
      appProps.load(fi);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static ConfigLoader create() {
    return new ConfigLoader();
  }

  public String get(String key) {
    return appProps.getProperty(key);
  }

  public int getInt(String key) {
    return Integer.parseInt(appProps.getProperty(key));
  }
}
