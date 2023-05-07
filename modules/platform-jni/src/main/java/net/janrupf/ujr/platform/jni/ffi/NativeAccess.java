package net.janrupf.ujr.platform.jni.ffi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which indicates that the annotated element can be accessed by native code.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD})
public @interface NativeAccess {
}
