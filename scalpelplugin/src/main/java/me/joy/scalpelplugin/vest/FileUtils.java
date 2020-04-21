package me.joy.scalpelplugin.vest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;
import me.joy.scalpelplugin.utils.L;

/**
 * Created by Joy on 2020/4/20
 */
public class FileUtils {

  public static String TAG = "FileUtils";

  public static void traverseFolder(String path, CallBack callBack) {
    File file = new File(path);
    if (file.exists()) {
      File[] files = file.listFiles();
      if (null == files || files.length == 0) {
        L.print(TAG, "the dir is empty");
        return;
      } else {
        for (File f : files) {
          if (f.isDirectory()) {
            L.print(TAG, "the dir Path:" + f.getAbsolutePath());
            traverseFolder(f.getAbsolutePath(), callBack);
          } else {
            L.print(TAG, "the file path:" + f.getAbsolutePath());
            callBack.doAction(f);

          }
        }
      }
    } else {
      L.print(TAG, "the file is not exists");
    }
  }

  public static void copyFileUsingFileChannels(File source, File dest) throws IOException {
    FileChannel inputChannel = null;
    FileChannel outputChannel = null;
    try {
      inputChannel = new FileInputStream(source).getChannel();
      outputChannel = new FileOutputStream(dest).getChannel();
      outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
    } finally {
      inputChannel.close();
      outputChannel.close();
    }
  }

  public static void createDirectory(String path) {
    if (isEmpty(path)) {
      return;
    }
    try {
      // 获得文件对象
      File f = new File(path);
      if (!f.exists()) {
        // 如果路径不存在,则创建
        f.mkdirs();
      }
    } catch (Exception e) {
      L.print(TAG, "createDirectory  Exception = " + e.getMessage());
    }
  }


  /**
   * 新建文件.
   *
   * @param path 文件路径
   */
  public static File createFile(String path) {
    if (isEmpty(path)) {
      return null;
    }
    try {
      // 获得文件对象
      File f = new File(path);
      if (f.exists()) {
        return f;
      }
      // 如果路径不存在,则创建
      if (!f.getParentFile().exists()) {
        f.getParentFile().mkdirs();
      }
      f.createNewFile();
      return f;
    } catch (Exception e) {
      L.print(TAG, "createFile Exception =  " + e.getMessage());
    }

    return null;
  }

  public static void modifyFileContent(List<String> filePathList, Map<String, String> map) {
    int size = filePathList.size();
    for (int i = 0; i < size; i++) {
      String filePath = filePathList.get(i);
      File file = new File(filePath);
      Long fileLength = file.length();
      byte[] fileContext = new byte[fileLength.intValue()];
      FileInputStream in = null;
      PrintWriter out = null;
      try {
        in = new FileInputStream(filePath);
        in.read(fileContext);
        // 避免出现中文乱码
        String str = new String(fileContext, "utf-8");
        for (Map.Entry<String, String> entry : map.entrySet()) {
          str = str.replace(entry.getKey(), entry.getValue());
        }
        out = new PrintWriter(filePath);
        out.write(str);
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          out.flush();
          out.close();
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static boolean isEmpty(String str) {
    return str == null || str.length() == 0 || str.equals("null");
  }


}
