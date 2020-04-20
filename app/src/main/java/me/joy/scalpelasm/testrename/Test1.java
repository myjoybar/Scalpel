package me.joy.scalpelasm.testrename;

import me.joy.scalpelasm.testrename.tes2.Test2;

/**
 * Created by Joy on 2020/4/19
 */
public class Test1 {

  public void test(){

    new Test2().test();

  }
  public static void testStatic(){
    Test2.testStatic();
  }

}
