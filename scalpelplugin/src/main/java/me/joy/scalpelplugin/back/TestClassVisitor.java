package me.joy.scalpelplugin.back;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Joy on 2020-04-03
 */
public class TestClassVisitor extends ClassVisitor implements Opcodes {

  private String className;
  private boolean isInterface;
  private String methodName;

  public TestClassVisitor(ClassVisitor classVisitor) {
    super(ASM6, classVisitor);
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName,
      String[] interfaces) {
    isInterface = (access & ACC_INTERFACE) != 0;
    this.className = name;
    super.visit(version, access, name, signature, superName, interfaces);
  }

  @Override
  public FieldVisitor visitField(int access, String name, String desc, String signature,
      Object value) {
    return super.visitField(access, name, desc, signature, value);
  }

  @Override
  public MethodVisitor visitMethod(int access, String methodName, String desc, String signature,
      String[] exceptions) {
    this.methodName = methodName;
    return super.visitMethod(access, methodName, desc, signature, exceptions);
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
  }
}
