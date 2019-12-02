package me.joy.scalpelplugin.logger.visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.joy.scalpelplugin.Constant;
import me.joy.scalpelplugin.logger.data.LogAnnotationInfo;
import me.joy.scalpelplugin.utils.ClassUtils;
import me.joy.scalpelplugin.utils.LogUtils;
import me.joy.scalpelplugin.utils.MethodArgumentUtils;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

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
  int argStartIndex = 0;
  private int argsCount;
  List<String> argList = new ArrayList<>(0);

//  private final boolean isStatic;
//  private final List<Type> parameterTypes;
//  private final String[] slotNames;


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
    LogUtils.print(
        TAG, " visitMethodAnnotation=" + desc + " visible=" + visible + ",methodName "
            + methodName);
    if (Constant.LOG_TRACE_METHOD_ANNOTATION.equals(desc)) {

      logAnnotationInfo = new LogAnnotationInfo();
      logAnnotationInfo.setAnnotationName(desc);
      logAnnotationInfo.setAnnotationPresent(true);
      logAnnotationInfo.setMethodName(methodName);
      LogAnnotationVisitor logAnnotationVisitor = new LogAnnotationVisitor(Opcodes.ASM5,
          logAnnotationInfo);
      LogUtils.print(TAG, " visitMethodAnnotation after logAnnotationInfo setting");
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
    LogUtils.print(TAG, "visitCode methodName " + methodName);
    if (handleMethodAnnotation()) {
      LogUtils
          .print(TAG, "visitCode logAnnotationInfo " + logAnnotationInfo.toString());

      test2(desc);
      test3(desc);

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
    LogUtils.print(TAG, "AAAAA_visitLocalVariable  desc=  " + desc);
   LogUtils.print(TAG, "AAAAA_visitLocalVariable  name=  " + name);

    String argName = name;
    if (!"this".equals(argName)) {
      argList.add(argName);
    }

  }

  private void test2(String desc) {
    boolean isStatic = (access & Opcodes.ACC_STATIC) != 0;
    Type[] argTypes = Type.getArgumentTypes(desc);
    char[] args = MethodArgumentUtils.parseMethodArguments(desc);
    LogUtils.print(TAG, "AAAAA_args " + Arrays.toString(args));
    LogUtils.print(TAG, "AAAAA_argList " + Arrays.toString(argList.toArray()));



  }
  private void test3(String desc) {
    Type[] argTypes = Type.getArgumentTypes(desc);
    int paramLength=argTypes.length;
    for(Type t: argTypes) {
      System.out.println(" type-->" + t);
    }

    int ind=desc.lastIndexOf(')');
    Type returnType=Type.getType(desc.substring(ind + 1));
    System.out.println("returnType=" + returnType);

    int loadI=1;
    for(Type tp: argTypes) {
      if(tp.equals(Type.LONG_TYPE) || tp.equals(Type.DOUBLE_TYPE)) {
        loadI++;
      }
      loadI++;
    }

    System.out.println(" loadI = " + loadI);
  }


  @Override
  public void visitInsn(int opcode) {
    //方法执行后插入
    if (handleMethodAnnotation()) {
      LogUtils.print("LogMethodVisitor: visitInsn " + methodName + "，opcode = " + opcode);
      if (((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW)) {

        // MethodCostTimeHelper.addEndTime(true,"bbbbbbbb");
        setEnableTraceSpendTime(logAnnotationInfo, mv);
        mv.visitLdcInsn(className + methodName);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, Constant.METHOD_COST_TIME_HELPER_CLASS_OWNER,
            "addEndTime", "(ZLjava/lang/String;)V", false);

        // ScalpelManager.getScalpelExecutorDelegate().log("TAGTAG",MethodCostTimeHelper.combineLogStrInfo("CCCCCCC",true,"DDDDDDD"),2222);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "me/joy/scalpel/helper/ScalpelManager",
            "getScalpelExecutorDelegate", "()Lme/joy/scalpel/helper/ScalpelExecutorDelegate;",
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
                "me/joy/scalpel/helper/ScalpelExecutorDelegate", "log",
                "(Ljava/lang/String;Ljava/lang/String;I)V", true);

//        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "me/joy/scalpel/helper/MethodCostTimeHelper",
//            "combineLogStrInfo", "(Ljava/lang/String;ZLjava/lang/String;)Ljava/lang/String;",
//            false);
//        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log",
//            ScalpelUtils.getLogLevel(logAnnotationInfo.getLevel()),
//            "(Ljava/lang/String;Ljava/lang/String;)I", false);
//        mv.visitInsn(Opcodes.POP);

      }

    }

    super.visitInsn(opcode);

  }

  @Override
  public void visitParameter(String name, int access) {
    LogUtils.print("MethodScanner: visitParameter, name: " + name + " access=" + access);
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




  private void test(String desc) {

    boolean isStatic = (access & Opcodes.ACC_STATIC) != 0;
    Type[] argTypes = Type.getArgumentTypes(desc);
    LogUtils.print(TAG, "test——argTypes.length =  " + argTypes.length);
    LogUtils.print(TAG, "Atest——desc =  " + desc);
    // the parameter is empty,argStartIndex is 1;
    argStartIndex = isStatic ? 0 : 1;
    if (null != argTypes) {
      for (Type type : argTypes) {
        argStartIndex += ClassUtils.lenOfType(type);
      }
    }
    LogUtils.print(TAG, "test——AargStartIndex " + argStartIndex);

    int size = argTypes.length;

    int paramLength = argTypes.length;
    // Create array with length equal to number of parameters
    mv.visitIntInsn(Opcodes.BIPUSH, paramLength);
    mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
    mv.visitVarInsn(Opcodes.ASTORE, paramLength);

    char[] args = MethodArgumentUtils.parseMethodArguments(desc);
    LogUtils.print(TAG, "test——args " + Arrays.toString(args));


  }

}

