package me.joy.scalpelasm.test;

import android.util.Log;
import me.joy.scalpel.helper.MethodInfoTimeHelper;

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


  public void test3(int iiiiiii, Long llllll, String ssssss, boolean blblblblbl) {

    MethodInfoTimeHelper.addMethodInfo(iiiiiii, llllll, ssssss, blblblblbl);

  }

  public void test4(int iiiiiii, Long llllll, String ssssss, boolean blblblblbl) {

    MethodInfoTimeHelper.addMethodInfo(iiiiiii, llllll, ssssss, blblblblbl);

  }
//
//  public void test5(View view) {
//
//    if(ScalpelManager.getScalpelDelegateService().isFastClick(view)){
//      ScalpelManager.getScalpelDelegateService().enterViewClick(view);
//    }
//
//
//  }
  public void test6() {

    long startTime = System.nanoTime();
    long startTime2 = System.currentTimeMillis();
    String str = "Test String";
    long duration = System.nanoTime() - startTime;
    Log.d("AAAAA","Cost time: "+duration);

  }

  public void test7() {

    //ScalpelManager.getScalpelDelegateService().log("AAAA","BBBBB",5);
    Log.d("AAAA","BBBBB");

  }





}
