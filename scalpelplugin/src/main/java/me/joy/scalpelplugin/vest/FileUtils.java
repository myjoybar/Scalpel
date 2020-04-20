package me.joy.scalpelplugin.vest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.joy.scalpelplugin.utils.L;

/**
 * Created by Joy on 2020/4/20
 */
public class FileUtils {

  public static String TAG = "FileUtils";
  public static void traverseFolder(String path,CallBack callBack) {
    File file = new File(path);
    if (file.exists()) {
      File[] files = file.listFiles();
      if (null == files || files.length == 0) {
        L.print(TAG,"the dir is empty");
        return;
      } else {
        for (File f : files) {
          if (f.isDirectory()) {
            L.print(TAG,"the dir Path:" + f.getAbsolutePath());
            traverseFolder(f.getAbsolutePath(),callBack);
          } else {
            L.print(TAG,"the file path:" + f.getAbsolutePath());
            callBack.doAction(f);

          }
        }
      }
    } else {
      L.print(TAG,"the file is not exists");
    }
  }



}
