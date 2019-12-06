package me.joy.scalpelplugin.costtime;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * Created by Joy on 2019-12-05
 */
public class CostTimeClassVisitor extends ClassVisitor implements Opcodes {
  private String owner;
  private boolean isInterface;
  private String filedName="UDASMCN";
  private int acc=Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC+ Opcodes.ACC_FINAL;
  private boolean isPresent=false;

  private String methodName;

  public CostTimeClassVisitor(ClassVisitor classVisitor) {
    super(ASM6, classVisitor);
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    super.visit(version, access, name, signature, superName, interfaces);
    owner = name;
    isInterface = (access & ACC_INTERFACE) != 0;
  }
  @Override
  public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
    if (name.equals(filedName)){
      isPresent=true;
    }
    return super.visitField(access, name, descriptor, signature, value);
  }
  @Override
  public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
    MethodVisitor mv=cv.visitMethod(access, name, descriptor, signature, exceptions);

    if (!isInterface && mv != null && !name.equals("<init>") && !name.equals("<clinit>")) {
      methodName=name;
//      CostTimeMethodVisitor at = new CostTimeMethodVisitor(mv);
      CostTimeMethodVisitor at = new CostTimeMethodVisitor(mv, owner,filedName,
          methodName, isPresent);
      at.aa = new AnalyzerAdapter(owner, access, name, descriptor, at);
      at.lvs = new LocalVariablesSorter(access, descriptor, at.aa);

      return at.lvs;
    }

    return mv;
  }

  public void visitEnd() {
    if (!isInterface) {
      FieldVisitor fv = cv.visitField(acc, filedName,
          "Ljava/lang/String;", null, owner);
      if (fv != null) {
        fv.visitEnd();
      }
    }
    cv.visitEnd();
  }

}
