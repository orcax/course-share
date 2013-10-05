package org.tjsse.courseshare.util;

public final class Config {

  public static String ROOT_PATH = null;
  static {
    String os = System.getProperty("os.name").toLowerCase();
    if(os.startsWith("windows")) {
      ROOT_PATH = "D:/course-share/";
    }
    else {
      ROOT_PATH = "/var/tmp/course-share/";
    }
  }

}
