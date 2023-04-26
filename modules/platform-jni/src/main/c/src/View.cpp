#include "net_janrupf_ujr_platform_jni_exception_JniJavascriptException_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlView.h"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlView_native_access.hpp"

#include <Ultralight/View.h>

#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT jstring JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeUrl(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        return ujr::JniLocalRef<jstring>::from_utf16(env, view->url().utf16()).leak();
    });
}

JNIEXPORT jstring JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeTitle(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        return ujr::JniLocalRef<jstring>::from_utf16(env, view->title().utf16()).leak();
    });
}

JNIEXPORT jlong JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeWidth(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        return view->width();
    });
}

JNIEXPORT jlong JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeHeight(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        return view->height();
    });
}

JNIEXPORT jdouble JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeDeviceScale(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        return view->device_scale();
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeSetDeviceScale(JNIEnv *env, jobject self, jdouble scale) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        view->set_device_scale(scale);
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeIsAccelerated(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        return view->is_accelerated();
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeIsTransparent(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        return view->is_transparent();
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeIsLoading(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        return view->is_loading();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeLoadHTML(
    JNIEnv *env, jobject self, jstring html, jstring url, jboolean add_to_history
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto j_html = env.wrap_argument(html).require_non_null_argument("html");
        auto j_url = env.wrap_argument(url).require_non_null_argument("url");

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        view->LoadHTML(j_html.to_utf16(), j_html.to_utf16(), add_to_history);
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeLoadURL(JNIEnv *env, jobject self, jstring url) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto j_url = env.wrap_argument(url).require_non_null_argument("url");

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        view->LoadURL(j_url.to_utf16());
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeResize(JNIEnv *env, jobject self, jlong width, jlong height) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        view->Resize(width, height);
    });
}

JNIEXPORT jstring JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeEvaluateScript(JNIEnv *env, jobject self, jstring script) {
    return ujr::jni_entry_guard(env, [&](auto env) -> jstring {
        using ujr::native_access::JNIUlView;
        using ujr::native_access::JniJavascriptException;

        auto j_script = env.wrap_argument(script).require_non_null_argument("script");

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        ultralight::String exception;
        ultralight::String result = view->EvaluateScript(j_script.to_utf16(), &exception);

        if (!exception.empty()) {
            auto j_exception = JniJavascriptException::CONSTRUCTOR.invoke(
                env,
                ujr::JniLocalRef<jstring>::from_utf16(env, exception.utf16())
            );

            env->Throw(j_exception);
            return nullptr;
        }

        return ujr::JniLocalRef<jstring>::from_utf16(env, result.utf16()).leak();
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeCanGoBack(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        return view->CanGoBack();
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeCanGoForward(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        return view->CanGoForward();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeGoBack(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        view->GoBack();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeGoForward(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        view->GoForward();
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeGoToHistoryOffset(JNIEnv *env, jobject self, jint offset) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        view->GoToHistoryOffset(offset);
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeReload(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        view->Reload();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeStop(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        view->Stop();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeFocus(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        view->Focus();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeUnfocus(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        view->Unfocus();
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeHasFocus(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        return view->HasFocus();
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeHasInputFocus(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        return view->HasInputFocus();
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeSetNeedsPaint(JNIEnv *env, jobject self, jboolean needs_paint) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));
        view->set_needs_paint(needs_paint);
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeNeedsPaint(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        return view->needs_paint();
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeCreateLocalInspectorView(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        view->CreateLocalInspectorView();
    });
}
