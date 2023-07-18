#include "net_janrupf_ujr_api_bitmap_UlBitmapFormat_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlBitmap_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlBitmapFactory.h"

#include <Ultralight/Bitmap.h>

#include <stdexcept>

#include "ujr/Bitmap.hpp"
#include "ujr/util/JniEntryGuard.hpp"

namespace {
    ultralight::BitmapFormat java_format_to_native(const ujr::JniLocalRef<jobject> &j_format) {
        using ujr::native_access::UlBitmapFormat;

        if (j_format == UlBitmapFormat::A8_UNORM.get(j_format.associated_env())) {
            return ultralight::BitmapFormat::A8_UNORM;
        } else if (j_format == UlBitmapFormat::BGRA8_UNORM_SRGB.get(j_format.associated_env())) {
            return ultralight::BitmapFormat::BGRA8_UNORM_SRGB;
        } else {
            throw std::runtime_error("Unknown bitmap format");
        }
    }
} // namespace

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmapFactory_nativeCreate0(JNIEnv *env, jobject) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        auto bitmap = ujr::Bitmap::wrap(env, ultralight::Bitmap::Create());
        return bitmap.leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmapFactory_nativeCreate1(
    JNIEnv *env, jobject, jlong width, jlong height, jobject format
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        auto j_format = env.wrap_argument(format);
        auto ul_bitmap = ultralight::Bitmap::Create(
            static_cast<uint32_t>(width),
            static_cast<uint32_t>(height),
            java_format_to_native(j_format)
        );

        auto bitmap = ujr::Bitmap::wrap(env, ul_bitmap);
        return bitmap.leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmapFactory_nativeCreate2(
    JNIEnv *env, jobject, jlong width, jlong height, jobject format, jlong alignment
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        auto j_format = env.wrap_argument(format);
        auto ul_bitmap = ultralight::Bitmap::Create(
            static_cast<uint32_t>(width),
            static_cast<uint32_t>(height),
            java_format_to_native(j_format),
            static_cast<uint32_t>(alignment)
        );

        auto bitmap = ujr::Bitmap::wrap(env, ul_bitmap);
        return bitmap.leak();
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlBitmapFactory_nativeCreate3(JNIEnv *env, jobject, jobject other) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlBitmap;

        auto j_other = env.wrap_argument(other);
        auto *other_ref = reinterpret_cast<ultralight::Bitmap *>(JNIUlBitmap::HANDLE.get(env, j_other));
        auto ul_bitmap = ultralight::Bitmap::Create(*other_ref);

        auto bitmap = ujr::Bitmap::wrap(env, ul_bitmap);
        return bitmap.leak();
    });
}
