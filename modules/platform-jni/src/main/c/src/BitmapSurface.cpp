#include "net_janrupf_ujr_platform_jni_impl_JNIUlBitmapSurface.h"
#include "net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceNative_native_access.hpp"

#include <Ultralight/platform/Surface.h>

#include "ujr/Bitmap.hpp"
#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmapSurface_nativeBitmap(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlSurfaceNative;

        auto *bitmap_surface = reinterpret_cast<ultralight::BitmapSurface *>(JNIUlSurfaceNative::HANDLE.get(env, self));
        auto bitmap = ujr::Bitmap::wrap(env, bitmap_surface->bitmap());

        return bitmap.leak();
    });
}
