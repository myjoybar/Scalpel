package me.joy.scalpelasm;

import android.util.Log;
import android.view.View;
import me.joy.scalpel.helper.ScalpelDelegateImpl;
import me.joy.scalpel.helper.viewclick.MarkViewUtils;

/**
 * Created by Joy on 2019-12-05
 */
public class ScalpelExecutorDelegate  extends ScalpelDelegateImpl {

  @Override
  public void enterViewClick(View view) {
    if(MarkViewUtils.isTrackView(view)){
      Log.d(TAG, "View 在配置中");
    }else{
      Log.d(TAG, "View 不在配置中");
      super.enterViewClick(view);
    }

  }
}
