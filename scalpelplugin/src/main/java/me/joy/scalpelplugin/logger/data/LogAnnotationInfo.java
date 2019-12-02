package me.joy.scalpelplugin.logger.data;

/**
 * Created by Joy on 2019-08-20
 */
public class LogAnnotationInfo {

  private String methodName;
  private String annotationName;
  private boolean isAnnotationPresent;
  private int level = 5;
  private boolean traceSpendTime = true;



  public boolean isAnnotationPresent() {
    return isAnnotationPresent;
  }

  public void setAnnotationPresent(boolean annotationPresent) {
    isAnnotationPresent = annotationPresent;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public boolean isTraceSpendTime() {
    return traceSpendTime;
  }

  public void setTraceSpendTime(boolean traceSpendTime) {
    this.traceSpendTime = traceSpendTime;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public String getAnnotationName() {
    return annotationName;
  }

  public void setAnnotationName(String annotationName) {
    this.annotationName = annotationName;
  }

  @Override
  public String toString() {
    return "LogAnnotationInfo{" +
        "methodName='" + methodName + '\'' +
        ", annotationName='" + annotationName + '\'' +
        ", isAnnotationPresent=" + isAnnotationPresent +
        ", level=" + level +
        ", traceSpendTime=" + traceSpendTime +
        '}';
  }
}
