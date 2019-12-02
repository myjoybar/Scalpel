package me.joy.scalpelplugin.utils;


/**
 * Created by Joy on 2019-08-14
 */
public class ClassCheckUtils {

  public static boolean isActivity(String name) {

    if (name.endsWith("/AppCompatActivity.class")
        || name.endsWith("/FragmentActivity.class")
        || name.endsWith("/SupportActivity.class")) {
      return true;
    }

    return false;
  }


  public static boolean isFragment(String name) {
    if (name.endsWith("/Fragment.class")) {
      return true;
    }
    return false;
  }

  public static boolean isView(String name) {
//    if (name.endsWith("/View.class")) {
//      return true;
//    }
    return false;
  }


}
