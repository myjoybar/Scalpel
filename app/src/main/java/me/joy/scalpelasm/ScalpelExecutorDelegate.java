package me.joy.scalpelasm;

import android.util.Log;
import android.view.View;
import me.joy.scalpel.helper.ScalpelDelegateImpl;
import me.joy.scalpel.helper.viewclick.MarkViewUtils;

/**
 * Created by Joy on 2019-12-05
 */
public class ScalpelExecutorDelegate extends ScalpelDelegateImpl {

  @Override
  public void enterViewClick(View view) {
    if(MarkViewUtils.isTrackView(view)){
      Log.d(TAG, MarkViewUtils.getIdName(view)+ " 在配置中, 添加埋点数据");
      Log.d(TAG, "模拟发送 埋点数据: Click " + MarkViewUtils.isTrackView(view));
    }else{
      Log.d(TAG, MarkViewUtils.getIdName(view)+  " 不在配置中， 不添加埋点数据");
      super.enterViewClick(view);
    }
  }

}
