package me.joy.scalpelplugin.garbagecode.visitor;

import me.joy.scalpelplugin.extention.ConfigHelper;
import me.joy.scalpelplugin.helper.GarbageCodeHelper;
import me.joy.scalpelplugin.utils.L;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Joy on 2019-08-16
 */
public class GarbageCodeMethodVisitor extends MethodVisitor implements Opcodes {

  private final static String TAG = "GarbageCodeMethodVisitor";

  private String methodName;
  private String className;

  public GarbageCodeMethodVisitor(int i) {
    super(i);
  }

  public GarbageCodeMethodVisitor(int i, MethodVisitor methodVisitor) {
    super(i, methodVisitor);
  }

  public GarbageCodeMethodVisitor(MethodVisitor oriMv, int access, String className,
      String methodName,
      String desc, String signature) {
//      super( ASM5);
    super(ASM5, oriMv);
    this.className = className;
    this.methodName = methodName;

  }

  //多次调用
  @Override
  public void visitParameter(String name, int access) {
    super.visitParameter(name, access);
  }

  //调用一次
  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    //check method for annotation
    return super.visitAnnotation(desc, visible);
  }

  /**
   * 开始访问方法代码
   */
  @Override
  public void visitCode() {
    super.visitCode();
    L.print(TAG, "visitCode：  methodName " + methodName);
    // 方法执行前插入：

    double garbageCodeInjectRatio = ConfigHelper.getInstance().getGarbageConfigExtension().garbageCodeInjectRatio();
    double random = Math.random();
    L.print(TAG, "transform：  The garbageCodeInjectRatio is：" + garbageCodeInjectRatio);
    boolean enableModification = garbageCodeInjectRatio > random;
    L.print(TAG, "transform：  The enableModification is：" + enableModification);
    if (enableModification) {
      GarbageCodeHelper.injectGarbageCode(mv);
    }

  }


  @Override
  public void visitInsn(int opcode) {
    //方法执行后插入
    L.print("visitInsn：  methodName = " + methodName + "，opcode = " + opcode);
    double garbageCodeInjectRatio = ConfigHelper.getInstance().getGarbageConfigExtension().garbageCodeInjectRatio();
    double random = Math.random();
    boolean enableModification = garbageCodeInjectRatio > random;
    if (enableModification) {
      GarbageCodeHelper.injectGarbageCode(mv);
    }
    super.visitInsn(opcode);

  }


  @Override
  public void visitEnd() {
    super.visitEnd();
  }




}

