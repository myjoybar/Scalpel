package me.joy.scalpelasm;

import android.util.Log;
import me.joy.scalpel.helper.MethodCostTimeHelper;
import me.joy.scalpel.helper.MethodInfoTimeHelper;

/**
 * Created by Joy on 2019-11-22
 */
public class Test {

  public void test1() {
    MethodCostTimeHelper.addStartTime(false, "aaaaaaa");

    MethodCostTimeHelper.addEndTime(true, "bbbbbbbb");

    Log.v("TAGTAG", MethodCostTimeHelper.combineLogStrInfo("CCCCCCC", true, "DDDDDDD"));

  }


//  public void test2() {
//    MethodCostTimeHelper.addStartTime(false, "aaaaaaa");
//
//    MethodCostTimeHelper.addEndTime(true, "bbbbbbbb");
//
//    ScalpelManager.getScalpelExecutorDelegate()
//        .log("TAGTAG", MethodCostTimeHelper.combineLogStrInfo("CCCCCCC", true, "DDDDDDD"), 2222);
//  }


  public void test3(int iiiiiii, Long llllll, String ssssss, boolean blblblblbl) {

    MethodInfoTimeHelper.addMethodInfo(iiiiiii, llllll, ssssss, blblblblbl);

  }


}
