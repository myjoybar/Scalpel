package me.joy.scalpelplugin.logger.visitor;

import java.util.ArrayList;
import java.util.List;
import me.joy.scalpelplugin.Constant;
import me.joy.scalpelplugin.logger.data.LogAnnotationInfo;
import me.joy.scalpelplugin.utils.L;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Joy on 2019-08-16
 */
public class LogMethodVisitor extends MethodVisitor {

  private final static String TAG = "LogMethodVisitor";


  private int access;
  private String methodName;
  private String className;
  private String desc;
  private String signature;
  private int argStartIndex = 0;
  private List<String> argList = new ArrayList<>(0);


  private LogAnnotationInfo logAnnotationInfo;


  public LogMethodVisitor(int i) {
    super(i);
  }

  public LogMethodVisitor(int i, MethodVisitor methodVisitor) {
    super(i, methodVisitor);
  }

  public LogMethodVisitor(MethodVisitor oriMv, int access, String className, String methodName,
      String desc, String signature) {
//      super(Opcodes.ASM5);

    super(Opcodes.ASM5, oriMv);
    this.access = access;
    this.className = className;
    this.methodName = methodName;
    this.desc = desc;
    this.signature = signature;


  }


  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    //check method for annotation
    L.print(
        TAG, "visitMethodAnnotation： " + desc + " visible=" + visible + ",methodName "
            + methodName);
    if (Constant.LOG_TRACE_METHOD_ANNOTATION.equals(desc)) {

      logAnnotationInfo = new LogAnnotationInfo();
      logAnnotationInfo.setAnnotationName(desc);
      logAnnotationInfo.setAnnotationPresent(true);
      logAnnotationInfo.setMethodName(methodName);
      LogAnnotationVisitor logAnnotationVisitor = new LogAnnotationVisitor(Opcodes.ASM5,
          logAnnotationInfo);
      L.print(TAG, "visitMethodAnnotation： after AnnotationInfo setting");
      return logAnnotationVisitor;
    }
    return super.visitAnnotation(desc, visible);
  }


  /**
   * 开始访问方法代码
   */
  @Override
  public void visitCode() {
    super.visitCode();
    L.print(TAG, "visitCode：  methodName " + methodName);
    if (handleMethodAnnotation()) {
      L.print(TAG, "visitCode： logAnnotationInfo " + logAnnotationInfo.toString());

      // 方法执行前插入： MethodCostTimeHelper.addStartTime(false,"aaaaaaa");
      setEnableTraceSpendTime(logAnnotationInfo, mv);
      mv.visitLdcInsn(className + methodName);
      mv.visitMethodInsn(Opcodes.INVOKESTATIC, Constant.METHOD_COST_TIME_HELPER_CLASS_OWNER,
          "addStartTime", "(ZLjava/lang/String;)V", false);
    }

  }

  @Override
  public void visitLocalVariable(String name, String desc, String signature, Label start, Label end,
      int index) {
//    L.print(TAG, "visitLocalVariable： " + String.format("desc = %s, name = %s", desc, name));
    String argName = name;
    if (!"this".equals(argName)) {
      argList.add(argName);
    }

  }


  @Override
  public void visitInsn(int opcode) {
    //方法执行后插入
//    L.print("visitInsn：  methodName = " + methodName + "，opcode = " + opcode);
    if (handleMethodAnnotation()) {
      if (((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW)) {
        L.print("visitInsn：  modify the code, " + methodName + "，opcode = " + opcode);
        // MethodCostTimeHelper.addEndTime(true,"bbbbbbbb");
        setEnableTraceSpendTime(logAnnotationInfo, mv);
        mv.visitLdcInsn(className + methodName);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, Constant.METHOD_COST_TIME_HELPER_CLASS_OWNER,
            "addEndTime", "(ZLjava/lang/String;)V", false);

        // ScalpelManager.getScalpelExecutorDelegate().print("TAGTAG",MethodCostTimeHelper.combineLogStrInfo("CCCCCCC",true,"DDDDDDD"),2222);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "me/joy/scalpel/helper/ScalpelManager",
            "getScalpelDelegateService", "()Lme/joy/scalpel/helper/ScalpelDelegateService;",
            false);
        mv.visitLdcInsn(className);
        mv.visitLdcInsn(methodName);
        setEnableTraceSpendTime(logAnnotationInfo, mv);
        mv.visitLdcInsn(className + methodName);

        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "me/joy/scalpel/helper/MethodCostTimeHelper",
            "combineLogStrInfo", "(Ljava/lang/String;ZLjava/lang/String;)Ljava/lang/String;",
            false);
        mv.visitIntInsn(Opcodes.SIPUSH, logAnnotationInfo.getLevel());
        mv
            .visitMethodInsn(Opcodes.INVOKEINTERFACE,
                "me/joy/scalpel/helper/ScalpelDelegateService", "log",
                "(Ljava/lang/String;Ljava/lang/String;I)V", true);

      }

    }

    super.visitInsn(opcode);

  }

  @Override
  public void visitParameter(String name, int access) {
    L.print("visitParameter： name: " + name + " access=" + access);
    super.visitParameter(name, access);
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
  }


  private boolean handleMethodAnnotation() {
    return (null != logAnnotationInfo && logAnnotationInfo.isAnnotationPresent());
  }


  private void setEnableTraceSpendTime(LogAnnotationInfo logAnnotationInfo, MethodVisitor mv) {
    //set the value of parameter traceSpendTime
    if (logAnnotationInfo.isTraceSpendTime()) {
      mv.visitInsn(Opcodes.ICONST_1);
    } else {
      mv.visitInsn(Opcodes.ICONST_0);
    }
  }


}

