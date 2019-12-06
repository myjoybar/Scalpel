package me.joy.scalpelplugin.viewclick.visitor;

import me.joy.scalpelplugin.logger.data.LogAnnotationInfo;
import me.joy.scalpelplugin.utils.L;
import org.objectweb.asm.AnnotationVisitor;

/**
 * Created by Joy on 2019-08-16
 */
public class ViewClickAnnotationVisitor extends AnnotationVisitor {

  private final static String TAG = "ViewClickAnnotationVisitor";
  private final static String NAME_LEVEL = "level";
  private final static String NAME_TRACESPEND_TIME = "traceSpendTime";

  private LogAnnotationInfo logAnnotationInfo;


  public ViewClickAnnotationVisitor(int api, LogAnnotationInfo logAnnotationInfo) {
    super(api);
    this.logAnnotationInfo = logAnnotationInfo;
  }

  @Override
  public void visit(String name, Object value) {
    L.print(TAG,
        String.format("visit：  name = %s, value= %s, annotationName = %s",name,value.toString(),logAnnotationInfo
            .getAnnotationName()));
    if (NAME_LEVEL.equals(name)) {
      logAnnotationInfo.setLevel((int) value);

    }
    if (NAME_TRACESPEND_TIME.equals(name)) {
      logAnnotationInfo.setTraceSpendTime((boolean) value);
    }

    super.visit(name, value);
  }

  @Override
  public AnnotationVisitor visitArray(String name) {
    L.print(TAG, "visitArray：  name： " + name);
    return super.visitArray(name);
  }


}

