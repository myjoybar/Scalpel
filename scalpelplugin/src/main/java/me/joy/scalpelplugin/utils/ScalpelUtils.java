package me.joy.scalpelplugin.utils;

/**
 * Created by Joy on 2019-08-21
 */
public class ScalpelUtils {



  public static String getLogLevel(int level) {

    switch (level) {
      case 1:
        return "e";

      case 2:
        return "w";

      case 3:
        return "i";

      case 4:
        return "d";

      case 5:
        return "v";

      default:
        return "d";
    }
  }
}
