package me.joy.scalpel.helper;

import android.util.Log;
import android.view.View;
import me.joy.scalpel.helper.logger.data.LogLevel;
import me.joy.scalpel.helper.logger.data.MethodInfo;
import me.joy.scalpel.helper.viewclick.DebounceUtils;
/**
 * Created by Joy on 2019-11-25
 */
public class ScalpelDelegateImpl implements ScalpelDelegateService {

//  public static long FROZEN_CLICK_MILLIS = 300L;

  public static final String TAG = "ScalpelDelegateImpl";

  @Override
  public void printMethod(MethodInfo methodInfo) {
    log(TAG, methodInfo.toString(), LogLevel.TYPE_VERBOSE);

  }

  @Override
  public void log(String tag, String msg, int logLevel) {

    if (logLevel == LogLevel.TYPE_VERBOSE) {
      Log.v(tag, msg);
    } else if (logLevel == LogLevel.TYPE_DEBUG) {
      Log.d(tag, msg);
    } else if (logLevel == LogLevel.TYPE_INFO) {
      Log.i(tag, msg);
    } else if (logLevel == LogLevel.TYPE_WARN) {
      Log.w(tag, msg);
    } else if (logLevel == LogLevel.TYPE_ERROR) {
      Log.e(tag, msg);
    }

  }

  @Override
  public void enterViewClick(View view) {
    Log.d(TAG, "enterViewClick");
  }

  @Override
  public boolean isFastClick(View view) {
    return DebounceUtils.isFastClick(view, getClickFrozenTime());
  }

  @Override
  public long getClickFrozenTime() {
    return 1000;
  }





}
