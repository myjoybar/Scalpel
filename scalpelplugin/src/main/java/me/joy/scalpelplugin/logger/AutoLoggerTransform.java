package me.joy.scalpelplugin.logger;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent.ContentType;
import com.android.build.api.transform.QualifiedContent.Scope;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import me.joy.scalpelplugin.extention.ConfigHelper;
import me.joy.scalpelplugin.logger.visitor.LogClassVisitor;
import me.joy.scalpelplugin.utils.LogUtils;
import me.joy.scalpelplugin.utils.TransformUtils;
import org.apache.commons.io.FileUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * Created by Joy on 2019-08-14
 */
public class AutoLoggerTransform extends Transform {

  private static final String TAG = "AutoLoggerTransform";

  @Override
  public String getName() {
    return TAG;
  }


  @Override
  public Set<ContentType> getInputTypes() {
    //ContentType	文件的类型：CLASSES、RESOURCES、DEX、NATIVE_LIBS等
    return TransformManager.CONTENT_CLASS;
  }

  @Override
  public Set<? super Scope> getScopes() {
    //作用域：PROJECT、SUB_PROJECTS、EXTERNAL_LIBRARIES等
    return TransformManager.SCOPE_FULL_PROJECT;
  }

  @Override
  public boolean isIncremental() {
    return false;
  }


  @Override
  public void transform(TransformInvocation transformInvocation)
      throws TransformException, InterruptedException, IOException {

    boolean enable = ConfigHelper.getInstance().getScalpelExtension().isEnable();
    boolean enableLog = ConfigHelper.getInstance().getScalpelExtension().isEnableLog();
    if (enable && enableLog) {
      LogUtils.print(TAG, "start----->");
      long startTime = System.currentTimeMillis();
      //当前是否是增量编译
      boolean isIncremental = transformInvocation.isIncremental();
      //消费型输入，可以从中获取jar包和class文件夹路径。需要输出给下一个任务
      Collection<TransformInput> inputs = transformInvocation.getInputs();
      //引用型输入，无需输出。
      Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs();
      //OutputProvider管理输出路径，如果消费型输入为空，你会发现OutputProvider == null
      TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
      //删除之前的输出
      if (outputProvider != null) {
        outputProvider.deleteAll();
      }
      //输入文件：DirectoryInput集合与JarInput集合
      for (TransformInput input : inputs) {
        loopJarInput(input, outputProvider);
        loopDirectoryInput(input, outputProvider);
      }
      long cost = (System.currentTimeMillis() - startTime);
      LogUtils.print(TAG, "end-----> cost ：" + cost + " ms ");
    } else {
      LogUtils.print(TAG, "The master switch is：" + enable);
      LogUtils.print(TAG, "The enableLog is：" + enableLog);
      TransformUtils.outputOrigin(transformInvocation);
    }
  }


  private void loopJarInput(TransformInput input, TransformOutputProvider outputProvider)
      throws IOException {
    //JarInput	以jar包方式参与项目编译的所有本地jar包或远程jar包
    for (JarInput jarInput : input.getJarInputs()) {
      File dest = outputProvider.getContentLocation(
          jarInput.getFile().getAbsolutePath(),
          jarInput.getContentTypes(),
          jarInput.getScopes(),
          Format.JAR);
      //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
      FileUtils.copyFile(jarInput.getFile(), dest);
    }
  }


  /**
   * //DirectoryInput	以源码方式参与项目编译的所有目录结构及其目录下的源码文件
   */
  private void loopDirectoryInput(TransformInput input, TransformOutputProvider outputProvider)
      throws TransformException, InterruptedException, IOException {
    for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
      LogUtils.print(TAG,
          "==AutoLoggerTransform  DI = " + Arrays
              .toString(directoryInput.getFile().listFiles()));
      File dest = outputProvider.getContentLocation(directoryInput.getName(),
          directoryInput.getContentTypes(), directoryInput.getScopes(),
          Format.DIRECTORY);
      //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
      transformDir(directoryInput.getFile(), dest);
    }
  }

  private static void transformDir(File input, File dest) throws IOException {

    if (dest.exists()) {
      FileUtils.forceDelete(dest);
    }
    FileUtils.forceMkdir(dest);
    String srcDirPath = input.getAbsolutePath();
    String destDirPath = dest.getAbsolutePath();
    LogUtils.print(TAG, "=== transform dir = " + srcDirPath + ", " + destDirPath);
    for (File file : input.listFiles()) {
      String destFilePath = file.getAbsolutePath().replace(srcDirPath, destDirPath);
      File destFile = new File(destFilePath);
      if (file.isDirectory()) {
        transformDir(file, destFile);
      } else if (file.isFile()) {
        FileUtils.touch(destFile);
        transformSingleFile(file, destFile);
      }
    }
  }

  /**
   * 遍历单个文件 eg：MainActivityPlugin$1.class，MainActivityPlugin.class，MainActivity.class，BuildConfig.class
   * R.class
   */
  private static void transformSingleFile(File input, File dest) {
    LogUtils.print(TAG, "=== AutoLoggerTransform transformSingleFile ===" + input.getName());
    try {
      FileInputStream is = new FileInputStream(input);
      ClassReader cr = new ClassReader(is);
      ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
      LogClassVisitor adapter = new LogClassVisitor(cw);
      cr.accept(adapter, 0);
      FileOutputStream fos = new FileOutputStream(dest);
      fos.write(cw.toByteArray());
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }




}
