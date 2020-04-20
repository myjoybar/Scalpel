package me.joy.scalpelasm.test;

import me.joy.scalpel.helper.logger.MethodInfoSaver;

/**
 * Created by Joy on 2019-11-22
 */
public class Test {
//
//  public void test1() {
//    MethodCostTimeHelper.addStartTime(false, "aaaaaaa");
//
//    MethodCostTimeHelper.addEndTime(true, "bbbbbbbb");
//
//    Log.v("TAGTAG", MethodCostTimeHelper.combineLogStrInfo("CCCCCCC", true, "DDDDDDD"));
//
//  }

//  public void test2() {
//    MethodCostTimeHelper.addStartTime(false, "aaaaaaa");
//
//    MethodCostTimeHelper.addEndTime(true, "bbbbbbbb");
//
//    ScalpelManager.getScalpelDelegateService()
//        .log("TAGTAG", MethodCostTimeHelper.combineLogStrInfo("CCCCCCC", true, "DDDDDDD"), 2222);
//  }

  //
//  public void test3(int iiiiiii, Long llllll, String ssssss, boolean blblblblbl) {
//
//    MethodInfoTimeHelper.addMethodInfo(iiiiiii, llllll, ssssss, blblblblbl);
//
//  }
//
  public void test4(int iiiiiii, Long llllll, String ssssss, boolean blblblblbl,Object object) {

    //MethodInfoSaver.addMethodInfo(iiiiiii, llllll, ssssss, blblblblbl);
    MethodInfoSaver.addMethodArg(iiiiiii);
    MethodInfoSaver.addMethodArg(llllll);
    MethodInfoSaver.addMethodArg(ssssss);
    MethodInfoSaver.addMethodArg(blblblblbl);
    MethodInfoSaver.addMethodArg(blblblblbl);
  }


//  public void test5(View view) {
//
//    if (ScalpelManager.getScalpelDelegateService().isFastClick(view)) {
//      ScalpelManager.getScalpelDelegateService().enterViewClick(view);
//    }
//
//  }


}
