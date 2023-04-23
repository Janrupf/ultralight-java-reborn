#include "net_janrupf_ujr_platform_jni_impl_JNIUlRenderer.h"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlRenderer_native_access.hpp"

#include <Ultralight/Renderer.h>

#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/util/JniRef.hpp"

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlRenderer_nativeUpdate(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlRenderer;

        auto *renderer = reinterpret_cast<ultralight::Renderer *>(JNIUlRenderer::HANDLE.get(env, self));
        renderer->Update();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlRenderer_nativeRender(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlRenderer;

        auto *renderer = reinterpret_cast<ultralight::Renderer *>(JNIUlRenderer::HANDLE.get(env, self));
        renderer->Render();
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlRenderer_nativePurgeMemory(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlRenderer;

        auto *renderer = reinterpret_cast<ultralight::Renderer *>(JNIUlRenderer::HANDLE.get(env, self));
        renderer->PurgeMemory();
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlRenderer_nativeLogMemoryUsage(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlRenderer;

        auto *renderer = reinterpret_cast<ultralight::Renderer *>(JNIUlRenderer::HANDLE.get(env, self));
        renderer->LogMemoryUsage();
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlRenderer_nativeStartRemoteInspectorServer(
    JNIEnv *env, jobject self, jstring address, jint port
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlRenderer;

        auto j_address = env.wrap_argument(address);
        auto address_str = j_address.to_utf8();

        auto *renderer = reinterpret_cast<ultralight::Renderer *>(JNIUlRenderer::HANDLE.get(env, self));
        return renderer->StartRemoteInspectorServer(address_str.data(), port);
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlRenderer_nativeSetGamepadDetails(
    JNIEnv *env, jobject self, jlong index, jstring id, jlong axis_count, jlong button_count
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlRenderer;

        auto j_id = env.wrap_argument(id);
        auto id_str = j_id.to_utf8();

        auto *renderer = reinterpret_cast<ultralight::Renderer *>(JNIUlRenderer::HANDLE.get(env, self));
        renderer->SetGamepadDetails(index, id_str.data(), axis_count, button_count);
    });
}
