package me.joy.scalpelplugin.extention;

/**
 * Created by Joy on 2019-08-15
 */
public class ScalpelExtension {

  private boolean enable = true;
  private boolean enableLog = true;
  private boolean enableMethodTrack = true;

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

  public void enableMethodTrack(boolean enableMethodTrack) {
    this.enableMethodTrack = enableMethodTrack;
  }

  public boolean isEnableMethodTrack() {
    return enableMethodTrack;
  }



}
