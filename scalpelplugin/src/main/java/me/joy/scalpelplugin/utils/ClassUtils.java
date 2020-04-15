package me.joy.scalpelplugin.utils;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Created by Joy on 2019-11-25
 */
public class ClassUtils {

  public static int lenOfType(Type type) {
    if (type.equals(Type.DOUBLE_TYPE)
        || type.equals(Type.LONG_TYPE)) {
      return 2;
    }
    return 1;
  }

  public static void autoBox(MethodVisitor mv, Type requireType) {
    String className = getBoxOrUnboxClassName(requireType);
    if (null != className) {
      // auto box
      mv.visitMethodInsn(Opcodes.INVOKESTATIC, className, "valueOf", getBoxMethodDesc(requireType),
          false);
    }
  }

  public static String getBoxMethodDesc(Type type) {
    String desc = null;
    switch (type.getSort()) {
      case Type.BOOLEAN:
        desc = "(Z)Ljava/lang/Boolean;";
        break;
      case Type.BYTE:
        desc = "(B)Ljava/lang/Byte;";
        break;
      case Type.CHAR:
        desc = "(C)Ljava/lang/Character;";
        break;
      case Type.SHORT:
        desc = "(S)Ljava/lang/Short;";
        break;
      case Type.INT:
        desc = "(I)Ljava/lang/Integer;";
        break;
      case Type.FLOAT:
        desc = "(F)Ljava/lang/Float;";
        break;
      case Type.DOUBLE:
        desc = "(D)Ljava/lang/Double;";
        break;
      case Type.LONG:
        desc = "(J)Ljava/lang/Long;";
        break;
      case Type.OBJECT:
      case Type.ARRAY:
      case Type.VOID:
      case Type.METHOD:
        break;
      default:
        System.out.println("unknown type : " + type.getClassName());

        break;
    }
    return desc;
  }


  public static String getBoxOrUnboxClassName(Type type) {
    String className = null;
    switch (type.getSort()) {
      case Type.BOOLEAN:
        className = "java/lang/Boolean";
        break;
      case Type.BYTE:
        className = "java/lang/Byte";
        break;
      case Type.CHAR:
        className = "java/lang/Character";
        break;
      case Type.SHORT:
        className = "java/lang/Short";
        break;
      case Type.INT:
        className = "java/lang/Integer";
        break;
      case Type.FLOAT:
        className = "java/lang/Float";
        break;
      case Type.DOUBLE:
        className = "java/lang/Double";
        break;
      case Type.LONG:
        className = "java/lang/Long";
        break;
      case Type.OBJECT:
      case Type.ARRAY:
      case Type.VOID:
      case Type.METHOD:
        break;
      default:
        System.out.println("unknown type : " + type.getClassName());
        break;
    }
    return className;
  }



}
