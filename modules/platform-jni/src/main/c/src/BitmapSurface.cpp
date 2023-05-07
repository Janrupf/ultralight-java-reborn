#include "net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlBitmapSurface.h"
#include "net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_native_access.hpp"

#include <Ultralight/Bitmap.h>
#include <Ultralight/platform/Surface.h>

#include "ujr/Bitmap.hpp"
#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmapSurface_nativeBitmap(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceNative;
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap_surface = reinterpret_cast<ultralight::BitmapSurface *>(JNIUlSurfaceNative::HANDLE.get(env, self));
        auto *bitmap = bitmap_surface->bitmap().LeakRef();

        auto j_bitmap = JNIUlBitmap::CLAZZ.alloc_object(env);
        JNIUlBitmap::HANDLE.set(env, j_bitmap, reinterpret_cast<jlong>(bitmap));
        ujr::GCSupport::attach_collector(env, j_bitmap, new ujr::BitmapCollector(bitmap));

        return j_bitmap.leak();
    });
}
