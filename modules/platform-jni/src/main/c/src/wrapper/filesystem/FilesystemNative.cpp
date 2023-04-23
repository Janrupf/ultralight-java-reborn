#include "net_janrupf_ujr_platform_jni_wrapper_filesystem_JNIUlFilesystemNative.h"
#include "net_janrupf_ujr_platform_jni_wrapper_filesystem_JNIUlFilesystemNative_native_access.hpp"

#include <Ultralight/platform/FileSystem.h>

#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniRef.hpp"
#include "ujr/wrapper/buffer/BufferNative.hpp"

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_filesystem_JNIUlFilesystemNative_nativeFileExists(
    JNIEnv *env, jobject self, jstring path
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlFilesystemNative;

        auto j_path = env.wrap_argument(path);

        auto *filesystem = reinterpret_cast<ultralight::FileSystem *>(JNIUlFilesystemNative::HANDLE.get(env, self));
        return filesystem->FileExists(j_path.to_utf16());
    });
}

JNIEXPORT jstring JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_filesystem_JNIUlFilesystemNative_nativeGetFileMimeType(
    JNIEnv *env, jobject self, jstring path
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlFilesystemNative;

        auto j_path = env.wrap_argument(path);

        auto *filesystem = reinterpret_cast<ultralight::FileSystem *>(JNIUlFilesystemNative::HANDLE.get(env, self));

        auto mime_type = filesystem->GetFileMimeType(j_path.to_utf16());

        return ujr::JniLocalRef<jstring>::from_utf16(env, mime_type.utf16()).leak();
    });
}

JNIEXPORT jstring JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_filesystem_JNIUlFilesystemNative_nativeGetFileCharset(
    JNIEnv *env, jobject self, jstring path
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlFilesystemNative;

        auto j_path = env.wrap_argument(path);

        auto *filesystem = reinterpret_cast<ultralight::FileSystem *>(JNIUlFilesystemNative::HANDLE.get(env, self));

        auto charset = filesystem->GetFileCharset(j_path.to_utf16());

        return ujr::JniLocalRef<jstring>::from_utf16(env, charset.utf16()).leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_filesystem_JNIUlFilesystemNative_nativeOpenFile(
    JNIEnv *env, jobject self, jstring path
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlFilesystemNative;

        auto j_path = env.wrap_argument(path);

        auto *filesystem = reinterpret_cast<ultralight::FileSystem *>(JNIUlFilesystemNative::HANDLE.get(env, self));

        auto file = filesystem->OpenFile(j_path.to_utf16());

        return ujr::BufferNative::as_java_buffer(env, file).leak();
    });
}
