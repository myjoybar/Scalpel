package me.joy.scalpelplugin.vest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

  private List<String> newJavaOrKotlinFilePathList = new ArrayList<>();
  private List<String> manifestFilesPathList = new ArrayList<>();

  private Map<String, String> pkgNameMap = new HashMap<>();
  private Map<String, String> importPkNameMap = new HashMap<>();
  private Map<String, String> classNameMap = new HashMap<>();
  private Map<String, String> classNewObjMap = new HashMap<>();
  private Map<String, String> classStaticRefMap = new HashMap<>();
  private Map<String, String> classReflectMap = new HashMap<>();

  private Map<String, String> manifestActivityNamesMap = new HashMap<>();
  private Map<String, String> manifestApplicationNamesMap = new HashMap<>();
  private Map<String, String> manifestPackageNamesMap = new HashMap<>();

  private Map<String, String> confusePckMap = new HashMap<>();
  private Map<String, String> mapFilesInfos = new LinkedHashMap<>();
  private String mapFilePath = "";

  private static final String BLANK_SPACE = " ";
  private static final String LEFT_BRACKET1 = "(";
  private static final String LEFT_BRACKET2 = " ("; // 防止留有一个空格
  private static final String LEFT_BRACKET3 = "  (";// 防止留有二个空格
  private static final String DOT = ".";
  private static final String SEMICOLON = ";";
  private static final String CLASS = ".class";
  private static final String CLASS_KOTLIN = "::class.java";


  @TaskAction
  public void doVestTask() {
    execute();

  }

  private void execute() {
    String rootProjectDirPath = getProject().getRootProject().getRootDir().getAbsolutePath();
    // eg:  /Users/joybar/Documents/WorkSpaces/AndroidStudio/My/Scalpel
    L.print(TAG, "this rootProjectDirPath = " + rootProjectDirPath);
    List<String> vestModules = ConfigHelper.getInstance().getVestConfigExtension().vestModules();
    int size = vestModules.size();
    for (int i = 0; i < size; i++) {
      // clear map todo
      String pathOuter = rootProjectDirPath + "/" + vestModules.get(i) + "/src/main/";
      String pathInner = rootProjectDirPath + "/" + vestModules.get(i) + "/src/main/java";
      mapFilePath =
          rootProjectDirPath + "/" + vestModules.get(i) + "/build/outputs/vest/vestMap.txt";
      //eg: String path = "/Users/joybar/Documents/WorkSpaces/AndroidStudio/My/Scalpel/app/src/main/java";
      L.print(TAG, "this pathOuter: " + pathOuter);
      L.print(TAG, "this pathInner: " + pathInner);
      findAllAndroidManifestFilePath(pathOuter);
      findPckNameApplicationNameAndActivityNames();
      renameClassAndKtFiles(pathInner);
      recordManifestFileModifyContent();
      modifyFiles();
//      printMap(confusePckMap);
//      printMap(mapFilesInfos);
      saveMapFile(mapFilePath, mapFilesInfos);
    }

  }

  private void findAllAndroidManifestFilePath(String path) {
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


  private void findPckNameApplicationNameAndActivityNames() {
    int size = manifestFilesPathList.size();
    for (int i = 0; i < size; i++) {
      File inputXml = new File(manifestFilesPathList.get(i));
      SAXReader saxReader = new SAXReader();
      try {
        Document document = saxReader.read(inputXml);
        String pkgName = document.getRootElement().attributeValue("package");
        L.print(TAG, "this pkgName =  " + pkgName);
        manifestPackageNamesMap.put(pkgName, "");
        Element applicationElement = document.getRootElement().element("application");
        String applicationName = applicationElement.attributeValue("name");
        L.print(TAG, "this applicationName = " + applicationName);
        manifestApplicationNamesMap.put(applicationName, "");
        List<Element> activityElements = applicationElement.elements("activity");
        int activityElementsSize = activityElements.size();
        for (int j = 0; j < activityElementsSize; j++) {
          Element activityElement = activityElements.get(j);
          String activityName = activityElement.attributeValue("name");
          L.print(TAG, "this activityName = " + activityName);
          manifestActivityNamesMap.put(activityName, "");
        }

      } catch (DocumentException e) {
        L.print(TAG, "this DocumentException = " + e.getMessage());
      }

    }
  }

  private void renameClassAndKtFiles(String path) {
    FileUtils.traverseFolder(path, new CallBack<File>() {
      @Override
      public void doAction(File file) {
        L.print(TAG, "the file getName = " + file.getName());
        if (canRenameFile(file.getName())) {
          renameFileAndRecordModifyInfos(file);
        }
      }
    });
  }

  private void recordConfusePckMap(String simpleOriginFileAbsolutePath,
      Map<String, String> confusePckTempMap) {
    String[] pckNames = simpleOriginFileAbsolutePath.split("/");
    int size = pckNames.length;
    for (int i = 0; i < size; i++) {
      String oldStr = pckNames[i];
      if (!confusePckMap.containsKey(oldStr)) {
        String newStr = getRandomStr(3);
        confusePckMap.put(oldStr, newStr);
      }
      confusePckTempMap.put(oldStr, confusePckMap.get(oldStr));
    }
  }

  private String generateNewFilePath(String originFileAbsolutePath,
      String signal, int signalIndex, Map<String, String> confusePckTempMap) {
    String newFilePath = originFileAbsolutePath.substring(0, signalIndex) + signal;
    for (Map.Entry<String, String> entry : confusePckTempMap.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      newFilePath = newFilePath + value + "/";
    }
    return newFilePath;
  }


  private void renameFileAndRecordModifyInfos(File originFile) {
    ///Users/joybar/Documents/WorkSpaces/AndroidStudio/My/Scalpel/app/src/main/java/me/joy/scalpelasm/testrename/Test1.java
    String originFileAbsolutePath = originFile.getAbsolutePath();
    //Test1.java
    String originFileName = originFile.getName();
    L.print(TAG, " the originFileAbsolutePath = " + originFileAbsolutePath);
    L.print(TAG, " the originFileName = " + originFileName);

    String signal = "/main/java/";
    int signalIndex = originFileAbsolutePath.lastIndexOf(signal);
    String simpleOriginFileAbsolutePath = originFileAbsolutePath
        .substring(signalIndex + signal.length(),
            originFileAbsolutePath.lastIndexOf(originFileName) - 1);
    L.print(TAG, " the simpleOriginFileAbsolutePath = " + simpleOriginFileAbsolutePath);

    Map<String, String> confusePckTempMap = new LinkedHashMap<>();
    recordConfusePckMap(simpleOriginFileAbsolutePath, confusePckTempMap);
    String newFilePath = generateNewFilePath(originFileAbsolutePath, signal, signalIndex,
        confusePckTempMap);
    //Test1
    String oldFileName = originFileName.substring(0, originFileName.lastIndexOf("."));
    // /Users/joybar/Documents/WorkSpaces/AndroidStudio/My/Scalpel/app/src/main/java/fao/jmo/spb/qku/lcb.java
    newFilePath =
        newFilePath + originFileName.replace(oldFileName, generateNewFileName(oldFileName));

    L.print(TAG, " originFileName = " + originFileName);
    L.print(TAG, " simpleOriginFileAbsolutePath = " + simpleOriginFileAbsolutePath);
    L.print(TAG, " newFilePath = " + newFilePath);
    L.print(TAG, " oldFileName = " + oldFileName);

    String newFileAbsolutePath = newFilePath;
    String newFileAbsolutePathDir = newFilePath.substring(0, newFilePath.lastIndexOf("/") + 1);
    L.print(TAG, " newFileAbsolutePath = " + newFileAbsolutePath);
    L.print(TAG, " newFileAbsolutePathDir = " + newFileAbsolutePathDir);
    FileUtils.createDirectory(newFileAbsolutePathDir);
    File newFile = FileUtils.createFile(newFileAbsolutePath);
    String newFileFullName = newFile.getName();

    L.print(TAG, " the newFilePath :" + newFileAbsolutePath);
    L.print(TAG, " the newFileFullName :" + newFile.getName());

    String originClassPackName = originFileAbsolutePath
        .substring(originFileAbsolutePath.lastIndexOf(signal) + signal.length(),
            originFileAbsolutePath.lastIndexOf(".")).replace("/", ".");
    String newClassPackName = newFileAbsolutePath
        .substring(newFileAbsolutePath.lastIndexOf(signal) + signal.length(),
            newFileAbsolutePath.lastIndexOf(".")).replace("/", ".");
    String oldFileSimpleName = originFileName.substring(0, originFileName.lastIndexOf("."));
    String newFileSimpleName = newFileFullName.substring(0, newFileFullName.lastIndexOf("."));
    String oldPackName = originClassPackName.substring(0, originClassPackName.lastIndexOf("."));
    String newPackName = newClassPackName.substring(0, newClassPackName.lastIndexOf("."));
    L.print(TAG, " oldFileSimpleName :" + oldFileSimpleName);
    L.print(TAG, " newFileSimpleName :" + newFileSimpleName);
    L.print(TAG, " originClassPackName :" + originClassPackName);
    L.print(TAG, " newClassPackName :" + newClassPackName);
    L.print(TAG, " originClassPackName :" + originClassPackName);
    L.print(TAG, " newClassPackName :" + newClassPackName);
    L.print(TAG, " oldPackName :" + oldPackName);
    L.print(TAG, " newPackName :" + newPackName);

    pkgNameMap.put(oldPackName + SEMICOLON, newPackName + SEMICOLON);
    importPkNameMap.put(originClassPackName, newClassPackName);
    classNameMap.put(BLANK_SPACE + oldFileSimpleName + BLANK_SPACE,
        BLANK_SPACE + newFileSimpleName + BLANK_SPACE);
    classNewObjMap
        .put(BLANK_SPACE + oldFileSimpleName + LEFT_BRACKET1,
            BLANK_SPACE + newFileSimpleName + LEFT_BRACKET1);
    classNewObjMap
        .put(BLANK_SPACE + oldFileSimpleName + LEFT_BRACKET2,
            BLANK_SPACE + newFileSimpleName + LEFT_BRACKET2);
    classNewObjMap
        .put(BLANK_SPACE + oldFileSimpleName + LEFT_BRACKET3,
            BLANK_SPACE + newFileSimpleName + LEFT_BRACKET3);
    classStaticRefMap
        .put(BLANK_SPACE + oldFileSimpleName + DOT, BLANK_SPACE + newFileSimpleName + DOT);
    classReflectMap.put(oldFileSimpleName + CLASS, newFileSimpleName + CLASS);
    classReflectMap.put(oldFileSimpleName + CLASS_KOTLIN, newFileSimpleName + CLASS_KOTLIN);
    newJavaOrKotlinFilePathList.add(newFile.getAbsolutePath());
    mapFilesInfos.put(
        originFile.getAbsolutePath().substring(originFile.getAbsolutePath().lastIndexOf(signal)),
        newFile.getAbsolutePath().substring(newFile.getAbsolutePath().lastIndexOf(signal)));
    boolean b = originFile.renameTo(newFile);
    L.print(TAG, "rename the file result:" + b);

  }


  private void recordManifestFileModifyContent() {
    Iterator<Map.Entry<String, String>> iterator = importPkNameMap.entrySet()
        .iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, String> entry = iterator.next();
      String key = entry.getKey();
      String value = entry.getValue();
      //recordManifestFileModifyContent(key,value,manifestPackageNamesMap);
      recordManifestFileModifyContent(key, value, manifestApplicationNamesMap);
      recordManifestFileModifyContent(key, value, manifestActivityNamesMap);
    }
    recordManifestFileModifyContent(manifestPackageNamesMap);
  }

  private void recordManifestFileModifyContent(String originClassPackName, String newClassPackName,
      Map<String, String> map) {
    Iterator<Map.Entry<String, String>> iterator = map.entrySet()
        .iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, String> entry = iterator.next();
      String key = entry.getKey();
      if (originClassPackName.endsWith(key)) {
        String value = entry.getValue();
        String[] originClassPackNames = originClassPackName.split("\\.");
        String[] newClassPackNames = newClassPackName.split("\\.");
        String[] keyNames = key.split("\\.");
        String[] newValues = new String[keyNames.length];
        for (int i = originClassPackNames.length - 1, j = keyNames.length - 1; i >= 0 && j >= 0;
            i--, j--) {
          if (originClassPackNames[i].equals(keyNames[j])) {
            newValues[j] = newClassPackNames[i];
          }
        }
        String replaceValueStr = ".";
        for (int j = 0; j < newValues.length; j++) {
          if (!FileUtils.isEmpty(newValues[j])) {
            replaceValueStr = replaceValueStr + newValues[j] + ".";
          }
        }
        replaceValueStr = replaceValueStr.substring(0, replaceValueStr.length() - 1);
        L.print(TAG, "map key = " + key + " ,replaceValueStr = " + replaceValueStr);
        map.put(key, replaceValueStr);

      }
    }
  }


  private void recordManifestFileModifyContent(Map<String, String> map) {
    Iterator<Map.Entry<String, String>> iterator = map.entrySet()
        .iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, String> entry = iterator.next();
      String key = entry.getKey();
      String value = entry.getValue();
      String replaceValue = "";
      String[] keyNames = key.split("\\.");
      int size = keyNames.length;
      L.print(TAG, "size = " + size);
      String[] values = new String[size];
      for (int i = 0; i < size; i++) {
        String str = keyNames[i];
        if (confusePckMap.containsKey(str)) {
          str = confusePckMap.get(str);
        }
        values[i] = str;
      }
      for (int j = 0; j < size; j++) {
        replaceValue = replaceValue + values[j] + ".";
      }
      replaceValue = replaceValue.substring(0, replaceValue.length() - 1);
      L.print(TAG, "map key = " + key + " ,replaceValue = " + replaceValue);
      map.put(key, replaceValue);
    }
  }


  boolean canRenameFile(String fileName) {
    return (fileName.endsWith(".java")
        || fileName.endsWith(".kt")
    );
  }


  private void modifyFiles() {
    Map<String, String> javaOrKotlinFileMap = new HashMap<>();
    javaOrKotlinFileMap.putAll(pkgNameMap);
    javaOrKotlinFileMap.putAll(importPkNameMap);
    javaOrKotlinFileMap.putAll(classNameMap);
    javaOrKotlinFileMap.putAll(classNewObjMap);
    javaOrKotlinFileMap.putAll(classStaticRefMap);
    javaOrKotlinFileMap.putAll(classReflectMap);
    FileUtils.modifyFileContent(newJavaOrKotlinFilePathList, javaOrKotlinFileMap);

    Map<String, String> manifestFileMap = new HashMap<>();
    manifestFileMap.putAll(manifestActivityNamesMap);
    manifestFileMap.putAll(manifestApplicationNamesMap);
    manifestFileMap.putAll(manifestPackageNamesMap);
    FileUtils.modifyFileContent(manifestFilesPathList, manifestFileMap);
  }


  private static String getRandomStr(int length) {
    String str = "abcdefghijklmnopqrstuvwxyz";
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++) {
      int number = random.nextInt(26);
      sb.append(str.charAt(number));
    }
    return sb.toString();
  }

  private String generateNewFileName(String originName) {
    if (originName.contains("Activity") || originName.contains("activity")) {
      return getRandomStr(3) + "Activity";
    }
    if (originName.contains("Application") || originName.contains("application")) {
      return getRandomStr(3) + "Application";
    }
    return getRandomStr(3);
  }


  private void printMap(Map<String, String> map) {
    for (Map.Entry<String, String> entry : map.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      L.print(TAG, " map key = " + key + ",  value = " + value);
    }
  }


  private void saveMapFile(String filePath, Map<String, String> map) {

    StringBuilder stringBuilder = new StringBuilder();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      L.print(TAG, " map key = " + key + ",  value = " + value);
      stringBuilder.append(key);
      stringBuilder.append("  -->  ");
      stringBuilder.append(value);
      stringBuilder.append("\r\n");
    }
    FileUtils.saveFile(filePath, stringBuilder.toString());
  }


}
