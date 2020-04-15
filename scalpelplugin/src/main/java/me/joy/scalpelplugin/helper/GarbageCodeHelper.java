package me.joy.scalpelplugin.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import me.joy.scalpelplugin.extention.ConfigHelper;
import me.joy.scalpelplugin.utils.L;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Joy on 2020-04-10
 */
public class GarbageCodeHelper implements Opcodes {

  private static Map<Integer, String> CLASS_SIMPLE_NAME_TYPE_MAP = new HashMap();
  private static Map<Integer, String> CLASS_FULL_NAME_TYPE_MAP = new HashMap();
  private static Map<Integer, String> METHOD_TYPE_MAP = new HashMap();
  private static Map<Integer, String> PKG_TYPE_MAP = new HashMap();
  private static String INSTANCE_NAME = "getIn";

  static {
    CLASS_SIMPLE_NAME_TYPE_MAP.put(1, "A");
    CLASS_SIMPLE_NAME_TYPE_MAP.put(2, "B");
    CLASS_SIMPLE_NAME_TYPE_MAP.put(3, "C");
    CLASS_SIMPLE_NAME_TYPE_MAP.put(4, "D");
    CLASS_SIMPLE_NAME_TYPE_MAP.put(5, "E");
    CLASS_SIMPLE_NAME_TYPE_MAP.put(6, "F");
    CLASS_SIMPLE_NAME_TYPE_MAP.put(7, "G");
    CLASS_SIMPLE_NAME_TYPE_MAP.put(8, "H");
    CLASS_SIMPLE_NAME_TYPE_MAP.put(9, "I");
    CLASS_SIMPLE_NAME_TYPE_MAP.put(10, "J");

  }

  static {
    PKG_TYPE_MAP.put(1, "com/ys/a/b/c/");
    PKG_TYPE_MAP.put(2, "com/ys/b/c/d/");
    PKG_TYPE_MAP.put(3, "com/ys/c/d/e/");
    PKG_TYPE_MAP.put(4, "com/ys/d/e/f/");
    PKG_TYPE_MAP.put(5, "com/ys/e/f/g/");
    PKG_TYPE_MAP.put(6, "com/ys/f/g/h/");
    PKG_TYPE_MAP.put(7, "com/ys/g/h/i/");
    PKG_TYPE_MAP.put(8, "com/ys/h/i/j/");
    PKG_TYPE_MAP.put(9, "com/ys/i/j/k/");
    PKG_TYPE_MAP.put(10, "com/ys/j/k/l/");
  }

  static {
    int count = 1;
    int count1 = PKG_TYPE_MAP.size();
    int count2 = CLASS_SIMPLE_NAME_TYPE_MAP.size();
    for (int i = 1; i <= count1; i++) {
      for (int j = 1; j <= count2; j++) {
        CLASS_FULL_NAME_TYPE_MAP
            .put(count, PKG_TYPE_MAP.get(i) + CLASS_SIMPLE_NAME_TYPE_MAP.get(j));
        count++;
      }
    }

//    CLASS_FULL_NAME_TYPE_MAP.put(1, "me/joy/scalpel/helper/garbage/A");
//    CLASS_FULL_NAME_TYPE_MAP.put(2, "me/joy/scalpel/helper/garbage/B");
//    CLASS_FULL_NAME_TYPE_MAP.put(3, "me/joy/scalpel/helper/garbage/C");
//    CLASS_FULL_NAME_TYPE_MAP.put(4, "me/joy/scalpel/helper/garbage/D");
//    CLASS_FULL_NAME_TYPE_MAP.put(5, "me/joy/scalpel/helper/garbage/E");
//    CLASS_FULL_NAME_TYPE_MAP.put(6, "me/joy/scalpel/helper/garbage/F");
//    CLASS_FULL_NAME_TYPE_MAP.put(7, "me/joy/scalpel/helper/garbage/G");
//    CLASS_FULL_NAME_TYPE_MAP.put(8, "me/joy/scalpel/helper/garbage/H");
//    CLASS_FULL_NAME_TYPE_MAP.put(9, "me/joy/scalpel/helper/garbage/I");
//    CLASS_FULL_NAME_TYPE_MAP.put(10, "me/joy/scalpel/helper/garbage/J");
  }

