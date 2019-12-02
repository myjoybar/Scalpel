package me.joy.scalpel.helper;

import me.joy.scalpel.data.MethodInfo;

/**
 * Created by Joy on 2019-11-25
 */
public interface ScalpelExecutorDelegate {

  void printMethod(MethodInfo methodInfo);

  void log(String tag, String msg, int logLevel);
}
