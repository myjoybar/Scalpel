package me.joy.scalpelplugin.vest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import me.joy.scalpelplugin.extention.ConfigHelper;
import me.joy.scalpelplugin.utils.L;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * Created by Joy on 2020-04-10
 */
public class VestTask extends DefaultTask {

  public static String TAG = "VestTask";

  public VestTask() {
    setGroup("Vest");
  }

  public List<String> newJavaOrKotlinFilePathList = new ArrayList<>();
  public List<String> manifestFilesPathList = new ArrayList<>();
  public Map<String, String> importPkNameMap = new HashMap<>();
  public Map<String, String> classNameMap = new HashMap<>();
  public Map<String, String> classNewObjMap = new HashMap<>();
  public Map<String, String> classStaticRefMap = new HashMap<>();
  public Map<String, String> classReflectMap = new HashMap<>();

  public Map<String, String> activityNamesMap = new HashMap<>();
  public Map<String, String> applicationNamesMap = new HashMap<>();


  public static String BLANK_SPACE = " ";
  public static String LEFT_BRACKET1 = "(";
  public static String LEFT_BRACKET2 = " ("; // 防止留有一个空格
  public static String LEFT_BRACKET3 = "  (";// 防止留有二个空格
  public static String DOT = ".";
  public static String CLASS = ".class";
  public static String CLASS_KOTLIN = "::class.java";

  @TaskAction
  public void doVestTask() {
    execute();

  }

  private void execute() {
    String rootProjectDirPath = getProject().getRootProject().getRootDir().getAbsolutePath();
    L.print(TAG, "this rootProjectDirPath: "
        + rootProjectDirPath);   // /Users/joybar/Documents/WorkSpaces/AndroidStudio/My/Scalpel

    List<String> vestModules = ConfigHelper.getInstance().vestModules();
    int size = vestModules.size();
    for (int i = 0; i < size; i++) {
      String pathOuter = rootProjectDirPath + "/" + vestModules.get(i) + "/src/main/";
      String pathInner = rootProjectDirPath + "/" + vestModules.get(i) + "/src/main/java";
      //String path = "/Users/joybar/Documents/WorkSpaces/AndroidStudio/My/Scalpel/app/src/main/java";
      L.print(TAG, "this pathOuter: " + pathOuter);
      L.print(TAG, "this pathInner: " + pathInner);
      findAllAndroidManifestFiles(pathOuter);
      findAllApplicationAndActivityNames();
      renameFiles(pathInner);
//      changeJavaOrKotlinFileContent(newJavaOrKotlinFilePathList);
//      changeManifestFileContent(manifestFilesPathList);
    }

  }

  private void findAllAndroidManifestFiles(String path) {
    FileUtils.traverseFolder(path, new CallBack<File>() {
      @Override
      public void doAction(File file) {
        L.print(TAG, "the file getName:" + file.getName());
        if (file.getName().equals("AndroidManifest.xml")) {
          manifestFilesPathList.add(file.getAbsolutePath());
          L.print(TAG, "this manifestFiles path: " + file.getAbsolutePath());
        }
      }
    });
  }

  private void findAllApplicationAndActivityNames() {
    int size = manifestFilesPathList.size();
    for (int i = 0; i < size; i++) {
      File inputXml = new File(manifestFilesPathList.get(i));
      SAXReader saxReader = new SAXReader();
      try {
        Document document = saxReader.read(inputXml);
        Element applicationElement = document.getRootElement().element("application");
        String applicationName = applicationElement.attributeValue("name");
        // applicationName = applicationName.substring(applicationName.lastIndexOf(".") + 1);
        L.print(TAG, "this applicationName " + applicationName);
        applicationNamesMap.put(applicationName, "");
        List<Element> activityElements = applicationElement.elements("activity");
        int activityElementsSize = activityElements.size();
        for (int j = 0; j < activityElementsSize; j++) {
          Element activityElement = activityElements.get(j);
          String activityName = activityElement.attributeValue("name");
          // activityName = activityName.substring(1, activityName.length());
          L.print(TAG, "this activityName " + activityName);
          activityNamesMap.put(activityName, "");
        }

      } catch (DocumentException e) {
        L.print(TAG, "this DocumentException: " + e.getMessage());
      }

    }
  }

  private void renameFiles(String path) {
    FileUtils.traverseFolder(path, new CallBack<File>() {
      @Override
      public void doAction(File file) {
        L.print(TAG, "the file getName:" + file.getName());
        if (canRenameFile(file.getName())) {
          renameFile(file);
        }
      }
    });

  }


