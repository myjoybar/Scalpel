package me.joy.scalpel.helper;

import android.util.Log;

/**
 * Created by Joy on 2019-08-21
 */
public class MethodInfoTimeHelper {

  public static void addMethodInfo(Object... args) {

    Object[] arguments = args;
    if (null != args) {
      int length = args.length;
      for (int i = 0; i <length ; i++) {
        Object arg = args[i];
        Log.d("MethodInfoTimeHelper", "getSimpleName  = "+ arg.getClass().getSimpleName());
        Log.d("MethodInfoTimeHelper", "value  = "+ arg.toString());
      }
    }

  }


}
