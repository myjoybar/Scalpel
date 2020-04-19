package me.joy.scalpel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import me.joy.scalpel.helper.logger.data.LogLevel;

/**
 * Created by joybar on 14/04/2018.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogTrace {
    boolean traceSpendTime() default true;
    boolean isTrackParameter() default true;
    @LogLevel.LogLevelType int level() default LogLevel.TYPE_VERBOSE;

}
