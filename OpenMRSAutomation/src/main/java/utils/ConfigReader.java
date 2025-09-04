// src/main/java/utils/ConfigReader.java
package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
  private static final Properties props = new Properties();
  static {
    try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
      if (in != null) props.load(in);
    } catch (Exception e) { throw new RuntimeException("Failed to load config.properties", e); }
  }
  public static String get(String key) {
    String v = System.getProperty(key);
    return (v != null && !v.isBlank()) ? v : props.getProperty(key);
  }
}
