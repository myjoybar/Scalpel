package me.joy.scalpelplugin.helper;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import me.joy.scalpelplugin.utils.L;
import org.apache.commons.io.FileUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Created by Joy on 2019-11-22
 */
public abstract class TransformService {

  private static final String TAG = "TransformUtils";

  public void transform(TransformInvocation transformInvocation)
      throws IOException {

    if (enable()) {
      L.print(TAG, "transform start");
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
      L.print(TAG, "transform finish cost ：" + cost + " ms ");
    } else {
      outputOrigin(transformInvocation);
    }

  }

  /**
   * 不做任何处理，将输入原样输出
   */
  private void outputOrigin(TransformInvocation transformInvocation) throws IOException {

    // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
    Collection<TransformInput> inputs = transformInvocation.getInputs();
    TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();

    //输入文件：DirectoryInput集合与JarInput集合
    for (TransformInput input : inputs) {
      //JarInput	以jar包方式参与项目编译的所有本地jar包或远程jar包
      //对类型为jar文件的input进行遍历
      for (JarInput jarInput : input.getJarInputs()) {
        File dest = outputProvider.getContentLocation(
            jarInput.getFile().getAbsolutePath(),
            jarInput.getContentTypes(),
            jarInput.getScopes(),
            Format.JAR);
        //将修改过的字节码copy到dest
        FileUtils.copyFile(jarInput.getFile(), dest);
      }

      //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
      for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
        // 获取output目录
        File dest = outputProvider.getContentLocation(directoryInput.getName(),
            directoryInput.getContentTypes(), directoryInput.getScopes(),
            Format.DIRECTORY);
        // 将input的目录复制到output指定目录
        FileUtils.copyDirectory(directoryInput.getFile(), dest);

      }

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
      L.print(TAG, "loopJarInput: jarInput.getFile().getName() = " + jarInput.getFile().getName());
      //将修改过的字节码copy到dest
      FileUtils.copyFile(jarInput.getFile(), dest);
    }
  }


  /**
   * //DirectoryInput	以源码方式参与项目编译的所有目录结构及其目录下的源码文件
   */
  private void loopDirectoryInput(TransformInput input, TransformOutputProvider outputProvider)
      throws IOException {
    for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
      L.print(TAG, "loopDirectoryInput： dir = " + Arrays
          .toString(directoryInput.getFile().listFiles()));
      File dest = outputProvider.getContentLocation(directoryInput.getName(),
          directoryInput.getContentTypes(), directoryInput.getScopes(),
          Format.DIRECTORY);
      //将修改过的字节码copy到dest
      transformDir(directoryInput.getFile(), dest);
    }
  }


  /**
   * 遍历处理文件夹
   */
  private void transformDir(File input, File dest) throws IOException {

    if (dest.exists()) {
      FileUtils.forceDelete(dest);
    }
    FileUtils.forceMkdir(dest);
    String srcDirPath = input.getAbsolutePath();
    String destDirPath = dest.getAbsolutePath();
    L.print(TAG, "transformDir： srcDirPath = " + srcDirPath);
    L.print(TAG, "transformDir： destDirPath = " + destDirPath);
    for (File file : input.listFiles()) {
      String destFilePath = file.getAbsolutePath().replace(srcDirPath, destDirPath);
      if (file.isDirectory()) {
        L.print(TAG, "transformDir：is Directory Name = " + file.getName());
        File destFile = new File(destFilePath);
        transformDir(file, destFile);
      } else if (file.isFile()) {
        L.print(TAG, "transformDir：is file Name = " + file.getName());
        L.print(TAG, "transformDir：is file Name  destFilePath = " + destFilePath);
        File destFile = new File(destFilePath);
        FileUtils.touch(destFile);
        transformSingleFile(file, destFile);
      }
    }
  }


  /**
   * 遍历单个文件 eg：MainActivityPlugin$1.class，MainActivityPlugin.class，MainActivity.class，BuildConfig.class
   * R.class
   */
  private void transformSingleFile(File input, File dest) {
    try {
      L.print(TAG, "transformSingleFile： File Name = " + input.getName());
      if (isIgnoredFiles(input.getName())) {
        FileUtils.copyFile(input, dest);
      } else {
        FileInputStream inputStream = new FileInputStream(input);
        ClassReader classReader = new ClassReader(inputStream);//创建一个对象，接受一个被修改class类的inputStream，
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor classVisitor = getClassVisitor(classWriter);
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES); //对象cr接受一个cv对象并完成class的修改
//      classReader.accept(classVisitor, 0);
        FileOutputStream fos = new FileOutputStream(dest);
        fos.write(classWriter.toByteArray()); //返回class修改完后生成新的class字节流
        fos.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  protected abstract ClassVisitor getClassVisitor(ClassWriter classWriter);

  protected abstract boolean isIgnoredFiles(String fileName);

  protected abstract boolean enable();


}
