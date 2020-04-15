package me.joy.scalpelplugin.garbagecode.visitor;

import me.joy.scalpelplugin.utils.L;
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
public class GarbageCodeClassVisitor extends ClassVisitor implements Opcodes {

  private final static String TAG = "GarbageCodeClassVisitor";
  private String className;
  private boolean ignore;
  private boolean isInterface;

  public GarbageCodeClassVisitor(ClassVisitor cv) {
    super(Opcodes.ASM5, cv);
  }


  @Override
  public void visit(int version, int access, String name, String signature, String superName,
      String[] interfaces) {
    // 开始访问某个文件 android/support/v7/app/AppCompatActivity
    this.className = name;
    isInterface = (access & ACC_INTERFACE) != 0;
    L.print(TAG, "visit：className:" + className);
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

    L.print(TAG,
        String.format("visitAnnotation：  className = %s, desc = %s", className, desc));

    return super.visitAnnotation(desc, visible);
  }

  @Override
  public void visitAttribute(Attribute attr) {
    L.print(TAG, " visitAttribute：  attr=" + attr);
    super.visitAttribute(attr);
  }

  /**
   * 访问静态变量和成员变量
   */
  @Override
  public FieldVisitor visitField(int access, String name, String desc, String signature,
      Object value) {

    L.print(TAG,
        String.format("visitField：  access = %s, name = %s, desc = %s, signature = %s, value = %s",
            access, name, desc, signature, value));

    return super.visitField(access, name, desc, signature, value);
  }


  /**
   * 访问方法
   */
  @Override
  public MethodVisitor visitMethod(int access, String methodName, String desc, String signature,
      String[] exceptions) {

    L.print(TAG,
        String.format(
            "visitMethod：  className = %s, methodName = %s, desc = %s, signature = %s, ignore = %s",
            className, methodName, desc, signature, ignore));
    //  if (ignore || isInterface || className.contains("android")) {
    if (ignore || isInterface
        ||className.contains("kotlin")
        ||className.contains("android")
        ||className.contains("$")
        ||className.equals("<init>")
        ||className.equals("<clinit>")
        ||className.endsWith("/R") ) {
      return super.visitMethod(access, methodName, desc, signature, exceptions);
    }
    MethodVisitor oriMv = cv.visitMethod(access, methodName, desc, signature, exceptions);
    return new GarbageCodeMethodVisitor(oriMv, access, className, methodName, desc, signature);

  }


  @Override
  public void visitEnd() {
    L.print(TAG, " visitEnd：  ClassName:" + className);
    super.visitEnd();
  }


}
