package me.joy.scalpelasm;

/**
 * Created by Joy on 2019-12-02
 */
public class TestSingleInstance {

  TestSingleInstance getInstance() {
    return TestSingleInstanceHolder.instance;
  }

  public static class TestSingleInstanceHolder {

    private static TestSingleInstance instance = new TestSingleInstance();
  }


}
