package me.joy.scalpelasm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import me.joy.scalpel.annotation.LogTrace;

public class MainActivityPlugin extends AppCompatActivity {

  public static String TAG = "MainActivityPlugin";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.btn_start).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        logMethod1();
        logMethod2();
        logMethod3(1,111L,"str",true);
        new Test().test3(1,2l,"ssss",true);
      }
    });

    findViewById(R.id.btn_test).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.i(TAG, "点击btn_test");
      }
    });
  }


  @LogTrace(traceSpendTime = false, level = 5)
  private void logMethod1() {
    Log.i(TAG, "执行 logMethod1");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  @LogTrace(traceSpendTime = true, level = 2)
  private int logMethod2() {

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Log.i(TAG, "执行 logMethod2");

    return 0;
  }

  @LogTrace(traceSpendTime = true, level = 2)
  private int logMethod3(int iiiiiii, Long llllll, String ssssss, boolean bl) {
    Log.i(TAG, "执行 logMethod3");
    return 0;
  }



  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }


  @Override
  protected void onStop() {
    super.onStop();
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

}

