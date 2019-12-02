package me.joy.scalpelplugin.extention;


/**
 * 用于保存gradle全局配置 Created by Joy on 2019-08-21
 */
public class ConfigHelper {



  private ScalpelExtension scalpelExtension;


  public static ConfigHelper getInstance() {
    return ConfigHelperHolder.INSTANCE;
  }

  static class ConfigHelperHolder {

    private static ConfigHelper INSTANCE = new ConfigHelper();
  }

  public ScalpelExtension getScalpelExtension() {
    return scalpelExtension;
  }

  public void setScalpelExtension(ScalpelExtension scalpelExtension) {
    this.scalpelExtension = scalpelExtension;
  }

  public boolean isEnable() {
    return getScalpelExtension().isEnable();
  }

  public boolean isEnableLog() {
    return getScalpelExtension().isEnableLog() && isEnable();
  }

  public boolean isEnableMethodTrack() {
    return getScalpelExtension().isEnableMethodTrack() && isEnable();
  }
}
