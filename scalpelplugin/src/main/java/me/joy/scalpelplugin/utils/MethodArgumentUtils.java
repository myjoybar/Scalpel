package me.joy.scalpelplugin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.objectweb.asm.Type;

/**
 * Created by Joy on 2019-08-16
 */
public class MethodArgumentUtils {
  private final static String TAG = "MethodArgumentUtils";

  public static char[] parseMethodArguments(String desc) {
    LogUtils.print(TAG, "parseMethodArguments ,desc = " + desc );
    String[] splitDesc = splitMethodDesc(desc);


    char[] returnChars = new char[splitDesc.length];
    LogUtils.print(TAG, "parseMethodArguments ,returnChars.length = " + returnChars.length );
    int count = 0;
    for(String type : splitDesc) {
      if(type.startsWith("L") || type.startsWith("[")) {
        returnChars[count] = 'L';
      }
      else {
        if(type.length() > 1) { throw new RuntimeException(); }
        returnChars[count] = type.charAt(0);
      }
      count += 1;
    }
    return returnChars;
  }

  public static String[] splitMethodDesc(String desc) {
    Type[] argTypes = Type.getArgumentTypes(desc);
    int arraylen =argTypes.length;

    int beginIndex = desc.indexOf('(');
    int endIndex = desc.lastIndexOf(')');
    if((beginIndex == -1 && endIndex != -1) || (beginIndex != -1 && endIndex == -1)) {
      System.err.println(beginIndex);
      System.err.println(endIndex);
      throw new RuntimeException();
    }
    String x0;
    if(beginIndex == -1 && endIndex == -1) {
      x0 = desc;
    }
    else {
      x0 = desc.substring(beginIndex + 1, endIndex);
    }
    Pattern pattern = Pattern.compile("\\[*L[^;]+;|\\[[ZBCSIFDJ]|[ZBCSIFDJ]"); //Regex for desc \[*L[^;]+;|\[[ZBCSIFDJ]|[ZBCSIFDJ]
    Matcher matcher = pattern.matcher(x0);
    String[] listMatches = new String[arraylen];
    int counter = 0;
    while(matcher.find()) {
      listMatches[counter] = matcher.group();
      counter += 1;
    }
    return listMatches;
  }

  public static void  parseMethod(String description){
    Type[] argTypes = Type.getArgumentTypes(description);
    int size = argTypes.length;
    LogUtils.print(TAG, "parseMethod ,size = " + size );
    for (int i = 0; i <size ; i++) {
      Type type = argTypes[i];
      LogUtils.print(TAG, "parseMethod ,i = " + i +", type = "+ type +", type.toString = "+ type.toString()+", type.getClassName = "+ type.getClassName());
    }
    if (size >= 0) {

    }

  }
}
