package me.joy.scalpelplugin.extention;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joy on 2019-08-15
 */
public class ScalpelExtension {

  private boolean enable = false; // the master switch
  private boolean enableLog = false;
  private boolean enableMethodTrace = false;
  private boolean enableViewClickTrace = false;
  private boolean enableModification = false;
  private boolean enableMethodCostTime = false;
  private List<String> vestModules = new ArrayList<>();


  public void enable(boolean enable) {
    this.enable = enable;
  }

  public boolean isEnable() {
    return enable;
  }

  public void enableLog(boolean enableLog) {
    this.enableLog = enableLog;
  }


  public boolean isEnableLog() {
    return enableLog;
  }

  public void enableMethodTrace(boolean enableMethodTrace) {
    this.enableMethodTrace = enableMethodTrace;
  }

  public boolean isEnableMethodTrace() {
    return enableMethodTrace;
  }

  public void enableViewClickTrace(boolean enableViewClickTrace) {
    this.enableViewClickTrace = enableViewClickTrace;
  }

  public boolean isEnableViewClickTrace() {
    return enableViewClickTrace;
  }

  public void enableModification(boolean enableModification) {
    this.enableModification = enableModification;
  }


  public boolean isEnableModification() {
    return enableModification;
  }


  public void enableMethodCostTime(boolean enableMethodCostTime) {
    this.enableMethodCostTime = enableMethodCostTime;
  }


  public boolean isEnableMethodCostTime() {
    return enableMethodCostTime;
  }


  public void vestModules(List<String> vestModules) {
    this.vestModules = vestModules;
  }


  public List<String> vestModules() {
    return this.vestModules;
  }

//  public GarbageConfigExtension garbageConfig() {
//    return garbageConfig;
//  }
//
//  public void garbageConfig(GarbageConfigExtension garbageConfig) {
//    this.garbageConfig = garbageConfig;
//  }
}
