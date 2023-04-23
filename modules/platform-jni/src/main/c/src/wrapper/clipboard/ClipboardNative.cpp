#include "net_janrupf_ujr_platform_jni_wrapper_clipboard_JNIUlClipboardNative.h"
#include "net_janrupf_ujr_platform_jni_wrapper_clipboard_JNIUlClipboardNative_native_access.hpp"

#include <Ultralight/platform/Clipboard.h>

#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniRef.hpp"

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_clipboard_JNIUlClipboardNative_nativeClear(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlClipboardNative;

        auto *clipboard = reinterpret_cast<ultralight::Clipboard *>(JNIUlClipboardNative::HANDLE.get(env, self));
        clipboard->Clear();
    });
}

JNIEXPORT jstring JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_clipboard_JNIUlClipboardNative_nativeReadPlainText(
    JNIEnv *env, jobject self
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlClipboardNative;

        auto *clipboard = reinterpret_cast<ultralight::Clipboard *>(JNIUlClipboardNative::HANDLE.get(env, self));
        return ujr::JniLocalRef<jstring>::from_utf16(env, clipboard->ReadPlainText().utf16()).leak();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_clipboard_JNIUlClipboardNative_nativeWritePlainText(
    JNIEnv *env, jobject self, jstring text
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlClipboardNative;

        auto j_text = env.wrap_argument(text);

        auto *clipboard = reinterpret_cast<ultralight::Clipboard *>(JNIUlClipboardNative::HANDLE.get(env, self));
        clipboard->WritePlainText(j_text.to_utf16());
    });
}
