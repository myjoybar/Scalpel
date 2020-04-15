package me.joy.scalpelplugin.classmodifier;

/**
 * Created by Joy on 2020-04-03
 */
public class ClassModification {

  private String classPrefix;
  private String classSuffix;

  private String methodPrefix;
  private String methodSuffix;

  public String getClassPrefix() {
    return classPrefix;
  }

  public void setClassPrefix(String classPrefix) {
    this.classPrefix = classPrefix;
  }

  public String getClassSuffix() {
    return classSuffix;
  }

  public void setClassSuffix(String classSuffix) {
    this.classSuffix = classSuffix;
  }

  public String getMethodPrefix() {
    return methodPrefix;
  }

  public void setMethodPrefix(String methodPrefix) {
    this.methodPrefix = methodPrefix;
  }

  public String getMethodSuffix() {
    return methodSuffix;
  }

  public void setMethodSuffix(String methodSuffix) {
    this.methodSuffix = methodSuffix;
  }
}
