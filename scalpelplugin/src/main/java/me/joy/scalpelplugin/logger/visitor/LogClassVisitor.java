package me.joy.scalpelplugin.logger.visitor;

import me.joy.scalpelplugin.utils.LogUtils;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author gavin
 * @date 2019/2/18 lifecycle class visitor
 */
public class LogClassVisitor extends ClassVisitor implements Opcodes {

  private final static String TAG = "LogMethodVisitor";
  private String className;
  private boolean ignore;

  public LogClassVisitor(ClassVisitor cv) {
    super(Opcodes.ASM5, cv);
  }


  @Override
  public void visit(int version, int access, String name, String signature, String superName,
      String[] interfaces) {
    // 开始访问某个文件 android/support/v7/app/AppCompatActivity
    this.className = name;
    LogUtils.print(TAG, "visit -----> started ：className:" + className);

    if ((access & Opcodes.ACC_INTERFACE) != 0) {
      // interface method has no code
      ignore = true;
    }
    super.visit(version, access, name, signature, superName, interfaces);
  }

  /**
   * 读取类的注解
   */
  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {

    LogUtils.print(TAG,
        String.format("visitAnnotation className = %s, desc = %s", className, desc));

    return super.visitAnnotation(desc, visible);
  }

  @Override
  public void visitAttribute(Attribute attr) {
    LogUtils.print(TAG, " visitAttribute: attr=" + attr);
    super.visitAttribute(attr);
  }

  /**
   * 访问静态变量和成员变量
   */
  @Override
  public FieldVisitor visitField(int access, String name, String desc, String signature,
      Object value) {

    LogUtils.print(TAG,
        String.format("visitField access = %s, name = %s, desc = %s, signature = %s, value = %s",
            access, name, desc, signature, value));

    return super.visitField(access, name, desc, signature, value);
  }


  /**
   * 访问方法
   */
  @Override
  public MethodVisitor visitMethod(int access, String methodName, String desc, String signature,
      String[] exceptions) {

    LogUtils.print(TAG,
        String.format("visitMethod className = %s, methodName = %s, desc = %s, signature = %s",
            className, methodName, desc, signature));

    if (ignore) {
      return super.visitMethod(access, methodName, desc, signature, exceptions);
    }
    MethodVisitor oriMv = cv.visitMethod(access, methodName, desc, signature, exceptions);
    return new LogMethodVisitor(oriMv, access, className, methodName, desc, signature);

//    MethodVisitor methodVisitor = super.visitMethod(access, methodName, desc, signature, exceptions);
//    return new LogMethodVisitor_new(methodVisitor, access, className, methodName, desc, signature);

  }


  @Override
  public void visitEnd() {
    LogUtils.print(TAG, " visit -----> end ClassName:" + className);
    super.visitEnd();
  }


}
