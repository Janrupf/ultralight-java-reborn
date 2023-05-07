package net.janrupf.ujr.platform.jni.ffi;

public interface NativePixelBufferHolder {
    void unlockPixels(byte[] storage);
}
