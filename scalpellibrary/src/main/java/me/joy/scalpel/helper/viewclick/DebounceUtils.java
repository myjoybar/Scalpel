package me.joy.scalpel.helper.viewclick;

import android.view.View;

/**
 * Created by Joy on 2019-12-06
 */
public class DebounceUtils {

  public static long FROZEN_CLICK__MILLIS = 300L;

  public static boolean isFastClick(View target) {
    return isFastClick(target, FROZEN_CLICK__MILLIS);
  }

  public static boolean isFastClick(View target, long frozenTime) {
    long curTimeStamp = System.currentTimeMillis();
    long lastClickTimeStamp = 0;
    Object o = target.getTag(1001);
    if (o == null) {
      target.setTag(1001, curTimeStamp);
      return false;
    }
    lastClickTimeStamp = (Long) o;
    boolean isInvalid = curTimeStamp - lastClickTimeStamp < frozenTime;
    if (!isInvalid) {
      target.setTag(1001, curTimeStamp);
    }
    return isInvalid;
  }
}
