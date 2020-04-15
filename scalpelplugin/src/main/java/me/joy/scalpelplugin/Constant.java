package me.joy.scalpelplugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joy on 2019-08-14
 */
public class Constant {
  public static final String SCALPEL_CONFIG =  "ScalpelConfig";
  public static final String GARBAGE_CONFIG =  "GarbageConfig";

  public static final String LOG_TRACE_METHOD_ANNOTATION = "Lme/joy/scalpel/annotation/LogTrace;";
  public static final String METHOD_COST_TIME_HELPER_CLASS_OWNER = "me/joy/scalpel/helper/MethodCostTimeHelper";

  public static final String METHOD_VIEW_CLICK_DESC = "(Landroid/view/View;)V";
  public static final String METHOD_VIEW_CLICK_NAME = "onClick";



  public static final Object OBJECT = new Object();

  public static final String METHOD_ACTIVITY_ONCREATE ="onCreate";
  public static final String METHOD_ACTIVITY_ONSTART ="onStart";
  public static final String METHOD_ACTIVITY_ONRESUME ="onResume";
  public static final String METHOD_ACTIVITY_ONSTOP ="onStop";
  public static final String METHOD_ACTIVITY_ONDESTROY ="onDestroy";

  public static final Map<String,Object> METHOD_ACTIVITY_MAP = new HashMap<>();

  {

    METHOD_ACTIVITY_MAP.put(METHOD_ACTIVITY_ONCREATE,OBJECT);
    METHOD_ACTIVITY_MAP.put(METHOD_ACTIVITY_ONSTART,OBJECT);
    METHOD_ACTIVITY_MAP.put(METHOD_ACTIVITY_ONRESUME,OBJECT);
    METHOD_ACTIVITY_MAP.put(METHOD_ACTIVITY_ONSTOP,OBJECT);
    METHOD_ACTIVITY_MAP.put(METHOD_ACTIVITY_ONDESTROY,OBJECT);
  }

}
