package me.joy.scalpel.helper.logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joy on 2019-08-21
 */
public class MethodCostTimeHelper {

  public static final Map<String, Long> METHOD_START_MAP = new HashMap<>();
  public static final Map<String, Long> METHOD_END_MAP = new HashMap<>();
  public static final String LOG_EXECUTION = " execution ";
  public static final String LOG_COST_TIME = "cost time：";
  public static final String LOG_COST_MS = "ms";


  public static String combineLogStrInfo(String methodName, boolean traceSpendTime,
      String methodTag) {
    StringBuilder stringBuilder = new StringBuilder(methodName);
    stringBuilder.append(LOG_EXECUTION);
    if (traceSpendTime) {
      stringBuilder.append(LOG_COST_TIME);
      stringBuilder.append(computeExecutionTime(traceSpendTime, methodTag));
      stringBuilder.append(LOG_COST_MS);
    }
    return stringBuilder.toString();

  }


  public static long computeExecutionTime(boolean traceSpendTime, String methodTag) {

    if (traceSpendTime) {
      Long startTime = METHOD_START_MAP.get(methodTag);
      Long endTime = METHOD_END_MAP.get(methodTag);
      if ((startTime = METHOD_START_MAP.get(methodTag)) != null
          && (endTime = METHOD_END_MAP.get(methodTag)) != null) {
        METHOD_START_MAP.remove(methodTag);
        METHOD_END_MAP.remove(methodTag);
        return endTime - startTime;
      }
    }

    return 0;
  }


  public static void addStartTime(boolean traceSpendTime, String methodTag) {
    if (traceSpendTime) {
      METHOD_START_MAP.put(methodTag, System.currentTimeMillis());
    }
  }

  public static void addEndTime(boolean traceSpendTime, String methodTag) {
    if (traceSpendTime) {
      METHOD_END_MAP.put(methodTag, System.currentTimeMillis());

    }
  }
}
