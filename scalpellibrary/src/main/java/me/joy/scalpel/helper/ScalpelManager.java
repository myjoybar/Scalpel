package me.joy.scalpel.helper;

/**
 * Created by Joy on 2019-11-25
 */
public class ScalpelManager {

  static ScalpelDelegateService defaultScalpelDelegateService = new ScalpelDelegateImpl();

  //getScalpelDelegateService
  public static ScalpelDelegateService getScalpelDelegateService() {
    return defaultScalpelDelegateService;
  }

  public static  void setScalpelDelegateService(
      ScalpelDelegateService scalpelDelegateService) {
    defaultScalpelDelegateService = scalpelDelegateService;
  }



}
