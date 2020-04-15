package me.joy.scalpelplugin.extention;

import java.io.Serializable;

/**
 * Created by Joy on 2020-04-11
 */
public class GarbageConfigExtension implements Serializable {

  private double garbageCodeInjectRatio = 1;
  private boolean enableGarbageCode = true;
  private boolean handleInnerClass = true;
  private String garbageType = "B"; // "A","B","C","D","E","F","G","H","I","J",

  public double garbageCodeInjectRatio() {
    return garbageCodeInjectRatio;
  }

  public void setGarbageCodeInjectRatio(double garbageCodeInjectRatio) {
    this.garbageCodeInjectRatio = garbageCodeInjectRatio;
  }

  public String garbageType() {
    return garbageType;
  }

  public void setGarbageType(String garbageType) {
    this.garbageType = garbageType;
  }

  public void enableGarbageCode(boolean enableGarbageCode) {
    this.enableGarbageCode = enableGarbageCode;
  }


  public boolean isEnableGarbageCode() {
    return enableGarbageCode && ConfigHelper.getInstance().isEnable();
  }

  public void handleInnerClass(boolean handleInnerClass) {
    this.handleInnerClass = handleInnerClass;
  }


  public boolean isHandleInnerClass() {
    return handleInnerClass;
  }


}
