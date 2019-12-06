package me.joy.scalpelplugin.utils;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.io.FileUtils;

/**
 * Created by Joy on 2019-11-22
 */
public class TransformUtils {

  /**
   * 不做任何处理，将输入原样输出
   * @param transformInvocation
   * @throws IOException
   */
  public static void outputOrigin(TransformInvocation transformInvocation) throws IOException {

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

}
