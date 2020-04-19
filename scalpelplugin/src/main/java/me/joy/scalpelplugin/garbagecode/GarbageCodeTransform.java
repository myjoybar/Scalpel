package me.joy.scalpelplugin.garbagecode;

import com.android.build.api.transform.QualifiedContent.ContentType;
import com.android.build.api.transform.QualifiedContent.Scope;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;
import java.io.IOException;
import java.util.Set;
import me.joy.scalpelplugin.extention.ConfigHelper;
import me.joy.scalpelplugin.garbagecode.visitor.GarbageCodeClassVisitor;
import me.joy.scalpelplugin.helper.TransformService;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Created by Joy on 2019-08-14
 */
public class GarbageCodeTransform extends Transform {

  private static final String TAG = "GarbageCodeTransform";

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
      throws IOException {

    new TransformService() {
      @Override
      protected ClassVisitor getClassVisitor(ClassWriter classWriter) {
        GarbageCodeClassVisitor classVisitor = new GarbageCodeClassVisitor(classWriter);
        return classVisitor;
      }

      @Override
      protected boolean isIgnoredFiles(String fileName) {
        return (fileName.contains("$")
            || !fileName.endsWith(".class")
            || fileName.equals("BuildConfig.class")
            || fileName.equals("R.class")
            || fileName.startsWith("R$")

        );
      }

      @Override
      protected boolean enable() {
        return  ConfigHelper.getInstance().getGarbageConfigExtension()
            .isEnableGarbageCode();
      }
    }.transform(transformInvocation);

  }


}
