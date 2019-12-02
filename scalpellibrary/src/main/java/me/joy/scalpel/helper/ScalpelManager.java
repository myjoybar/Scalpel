package me.joy.scalpel.helper;

/**
 * Created by Joy on 2019-11-25
 */
public class ScalpelManager {

  static ScalpelExecutorDelegate defaultScalpelExecutorDelegate = new ScalpelExecutorDelegateImpl();

  public static ScalpelExecutorDelegate getScalpelExecutorDelegate() {
    return defaultScalpelExecutorDelegate;
  }

  public static  void setScalpelExecutorDelegate(
      ScalpelExecutorDelegate scalpelExecutorDelegate) {
   defaultScalpelExecutorDelegate = scalpelExecutorDelegate;
  }







}