  static {
    METHOD_TYPE_MAP.put(1, "a11");
    METHOD_TYPE_MAP.put(2, "a12");
    METHOD_TYPE_MAP.put(3, "a13");
    METHOD_TYPE_MAP.put(4, "a14");

    METHOD_TYPE_MAP.put(5, "a21");
    METHOD_TYPE_MAP.put(6, "a22");
    METHOD_TYPE_MAP.put(7, "a23");
    METHOD_TYPE_MAP.put(8, "a24");

    METHOD_TYPE_MAP.put(9, "b11");
    METHOD_TYPE_MAP.put(10, "b12");
    METHOD_TYPE_MAP.put(11, "b13");
    METHOD_TYPE_MAP.put(12, "b14");

    METHOD_TYPE_MAP.put(13, "b21");
    METHOD_TYPE_MAP.put(14, "b22");
    METHOD_TYPE_MAP.put(15, "b23");
    METHOD_TYPE_MAP.put(16, "b24");

    METHOD_TYPE_MAP.put(17, "c11");
    METHOD_TYPE_MAP.put(18, "c12");
    METHOD_TYPE_MAP.put(19, "c13");
    METHOD_TYPE_MAP.put(20, "c14");

    METHOD_TYPE_MAP.put(21, "c21");
    METHOD_TYPE_MAP.put(22, "c22");
    METHOD_TYPE_MAP.put(23, "c23");
    METHOD_TYPE_MAP.put(24, "c24");

    METHOD_TYPE_MAP.put(25, "d11");
    METHOD_TYPE_MAP.put(26, "d12");
    METHOD_TYPE_MAP.put(27, "d13");
    METHOD_TYPE_MAP.put(28, "d14");

    METHOD_TYPE_MAP.put(29, "d21");
    METHOD_TYPE_MAP.put(30, "d22");
    METHOD_TYPE_MAP.put(31, "d23");
    METHOD_TYPE_MAP.put(32, "d24");


  }


  private static String getRandomClassType() {
    String garbageType = ConfigHelper.getInstance().getGarbageConfigExtension().garbageType();
    if (garbageType.equalsIgnoreCase("A")) {
      return CLASS_FULL_NAME_TYPE_MAP.get(getRandomNumber(1, 10));
    } else if (garbageType.equalsIgnoreCase("B")) {
      return CLASS_FULL_NAME_TYPE_MAP.get(getRandomNumber(11, 20));
    } else if (garbageType.equalsIgnoreCase("C")) {
      return CLASS_FULL_NAME_TYPE_MAP.get(getRandomNumber(21, 30));
    } else if (garbageType.equalsIgnoreCase("D")) {
      return CLASS_FULL_NAME_TYPE_MAP.get(getRandomNumber(31, 40));
    } else if (garbageType.equalsIgnoreCase("E")) {
      return CLASS_FULL_NAME_TYPE_MAP.get(getRandomNumber(41, 50));
    } else if (garbageType.equalsIgnoreCase("F")) {
      return CLASS_FULL_NAME_TYPE_MAP.get(getRandomNumber(51, 60));
    } else if (garbageType.equalsIgnoreCase("G")) {
      return CLASS_FULL_NAME_TYPE_MAP.get(getRandomNumber(61, 70));
    } else if (garbageType.equalsIgnoreCase("H")) {
      return CLASS_FULL_NAME_TYPE_MAP.get(getRandomNumber(71, 80));
    } else if (garbageType.equalsIgnoreCase("I")) {
      return CLASS_FULL_NAME_TYPE_MAP.get(getRandomNumber(81, 90));
    } else {
      return CLASS_FULL_NAME_TYPE_MAP.get(getRandomNumber(91, 100));
    }

  }

  private static String getRandomMethodType() {
    return METHOD_TYPE_MAP.get(getRandomNumber(METHOD_TYPE_MAP.size()));
  }

