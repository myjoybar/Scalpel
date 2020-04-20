package me.joy.scalpelplugin.extention;


import java.util.List;

/**
 * 用于保存gradle全局配置 Created by Joy on 2019-08-21
 */
public class ConfigHelper {


  private ScalpelExtension scalpelExtension;
  private GarbageConfigExtension garbageConfigExtension;


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

  public GarbageConfigExtension getGarbageConfigExtension() {
    return garbageConfigExtension;
  }

  public void setGarbageConfigExtension(
      GarbageConfigExtension garbageConfigExtension) {
    this.garbageConfigExtension = garbageConfigExtension;
  }

  public boolean isEnable() {
    return getScalpelExtension().isEnable();
  }


  public boolean isEnableLog() {
    return getScalpelExtension().isEnableLog() && isEnable();
  }

  public boolean isEnableMethodTrace() {
    return getScalpelExtension().isEnableMethodTrace() && isEnable();
  }

  public boolean isEnableViewClickTrace() {
    return getScalpelExtension().isEnableViewClickTrace() && isEnable();
  }

  public boolean isEnableModification() {
    return getScalpelExtension().isEnableModification() && isEnable();
  }

  public boolean isEnableMethodCostTime() {
    return getScalpelExtension().isEnableMethodCostTime() && isEnable();
  }

  public List<String> vestModules() {
    return  getScalpelExtension().vestModules();
  }


}
