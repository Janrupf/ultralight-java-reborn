#include "net_janrupf_ujr_platform_jni_impl_JNIUlKeyboard.h"

#include <Ultralight/KeyEvent.h>

#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniRef.hpp"

JNIEXPORT jstring JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlKeyboard_nativeKeyIdentifierFromVirtualKeyCode(
    JNIEnv *env, jobject /* self */, jint virtual_key_code
) {
    return ujr::jni_entry_guard(env, [&](auto env) -> jstring {
        ultralight::String key_identifier;
        ultralight::GetKeyIdentifierFromVirtualKeyCode(virtual_key_code, /* ref */ key_identifier);

        if (key_identifier.empty()) {
            return nullptr;
        }

        return ujr::JniLocalRef<jstring>::from_utf16(env, key_identifier.utf16()).leak();
    });
}

JNIEXPORT jstring JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlKeyboard_nativeKeyFromVirtualKeyCode(
    JNIEnv *env, jobject /* self */, jint virtual_key_code, jboolean shift
) {
    return ujr::jni_entry_guard(env, [&](auto env) -> jstring {
        ultralight::String key;
        ultralight::GetKeyFromVirtualKeyCode(virtual_key_code, shift, /* ref */ key);

        if (key.empty()) {
            return nullptr;
        }

        return ujr::JniLocalRef<jstring>::from_utf16(env, key.utf16()).leak();
    });
}
