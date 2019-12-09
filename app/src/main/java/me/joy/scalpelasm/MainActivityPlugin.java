package me.joy.scalpelasm;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityPlugin extends AppCompatActivity  {

  public static String TAG = "MainActivityPlugin";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
//    findViewById(R.id.btn_test1).setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        testMethod1();
//      }
//    });
//    findViewById(R.id.btn_test2).setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        testMethod2();
//
//      }
//    });
//    findViewById(R.id.btn_test3).setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        testMethod3(1,111L,"str",true);
//      }
//    });
  }


//
//  @LogTrace(traceSpendTime = false, level = 5)
//  private void testMethod1() {
//    try {
//      Thread.sleep(1000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//  }
//  @LogTrace(traceSpendTime = true, level = 2)
//  private int testMethod2() {
//    try {
//      Thread.sleep(1000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    return 0;
//  }
//
//
//
//
//  @LogTrace(level = 2)
//  private int testMethod3(int iiiiiii, Long llllll, String ssssss, boolean bl) {
//    return 0;
//  }
//
//  @LogTrace(level = 2)
//  private static int testMethod3_static(int iiiiiii, Long llllll, String ssssss, boolean bl) {
//    return 0;
//  }
//
//
//
//  @Override
//  protected void onStart() {
//    super.onStart();
//  }
//
//  @Override
//  protected void onResume() {
//    super.onResume();
//  }
//
//
//  @Override
//  protected void onStop() {
//    super.onStop();
//  }
//
//
//  @Override
//  protected void onDestroy() {
//    super.onDestroy();
//  }

}

