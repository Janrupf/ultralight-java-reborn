#include "net_janrupf_ujr_api_config_UlViewConfig_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlRenderer.h"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlRenderer_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlSession_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlView_native_access.hpp"

#include <Ultralight/Renderer.h>
#include <Ultralight/View.h>

#include "ujr/Renderer.hpp"
#include "ujr/Session.hpp"
#include "ujr/support/GC.hpp"
#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/View.hpp"

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlRenderer_nativeCreateSession(
    JNIEnv *env, jobject self, jboolean is_persistent, jstring name
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlRenderer;

        auto j_name = env.wrap_argument(name);

        auto *renderer = reinterpret_cast<ultralight::Renderer *>(JNIUlRenderer::HANDLE.get(env, self));
        auto ul_session = renderer->CreateSession(is_persistent, j_name.to_utf16());

        auto session = ujr::Session::wrap(env, ul_session);
        return session.leak();
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlRenderer_nativeDefaultSession(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlRenderer;

        auto *renderer = reinterpret_cast<ultralight::Renderer *>(JNIUlRenderer::HANDLE.get(env, self));
        auto ul_session = renderer->default_session();

        auto session = ujr::Session::wrap(env, ul_session);
        return session.leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlRenderer_nativeCreateView(
    JNIEnv *env, jobject self, jint width, jint height, jobject config, jobject session
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlRenderer;
        using ujr::native_access::UlViewConfig;
        using ujr::native_access::JNIUlView;
        using ujr::native_access::JNIUlSession;

        auto j_config = env.wrap_argument(config);
        auto j_session = env.wrap_argument(session);

        ultralight::ViewConfig ul_config;
        ul_config.is_accelerated = UlViewConfig::IS_ACCELERATED.get(env, j_config);
        ul_config.is_transparent = UlViewConfig::IS_TRANSPARENT.get(env, j_config);
        ul_config.initial_device_scale = UlViewConfig::INITIAL_DEVICE_SCALE.get(env, j_config);
        ul_config.initial_focus = UlViewConfig::INITIAL_FOCUS.get(env, j_config);
        ul_config.enable_images = UlViewConfig::ENABLE_IMAGES.get(env, j_config);
        ul_config.enable_javascript = UlViewConfig::ENABLE_JAVASCRIPT.get(env, j_config);
        ul_config.font_family_standard = UlViewConfig::FONT_FAMILY_STANDARD.get(env, j_config)
                                             .require_non_null_argument("config.fontFamilyStandard")
                                             .to_utf16();
        ul_config.font_family_fixed = UlViewConfig::FONT_FAMILY_FIXED.get(env, j_config)
                                          .require_non_null_argument("config.fontFamilyFixed")
                                          .to_utf16();
        ul_config.font_family_serif = UlViewConfig::FONT_FAMILY_SERIF.get(env, j_config)
                                          .require_non_null_argument("config.fontFamilySerif")
                                          .to_utf16();
        ul_config.font_family_sans_serif = UlViewConfig::FONT_FAMILY_SANS_SERIF.get(env, j_config)
                                               .require_non_null_argument("config.fontFamilySansSerif")
                                               .to_utf16();
        ul_config.user_agent
            = UlViewConfig::USER_AGENT.get(env, j_config).require_non_null_argument("config.userAgent").to_utf16();

        auto *renderer = reinterpret_cast<ultralight::Renderer *>(JNIUlRenderer::HANDLE.get(env, self));

        ultralight::RefPtr<ultralight::Session> ul_session = nullptr;

        if (j_session.is_valid()) {
            auto *session_ref = reinterpret_cast<ultralight::Session *>(JNIUlSession::HANDLE.get(env, j_session));
            ul_session = ultralight::RefPtr(session_ref);
        }

        auto view_ref = renderer->CreateView(width, height, ul_config, std::move(ul_session));
        auto view = view_ref.LeakRef(); // We will take over reference counting ourselves

        auto j_view = JNIUlView::CLAZZ.alloc_object(env);
        JNIUlView::HANDLE.set(env, j_view, reinterpret_cast<jlong>(view));

        auto *collector = new ujr::ViewCollector(view);
        JNIUlView::NATIVE_COLLECTOR.set(env, j_view, reinterpret_cast<jlong>(collector));
        ujr::GCSupport::attach_collector(env, j_view, collector);

        return j_view.leak();
    });
}

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
Java_net_janrupf_ujr_platform_jni_impl_JNIUlRenderer_nativeRenderOnly(JNIEnv *env, jobject self, jobjectArray views) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlRenderer;
        using ujr::native_access::JNIUlView;

        auto *renderer = reinterpret_cast<ultralight::Renderer *>(JNIUlRenderer::HANDLE.get(env, self));

        auto view_count = env->GetArrayLength(views);
        auto **view_handles = new ultralight::View *[view_count];

        for (jsize i = 0; i < view_count; i++) {
            auto j_view = ujr::JniLocalRef<jobject>::wrap(env, env->GetObjectArrayElement(views, i));
            auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, j_view));

            view_handles[i] = view;
        }

        renderer->RenderOnly(view_handles, view_count);
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
        return renderer->StartRemoteInspectorServer(address_str.data(), static_cast<uint16_t>(port));
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
        renderer->SetGamepadDetails(
            static_cast<uint32_t>(index),
            id_str.data(),
            static_cast<uint32_t>(axis_count),
            static_cast<uint32_t>(button_count)
        );
    });
}

namespace ujr {
    JniLocalRef<jobject> Renderer::wrap(const JniEnv &env, ultralight::RefPtr<ultralight::Renderer> renderer) {
        using ujr::native_access::JNIUlRenderer;

        auto *renderer_ref = renderer.LeakRef();

        auto jni_renderer_ref = JNIUlRenderer::CLAZZ.alloc_object(env);
        JNIUlRenderer::HANDLE.set(env, jni_renderer_ref, reinterpret_cast<jlong>(renderer_ref));
        ujr::GCSupport::attach_collector(env, jni_renderer_ref, new ujr::RendererCollector(renderer_ref));

        return jni_renderer_ref;
    }

    RendererCollector::RendererCollector(ultralight::Renderer *renderer)
        : renderer(renderer) {}

    void RendererCollector::collect() { renderer->Release(); }
} // namespace ujr