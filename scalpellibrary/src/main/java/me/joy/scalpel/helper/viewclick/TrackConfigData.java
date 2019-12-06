package me.joy.scalpel.helper.viewclick;

import androidx.annotation.Keep;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Joy on 2019-12-06
 */
@Keep
public  class TrackConfigData {

  public String className;
  public List<String> resourceEntryNames;
  public List<String> methodNames;

  @Override
  public String toString() {
    return "TrackConfigData{" + "className='" + className + '\'' + ", resourceEntryNames="
        + Arrays
        .toString(resourceEntryNames.toArray()) + ", " +
        "methodNames=" +
        Arrays.toString(methodNames.toArray()) + '}';
  }
}