#include "ujr/Bitmap.hpp"
#include "net_janrupf_ujr_api_bitmap_UlBitmapFormat_native_access.hpp"
#include "net_janrupf_ujr_api_math_IntRect_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlBitmap.h"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_native_access.hpp"

#include <Ultralight/String.h>

#include <stdexcept>

#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT jlong JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeWidth(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        return bitmap->width();
    });
}

JNIEXPORT jlong JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeHeight(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        return bitmap->height();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeBounds(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;
        using ujr::native_access::IntRect;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        auto bounds = bitmap->bounds();

        auto j_bounds = IntRect::CLAZZ.alloc_object(env);
        IntRect::LEFT.set(env, j_bounds, bounds.left);
        IntRect::TOP.set(env, j_bounds, bounds.top);
        IntRect::RIGHT.set(env, j_bounds, bounds.right);
        IntRect::BOTTOM.set(env, j_bounds, bounds.bottom);

        return j_bounds.leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeFormat(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) -> jobject {
        using ujr::native_access::JNIUlBitmap;
        using ujr::native_access::UlBitmapFormat;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        auto format = bitmap->format();

        switch (format) {
            case ultralight::BitmapFormat::A8_UNORM:
                return UlBitmapFormat::A8_UNORM.get(env).leak();
            case ultralight::BitmapFormat::BGRA8_UNORM_SRGB:
                return UlBitmapFormat::BGRA8_UNORM_SRGB.get(env).leak();
            default:
                throw std::runtime_error("Unknown bitmap format");
        }
    });
}

JNIEXPORT jlong JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeBpp(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        return bitmap->bpp();
    });
}

JNIEXPORT jlong JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeRowBytes(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        return bitmap->row_bytes();
    });
}

JNIEXPORT jlong JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeSize(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        return bitmap->size();
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeOwnsPixels(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        return bitmap->owns_pixels();
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeLockPixels(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *ul_surface = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        auto *pixels = ul_surface->LockPixels();

        auto direct_buffer = env->NewDirectByteBuffer(pixels, ul_surface->size());
        if (direct_buffer || env->ExceptionCheck()) {
            return direct_buffer;
        }

        // Direct buffers not supported, use a byte array
        auto indirect_array
            = ujr::JniLocalRef<jbyteArray>::wrap(env, env->NewByteArray(static_cast<jsize>(ul_surface->size())));
        if (!indirect_array.is_valid() || env->ExceptionCheck()) {
            return static_cast<jobject>(indirect_array.leak());
        }

        // Copy the data from the pixel buffer to the array and store the original pointer
        env->SetByteArrayRegion(
            indirect_array.get(),
            0,
            static_cast<jsize>(ul_surface->size()),
            reinterpret_cast<jbyte *>(pixels)
        );
        JNIUlBitmap::LOCKED_PIXELS.set(env, self, reinterpret_cast<jlong>(pixels));

        return static_cast<jobject>(indirect_array.leak());
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeIsEmpty(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        return bitmap->IsEmpty();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeErase(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        bitmap->Erase();
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeSet(JNIEnv *env, jobject self, jobject other) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        auto *other_bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, other));

        ultralight::RefPtr<ultralight::Bitmap> other_bitmap_ref(other_bitmap);
        bitmap->Set(other_bitmap_ref);
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeDrawBitmap(
    JNIEnv *env, jobject self, jobject src_rect, jobject dest_rect, jobject bitmap, jboolean pad_repeat
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;
        using ujr::native_access::IntRect;

        auto j_src_rect = env.wrap_argument(src_rect);
        auto j_dest_rect = env.wrap_argument(dest_rect);
        auto *self_bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        auto *other_bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, bitmap));

        ultralight::IntRect ul_src_rect { IntRect::LEFT.get(env, j_src_rect),
                                          IntRect::TOP.get(env, j_src_rect),
                                          IntRect::RIGHT.get(env, j_src_rect),
                                          IntRect::BOTTOM.get(env, j_src_rect) };

        ultralight::IntRect ul_dest_rect { IntRect::LEFT.get(env, j_dest_rect),
                                           IntRect::TOP.get(env, j_dest_rect),
                                           IntRect::RIGHT.get(env, j_dest_rect),
                                           IntRect::BOTTOM.get(env, j_dest_rect) };

        return self_bitmap->DrawBitmap(ul_src_rect, ul_dest_rect, other_bitmap, pad_repeat);
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeWritePNG(
    JNIEnv *env, jobject self, jstring path, jboolean convert_to_rgba, jboolean convert_to_straight_alpha
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        auto j_path = env.wrap_argument(path);

        auto path8 = j_path.to_utf8();
        return bitmap->WritePNG(path8.c_str(), convert_to_rgba, convert_to_straight_alpha);
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeResample(
    JNIEnv *env, jobject self, jobject destination, jboolean high_quality
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        auto *other_bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, destination));

        ultralight::RefPtr<ultralight::Bitmap> other_bitmap_ref(other_bitmap);
        return bitmap->Resample(other_bitmap_ref, high_quality);
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeSwapRedBlueChannels(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        bitmap->SwapRedBlueChannels();
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeConvertToStraightAlpha(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        bitmap->ConvertToStraightAlpha();
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_nativeConvertToPremultipliedAlpha(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, self));
        bitmap->ConvertToPremultipliedAlpha();
    });
}

namespace ujr {
    JniLocalRef<jobject> Bitmap::wrap(const JniEnv &env, ultralight::RefPtr<ultralight::Bitmap> bitmap) {
        using ujr::native_access::JNIUlBitmap;

        auto *bitmap_ref = bitmap.LeakRef();

        auto j_bitmap = JNIUlBitmap::CLAZZ.alloc_object(env);
        JNIUlBitmap::HANDLE.set(env, j_bitmap, reinterpret_cast<jlong>(bitmap_ref));
        ujr::GCSupport::attach_collector(env, j_bitmap, new ujr::BitmapCollector(bitmap_ref));

        return j_bitmap;
    }

    BitmapCollector::BitmapCollector(ultralight::Bitmap *bitmap)
        : bitmap(bitmap) {}

    void BitmapCollector::collect() { bitmap->Release(); } // namespace ujr
} // namespace ujr
