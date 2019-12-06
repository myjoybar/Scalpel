package me.joy.scalpel.helper.viewclick;

import android.util.Log;
import androidx.annotation.Keep;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joybar on 2018/12/24.
 */

@Keep
public class TrackConfigManager {

  /**
   * [{ "className": "com.joy.aop.MainActivity", "resourceEntryNames": ["btn_getname",
   * "btn_permission"], "methodNames": ["businessTrack1"] }]
   */

  private static Map<String, String> viewTagHashMap = new HashMap<>();
  private static Map<String, String> methodHashMap = new HashMap<>();
  public static final String UNDER_LINE = "_";



  public static void intConfig(List<TrackConfigData> trackConfigDataList) {
    if (viewTagHashMap.size() != 0 || methodHashMap.size() != 0) {
      return;
    }
    int size = trackConfigDataList.size();
    Log.d("TrackConfig", Arrays.toString(trackConfigDataList.toArray()));
    for (int i = 0; i < size; i++) {
      TrackConfigData trackConfigData = trackConfigDataList.get(i);
      String className = trackConfigData.className;
      List<String> resourceEntryNames = trackConfigData.resourceEntryNames;
      List<String> methodNames = trackConfigData.methodNames;
      int resourceEntryNamesSize = resourceEntryNames.size();
      int methodNamesSize = methodNames.size();
      for (int k = 0; k < resourceEntryNamesSize; k++) {
        String resourceEntryName = resourceEntryNames.get(k);
        String viewTagName = className + UNDER_LINE + resourceEntryName;
        viewTagHashMap.put(viewTagName, viewTagName);
      }

      for (int j = 0; j < methodNamesSize; j++) {
        String methodName = methodNames.get(j);
        String methodTagName = className + UNDER_LINE + methodName;
        methodHashMap.put(methodTagName, methodTagName);
      }
    }

  }

  public static Map<String, String> getViewTagHashMap() {
    return viewTagHashMap;

  }

  public static Map<String, String> getMethodHashMap() {
    return methodHashMap;

  }



}
