package me.joy.scalpelplugin.viewclick.visitor;

import me.joy.scalpelplugin.utils.L;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Joy on 2019-08-16
 */
public class ViewClickMethodVisitor extends MethodVisitor implements Opcodes {

  private final static String TAG = "ViewClickMethodVisitor";


  private String methodName;
  private String className;


  public ViewClickMethodVisitor(int i) {
    super(i);
  }

  public ViewClickMethodVisitor(int i, MethodVisitor methodVisitor) {
    super(i, methodVisitor);
  }

  public ViewClickMethodVisitor(MethodVisitor oriMv, int access, String className,
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
    mv.visitMethodInsn(INVOKESTATIC, "me/joy/scalpel/helper/ScalpelManager",
        "getScalpelDelegateService", "()Lme/joy/scalpel/helper/ScalpelDelegateService;", false);
    mv.visitVarInsn(ALOAD, 1);
    mv
        .visitMethodInsn(INVOKEINTERFACE, "me/joy/scalpel/helper/ScalpelDelegateService",
            "enterViewClick", "(Landroid/view/View;)V", true);



//    mv.visitMethodInsn(INVOKESTATIC, "me/joy/scalpel/helper/ScalpelManager",
//        "getScalpelDelegateService", "()Lme/joy/scalpel/helper/ScalpelDelegateService;", false);
//    mv.visitVarInsn(ALOAD, 1);
//    mv.visitMethodInsn(INVOKEINTERFACE, "me/joy/scalpel/helper/ScalpelDelegateService",
//        "isFastClick", "(Landroid/view/View;)Z", true);
//    Label label1 = new Label();
//    mv.visitJumpInsn(IFEQ, label1);
//    mv.visitMethodInsn(INVOKESTATIC, "me/joy/scalpel/helper/ScalpelManager",
//        "getScalpelDelegateService", "()Lme/joy/scalpel/helper/ScalpelDelegateService;", false);
//    mv.visitVarInsn(ALOAD, 1);
//    mv.visitMethodInsn(INVOKEINTERFACE, "me/joy/scalpel/helper/ScalpelDelegateService",
//        "enterViewClick", "(Landroid/view/View;)V", true);

  }


  @Override
  public void visitInsn(int opcode) {
    //方法执行后插入
    L.print("visitInsn：  methodName = " + methodName + "，opcode = " + opcode);

    super.visitInsn(opcode);

  }


  @Override
  public void visitEnd() {
    super.visitEnd();
  }


}