  public static void injectGarbageCode1(MethodVisitor methodVisitor) {
    L.print("injectGarbageCode", "");
    methodVisitor
        .visitMethodInsn(INVOKESTATIC, "com/ys/a/b/c/A", "getInA", "()Lcom/ys/a/b/c/A;", false);
    methodVisitor.visitIntInsn(SIPUSH, 11111);
    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "com/ys/a/b/c/A", "a11", "(I)I", false);
    methodVisitor.visitInsn(POP);

  }

  public static void injectGarbageCode(MethodVisitor methodVisitor) {

    String classType = getRandomClassType();
    String methodType = getRandomMethodType();
    String classSimpleName = classType.substring(classType.length() - 1);
    L.print("injectGarbageCode：  methodType = " + methodType + "，classType = " + classType
        + "，classSimpleName = " + classSimpleName);

    if (methodType.contains("a1")) {

      methodVisitor.visitMethodInsn(INVOKESTATIC, classType, INSTANCE_NAME + classSimpleName,
          "()L" + classType + ";", false);
      methodVisitor.visitIntInsn(SIPUSH, getRandomNumber());
      methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classType, methodType, "(I)I", false);
      methodVisitor.visitInsn(POP);

//      methodVisitor
//          .visitMethodInsn(INVOKESTATIC, "com/ys/a/b/c/A", "getInA", "()Lcom/ys/a/b/c/A;", false);
//      methodVisitor.visitIntInsn(SIPUSH, 11111);
//      methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "com/ys/a/b/c/A", "a11", "(I)I", false);
//      methodVisitor.visitInsn(POP);

    } else if (methodType.contains("a2")) {

      methodVisitor.visitMethodInsn(INVOKESTATIC, classType, INSTANCE_NAME + classSimpleName,
          "()L" + classType + ";", false);
      methodVisitor.visitIntInsn(SIPUSH, getRandomNumber());
      methodVisitor.visitIntInsn(SIPUSH, getRandomNumber());
      methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classType, methodType, "(II)I", false);
      methodVisitor.visitInsn(POP);

    } else if (methodType.contains("b1")) {
      methodVisitor.visitMethodInsn(INVOKESTATIC, classType, INSTANCE_NAME + classSimpleName,
          "()L" + classType + ";", false);
      methodVisitor.visitLdcInsn(new Long(getRandomNumber()));
      methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classType, methodType, "(J)J", false);
      methodVisitor.visitInsn(POP2);

    } else if (methodType.contains("b2")) {

      methodVisitor.visitMethodInsn(INVOKESTATIC, classType, INSTANCE_NAME + classSimpleName,
          "()L" + classType + ";", false);
      methodVisitor.visitLdcInsn(new Long(getRandomNumber()));
      methodVisitor.visitLdcInsn(new Long(getRandomNumber()));
      methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classType, methodType, "(JJ)J", false);
      methodVisitor.visitInsn(POP2);


    } else if (methodType.contains("c1")) {

      methodVisitor.visitMethodInsn(INVOKESTATIC, classType, INSTANCE_NAME + classSimpleName,
          "()L" + classType + ";", false);
      methodVisitor.visitInsn(getRandomBool());
      methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classType, methodType, "(Z)Z", false);
      methodVisitor.visitInsn(POP);


    } else if (methodType.contains("c2")) {
      methodVisitor.visitMethodInsn(INVOKESTATIC, classType, INSTANCE_NAME + classSimpleName,
          "()L" + classType + ";", false);
      methodVisitor.visitInsn(getRandomBool());
      methodVisitor.visitInsn(getRandomBool());
      methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classType, methodType, "(ZZ)Z", false);
      methodVisitor.visitInsn(POP);

    } else if (methodType.contains("d1")) {

      methodVisitor.visitMethodInsn(INVOKESTATIC, classType, INSTANCE_NAME + classSimpleName,
          "()L" + classType + ";", false);
      methodVisitor.visitLdcInsn(getRandomStr());
      methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classType, methodType,
          "(Ljava/lang/String;)Ljava/lang/String;", false);
      methodVisitor.visitInsn(POP);

    } else if (methodType.contains("d2")) {
      methodVisitor.visitMethodInsn(INVOKESTATIC, classType, INSTANCE_NAME + classSimpleName,
          "()L" + classType + ";", false);
      methodVisitor.visitLdcInsn(getRandomStr());
      methodVisitor.visitLdcInsn(getRandomStr());
      methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classType, methodType,
          "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", false);
      methodVisitor.visitInsn(POP);
    }
  }

  private static int getRandomBool() {
    int i = getRandomNumber(2);
    if (i == 1) {
      return ICONST_1; //true
    } else {
      return ICONST_0; //false
    }
  }


  private static int getRandomNumber() {
    return getRandomNumber(100000);
  }


  private static int getRandomNumber(int max) {
    //[1,max]
    return (int) (Math.random() * max + 1);
  }

  private static int getRandomNumber(int min, int max) {
    //[m,n]
    return min + (int) (Math.random() * (max + 1 - min)); //生成从m到n的随机整数[m,n]
  }


  private static String getRandomStr() {
    return getRandomStr(6);
  }

  private static String getRandomStr(int length) {
    String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++) {
      int number = random.nextInt(62);
      sb.append(str.charAt(number));
    }
    return sb.toString();
  }

}
