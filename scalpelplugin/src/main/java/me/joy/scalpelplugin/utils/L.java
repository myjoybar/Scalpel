package me.joy.scalpelplugin.utils;

/**
 * Created by Joy on 2019-08-14
 */
public class L {

  private static final String TAG = "ScalpelPlugin";

  public static void print(String msg) {
    msg = TAG + ": " + msg;
    System.out.println(msg);
  }

  public static void print(String tag, String msg) {
    msg = TAG + "_ " + tag + ": " + msg;
    System.out.println(msg);
  }
}
