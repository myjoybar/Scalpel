package me.joy.scalpelplugin.extention;

/**
 * Created by Joy on 2019-08-15
 */
public class ScalpelExtension {

  private boolean enable = true; // the master switch
  private boolean enableLog = true;
  private boolean enableMethodTrace = true;
  private boolean enableViewClickTrace = true;

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

}
