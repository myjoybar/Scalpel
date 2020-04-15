package com.ys.a.b.c;

/**
 * Created by Joy on 2020-04-10
 */
public class G {


  public static G getInG() {
    return GHolder.g;
  }

  private static class GHolder {

    private static G g = new G();
  }

  public int a11(int a) {
    return a;
  }

  public int a12(int a) {
    return a;
  }

  public int a13(int a) {
    return a;
  }

  public int a14(int a) {
    return a;
  }

  public int a21(int a, int b) {
    return a + b;
  }

  public int a22(int a, int b) {
    return a + b;
  }

  public int a23(int a, int b) {
    return a + b;
  }

  public int a24(int a, int b) {
    return a + b;
  }


  public long b11(long a) {
    return a;
  }

  public long b12(long a) {
    return a;
  }

  public long b13(long a) {
    return a;
  }

  public long b14(long a) {
    return a;
  }

  public long b21(long a, long b) {
    return a + b;
  }

  public long b22(long a, long b) {
    return a + b;
  }

  public long b23(long a, long b) {
    return a + b;
  }

  public long b24(long a, long b) {
    return a + b;
  }


  public boolean c11(boolean c) {
    return c;
  }

  public boolean c12(boolean c) {
    return c;
  }

  public boolean c13(boolean c) {
    return c;
  }

  public boolean c14(boolean c) {
    return c;
  }

  public boolean c21(boolean c1, boolean c2) {
    return c1 && c2;
  }

  public boolean c22(boolean c1, boolean c2) {
    return c1 && c2;
  }

  public boolean c23(boolean c1, boolean c2) {
    return c1 && c2;
  }

  public boolean c24(boolean c1, boolean c2) {
    return c1 && c2;
  }


  public String d11(String a) {
    return a;
  }

  public String d12(String a) {
    return a;
  }

  public String d13(String a) {
    return a;
  }

  public String d14(String a) {
    return a;
  }

  public String d21(String a, String b) {
    return a + b;
  }

  public String d22(String a, String b) {
    return a + b;
  }

  public String d23(String a, String b) {
    return a + b;
  }

  public String d24(String a, String b) {
    return a + b;
  }


}