  private void renameFile(File originFile) {
    String originFileAbsolutePath = originFile.getAbsolutePath();
    String originFileName = originFile.getName();
    L.print(TAG, " the origin filePath:" + originFileAbsolutePath);
    L.print(TAG, " the origin fileName:" + originFileName);

    int index = originFileAbsolutePath.indexOf(originFileName);
    String newFileAbsolutePath =
        originFileAbsolutePath.substring(0, index) + getPrefixStr(originFileName) + originFileName;

    File newFile = new File(newFileAbsolutePath);

    String newFileName = newFile.getName();
    L.print(TAG, " the newFilePath :" + newFileAbsolutePath);
    L.print(TAG, " the newFileName :" + newFile.getName());

    String signal = "/main/java/";
    String originPackName = originFileAbsolutePath
        .substring(originFileAbsolutePath.lastIndexOf(signal) + signal.length(),
            originFileAbsolutePath.lastIndexOf(".")).replace("/", ".");
    String newPackName = newFileAbsolutePath
        .substring(newFileAbsolutePath.lastIndexOf(signal) + signal.length(),
            newFileAbsolutePath.lastIndexOf(".")).replace("/", ".");
    String oldName = originFileName.substring(0, originFileName.lastIndexOf("."));

    String newName = newFileName.substring(0, newFileName.lastIndexOf("."));
    L.print(TAG, " oldName :" + oldName);
    L.print(TAG, " newName :" + newName);

    importPkNameMap.put(originPackName, newPackName);
    classNameMap.put(BLANK_SPACE + oldName + BLANK_SPACE, BLANK_SPACE + newName + BLANK_SPACE);
    classNewObjMap
        .put(BLANK_SPACE + oldName + LEFT_BRACKET1, BLANK_SPACE + newName + LEFT_BRACKET1);
    classNewObjMap
        .put(BLANK_SPACE + oldName + LEFT_BRACKET2, BLANK_SPACE + newName + LEFT_BRACKET2);
    classNewObjMap
        .put(BLANK_SPACE + oldName + LEFT_BRACKET3, BLANK_SPACE + newName + LEFT_BRACKET3);
    classStaticRefMap.put(BLANK_SPACE + oldName + DOT, BLANK_SPACE + newName + DOT);
    classReflectMap.put(oldName + CLASS, newName + CLASS);
    classReflectMap.put(oldName + CLASS_KOTLIN, newName + CLASS_KOTLIN);

    Iterator<Map.Entry<String, String>> iterator = activityNamesMap.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, String> entry = iterator.next();
      String key = entry.getKey();
      String value = entry.getValue();
      if (originFileAbsolutePath.replace("/", ".").contains(key)) {
        activityNamesMap.put(key, key.replace(oldName, newName));

      }
    }

    Iterator<Map.Entry<String, String>> it = applicationNamesMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> entry = it.next();
      String key = entry.getKey();
      String value = entry.getValue();
      if (originFileAbsolutePath.replace("/", ".").contains(key)) {
        applicationNamesMap.put(key, key.replace(oldName, newName));
      }
    }

    newJavaOrKotlinFilePathList.add(newFile.getAbsolutePath());

    L.print(TAG, " originPackName :" + originPackName);
    L.print(TAG, " newPackName :" + newPackName);
//    boolean b = originFile.renameTo(newFile);
//    L.print(TAG, "rename the file result:" + b);

  }


  private String getPrefixStr(String name) {
    return "AAA";
  }

  private String getSuffixStr() {
    return "BBB";
  }


  boolean canRenameFile(String fileName) {
    return fileName.endsWith(".java")
        || fileName.endsWith(".kt")
        ;
  }


  /**
   * 替换Java ，Kt文件内容
   */
  private void changeJavaOrKotlinFileContent(List<String> newFilePathList) {

    int size = newFilePathList.size();
    for (int i = 0; i < size; i++) {
      String filePath = newFilePathList.get(i);
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
        for (Map.Entry<String, String> entry : importPkNameMap.entrySet()) {
          str = str.replace(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, String> entry : classNameMap.entrySet()) {
          str = str.replace(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, String> entry : classNewObjMap.entrySet()) {
          str = str.replace(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, String> entry : classStaticRefMap.entrySet()) {
          str = str.replace(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, String> entry : classReflectMap.entrySet()) {
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


  /**
   * 替换ManifestFile文件内容
   */
  private void changeManifestFileContent(List<String> newFilePathList) {

    int size = newFilePathList.size();
    for (int i = 0; i < size; i++) {
      String filePath = newFilePathList.get(i);
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
        for (Map.Entry<String, String> entry : activityNamesMap.entrySet()) {
          String key = entry.getKey();
          String value = entry.getValue();
          L.print(TAG, " activityNamesMap key = " + key);
          L.print(TAG, " activityNamesMap value = " + value);
          str = str.replace(key, value);
        }

        for (Map.Entry<String, String> entry : applicationNamesMap.entrySet()) {
          String key = entry.getKey();
          String value = entry.getValue();
          L.print(TAG, " applicationNamesMap key = " + key);
          L.print(TAG, " applicationNamesMap value = " + value);
          str = str.replace(key, value);
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


}
