package net.janrupf.ujr.platform.jni.ffi;

import java.lang.annotation.*;

/**
 * Special annotation to open up another class to native code.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface NativeAccessOther {
    /**
     * The class to open up to native code.
     *
     * @return the class to open up to native code
     */
    Class<?>[] value();
}
