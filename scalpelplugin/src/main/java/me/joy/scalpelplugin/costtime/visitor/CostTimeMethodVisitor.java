package me.joy.scalpelplugin.costtime.visitor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * Created by Joy on 2019-12-05
 */
public class CostTimeMethodVisitor extends MethodVisitor implements Opcodes {

  private int time;
  private int maxStack;

  private String owner;
  private String filedName;
  private String methodName;
  private boolean isPresent;


  public LocalVariablesSorter lvs;
  public AnalyzerAdapter aa;


  public CostTimeMethodVisitor(MethodVisitor methodVisitor) {
    super(ASM6, methodVisitor);
  }

  public CostTimeMethodVisitor(MethodVisitor methodVisitor, String owner, String filedName,
      String methodName, boolean isPresent) {
    this(methodVisitor);
    this.owner = owner;
    this.filedName = filedName;
    this.methodName = methodName;
    this.isPresent = isPresent;
  }

  @Override
  public void visitCode() {
    mv.visitCode();
    //nanoTime
    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
    time = lvs.newLocal(Type.LONG_TYPE);
    mv.visitVarInsn(LSTORE, time);
    maxStack = 4;
  }

  @Override
  public void visitInsn(int opcode) {
    if (((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) && !isPresent) {
      mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
      mv.visitVarInsn(LLOAD, time);
      mv.visitInsn(LSUB);
      mv.visitVarInsn(LSTORE, time);
      //  System.out.println(UDASMCN + "  onCreate:" + var2);

      mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

      mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
      mv.visitInsn(DUP);
      mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
      mv.visitFieldInsn(GETSTATIC, owner, filedName, "Ljava/lang/String;");
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
          "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

      mv.visitLdcInsn("  " + methodName + " cost time: ");
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
          "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

      mv.visitVarInsn(LLOAD, time);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
          "(J)Ljava/lang/StringBuilder;", false);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString",
          "()Ljava/lang/String;", false);
      mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",
          false);

      maxStack = Math.max(aa.stack.size() + 4, maxStack);
    }
    mv.visitInsn(opcode);
  }

  @Override
  public void visitMaxs(int maxStack, int maxLocals) {
    super.visitMaxs(Math.max(maxStack, this.maxStack), maxLocals);
  }
}
