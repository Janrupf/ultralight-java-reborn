#include "net_janrupf_ujr_platform_jni_impl_JNIUlView_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlLoadListenerNative.h"
#include "net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlLoadListenerNative_native_access.hpp"

#include <Ultralight/Listener.h>

#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlLoadListenerNative_nativeOnBeginLoading(
    JNIEnv *env, jobject self, jobject view, jlong frame_id, jboolean is_main_frame, jstring url
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlLoadListenerNative;
        using ujr::native_access::JNIUlView;

        auto j_url = env.wrap_argument(url);

        auto *listener = reinterpret_cast<ultralight::LoadListener *>(JNIUlLoadListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        listener->OnBeginLoading(
            ul_view,
            static_cast<uint64_t>(frame_id),
            static_cast<bool>(is_main_frame),
            j_url.to_utf16()
        );
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlLoadListenerNative_nativeOnFinishLoading(
    JNIEnv *env, jobject self, jobject view, jlong frame_id, jboolean is_main_frame, jstring url
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlLoadListenerNative;
        using ujr::native_access::JNIUlView;

        auto j_url = env.wrap_argument(url);

        auto *listener = reinterpret_cast<ultralight::LoadListener *>(JNIUlLoadListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        listener->OnFinishLoading(
            ul_view,
            static_cast<uint64_t>(frame_id),
            static_cast<bool>(is_main_frame),
            j_url.to_utf16()
        );
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlLoadListenerNative_nativeOnFailLoading(
    JNIEnv *env,
    jobject self,
    jobject view,
    jlong frame_id,
    jboolean is_main_frame,
    jstring url,
    jstring description,
    jstring error_domain,
    jint error_code
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlLoadListenerNative;
        using ujr::native_access::JNIUlView;

        auto j_url = env.wrap_argument(url);
        auto j_description = env.wrap_argument(description);
        auto j_error_domain = env.wrap_argument(error_domain);

        auto *listener = reinterpret_cast<ultralight::LoadListener *>(JNIUlLoadListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        listener->OnFailLoading(
            ul_view,
            static_cast<uint64_t>(frame_id),
            static_cast<bool>(is_main_frame),
            j_url.to_utf16(),
            j_description.to_utf16(),
            j_error_domain.to_utf16(),
            static_cast<int>(error_code)
        );
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlLoadListenerNative_nativeOnWindowObjectReady(
    JNIEnv *env, jobject self, jobject view, jlong frame_id, jboolean is_main_frame, jstring url
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlLoadListenerNative;
        using ujr::native_access::JNIUlView;

        auto j_url = env.wrap_argument(url);

        auto *listener = reinterpret_cast<ultralight::LoadListener *>(JNIUlLoadListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        listener->OnWindowObjectReady(
            ul_view,
            static_cast<uint64_t>(frame_id),
            static_cast<bool>(is_main_frame),
            j_url.to_utf16()
        );
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlLoadListenerNative_nativeOnDOMReady(
    JNIEnv *env, jobject self, jobject view, jlong frame_id, jboolean is_main_frame, jstring url
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlLoadListenerNative;
        using ujr::native_access::JNIUlView;

        auto j_url = env.wrap_argument(url);

        auto *listener = reinterpret_cast<ultralight::LoadListener *>(JNIUlLoadListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        listener
            ->OnDOMReady(ul_view, static_cast<uint64_t>(frame_id), static_cast<bool>(is_main_frame), j_url.to_utf16());
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlLoadListenerNative_nativeOnUpdateHistory(
    JNIEnv *env, jobject self, jobject view
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlLoadListenerNative;
        using ujr::native_access::JNIUlView;

        auto *listener = reinterpret_cast<ultralight::LoadListener *>(JNIUlLoadListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        listener->OnUpdateHistory(ul_view);
    });
}
