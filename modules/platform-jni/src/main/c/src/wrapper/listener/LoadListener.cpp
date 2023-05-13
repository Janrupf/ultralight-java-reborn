#include "ujr/wrapper/listener/LoadListener.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlLoadListener_native_access.hpp"

#include "ujr/View.hpp"

namespace ujr {
    LoadListener::LoadListener(JniGlobalRef<jobject> j_listener)
        : j_listener(std::move(j_listener)) {}

    void LoadListener::OnBeginLoading(
        ultralight::View *caller, uint64_t frame_id, bool is_main_frame, const ultralight::String &url
    ) {
        auto env = JniEnv::require_existing_from_thread();

        auto j_view = ujr::View::wrap(env, ultralight::RefPtr(caller));

        // Translate the url
        auto j_url = JniLocalRef<jstring>::from_utf16(env, url.utf16());

        // Call the java instance
        native_access::JNIUlLoadListener::ON_BEGIN_LOADING
            .invoke(env, j_listener, j_view, static_cast<jlong>(frame_id), static_cast<jboolean>(is_main_frame), j_url);
    }

    void LoadListener::OnFinishLoading(
        ultralight::View *caller, uint64_t frame_id, bool is_main_frame, const ultralight::String &url
    ) {
        auto env = JniEnv::require_existing_from_thread();

        auto j_view = ujr::View::wrap(env, ultralight::RefPtr(caller));

        // Translate the url
        auto j_url = JniLocalRef<jstring>::from_utf16(env, url.utf16());

        // Call the java instance
        native_access::JNIUlLoadListener::ON_FINISH_LOADING
            .invoke(env, j_listener, j_view, static_cast<jlong>(frame_id), static_cast<jboolean>(is_main_frame), j_url);
    }

    void LoadListener::OnFailLoading(
        ultralight::View *caller,
        uint64_t frame_id,
        bool is_main_frame,
        const ultralight::String &url,
        const ultralight::String &description,
        const ultralight::String &error_domain,
        int error_code
    ) {
        auto env = JniEnv::require_existing_from_thread();

        auto j_view = ujr::View::wrap(env, ultralight::RefPtr(caller));

        // Translate the url, description and error domain
        auto j_url = JniLocalRef<jstring>::from_utf16(env, url.utf16());
        auto j_description = JniLocalRef<jstring>::from_utf16(env, description.utf16());
        auto j_error_domain = JniLocalRef<jstring>::from_utf16(env, error_domain.utf16());

        // Call the java instance
        native_access::JNIUlLoadListener::ON_FAIL_LOADING.invoke(
            env,
            j_listener,
            j_view,
            static_cast<jlong>(frame_id),
            static_cast<jboolean>(is_main_frame),
            j_url,
            j_description,
            j_error_domain,
            static_cast<jint>(error_code)
        );
    }

    void LoadListener::OnWindowObjectReady(
        ultralight::View *caller, uint64_t frame_id, bool is_main_frame, const ultralight::String &url
    ) {
        auto env = JniEnv::require_existing_from_thread();

        auto j_view = ujr::View::wrap(env, ultralight::RefPtr(caller));

        // Translate the url
        auto j_url = JniLocalRef<jstring>::from_utf16(env, url.utf16());

        // Call the java instance
        native_access::JNIUlLoadListener::ON_WINDOW_OBJECT_READY
            .invoke(env, j_listener, j_view, static_cast<jlong>(frame_id), static_cast<jboolean>(is_main_frame), j_url);
    }

    void LoadListener::OnDOMReady(
        ultralight::View *caller, uint64_t frame_id, bool is_main_frame, const ultralight::String &url
    ) {
        auto env = JniEnv::require_existing_from_thread();

        auto j_view = ujr::View::wrap(env, ultralight::RefPtr(caller));

        // Translate the url
        auto j_url = JniLocalRef<jstring>::from_utf16(env, url.utf16());

        // Call the java instance
        native_access::JNIUlLoadListener::ON_DOM_READY
            .invoke(env, j_listener, j_view, static_cast<jlong>(frame_id), static_cast<jboolean>(is_main_frame), j_url);
    }

    void LoadListener::OnUpdateHistory(ultralight::View *caller) {
        auto env = JniEnv::require_existing_from_thread();

        auto j_view = ujr::View::wrap(env, ultralight::RefPtr(caller));

        // Call the java instance
        native_access::JNIUlLoadListener::ON_UPDATE_HISTORY.invoke(env, j_listener, j_view);
    }

    const JniGlobalRef<jobject> &LoadListener::get_j_listener() const { return j_listener; }
} // namespace ujr