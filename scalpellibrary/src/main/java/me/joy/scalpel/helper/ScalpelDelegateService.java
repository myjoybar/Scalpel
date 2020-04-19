package me.joy.scalpel.helper;

import android.view.View;
import me.joy.scalpel.helper.logger.data.MethodInfo;

/**
 * Created by Joy on 2019-11-25
 */
public interface ScalpelDelegateService {

  void printMethod(MethodInfo methodInfo);

  void log(String tag, String msg, int logLevel);

  void enterViewClick(View view);

  boolean isFastClick(View view);

  long getClickFrozenTime();


}
