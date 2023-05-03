#include "net_janrupf_ujr_api_event_UlKeyEvent_native_access.hpp"
#include "net_janrupf_ujr_api_event_UlKeyEventModifiers_native_access.hpp"
#include "net_janrupf_ujr_api_event_UlKeyEventType_native_access.hpp"
#include "net_janrupf_ujr_api_event_UlMouseButton_native_access.hpp"
#include "net_janrupf_ujr_api_event_UlMouseEvent_native_access.hpp"
#include "net_janrupf_ujr_api_event_UlMouseEventType_native_access.hpp"
#include "net_janrupf_ujr_api_event_UlScrollEvent_native_access.hpp"
#include "net_janrupf_ujr_api_event_UlScrollEventType_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_exception_JniJavascriptException_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlView.h"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlView_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlLoadListenerNative_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlViewListenerNative_native_access.hpp"

#include <Ultralight/View.h>

#include "ujr/util/JniClass.hpp"
#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/util/JniMethod.hpp"
#include "ujr/util/JniRef.hpp"
#include "ujr/View.hpp"
#include "ujr/wrapper/listener/LoadListener.hpp"
#include "ujr/wrapper/listener/ViewListener.hpp"

namespace ujr {
    namespace {
        JniClass<"java/util/Set"> SET_CLASS;
        JniInstanceMethod<decltype(SET_CLASS), "toArray", jobjectArray> SET_TO_ARRAY(SET_CLASS);
    } // namespace
} // namespace ujr

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

        view->LoadHTML(j_html.to_utf16(), j_url.to_utf16(), add_to_history);
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
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeFireKeyEvent(JNIEnv *env, jobject self, jobject event) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;
        using ujr::native_access::UlKeyEvent;
        using ujr::native_access::UlKeyEventType;
        using ujr::native_access::UlKeyEventModifiers;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        auto j_event = env.wrap_argument(event);

        ultralight::KeyEvent ul_event;

        auto j_type = UlKeyEvent::TYPE.get(env, j_event).require_non_null_argument("event.type");
        if (j_type == UlKeyEventType::DOWN.get(env)) {
            ul_event.type = ultralight::KeyEvent::kType_KeyDown;
        } else if (j_type == UlKeyEventType::UP.get(env)) {
            ul_event.type = ultralight::KeyEvent::kType_KeyUp;
        } else if (j_event == UlKeyEventType::RAW_DOWN.get(env)) {
            ul_event.type = ultralight::KeyEvent::kType_RawKeyDown;
        } else if (j_event == UlKeyEventType::CHAR.get(env)) {
            ul_event.type = ultralight::KeyEvent::kType_Char;
        } else {
            throw std::runtime_error("Invalid key event type");
        }

        ul_event.modifiers = 0;
        if (auto j_modifiers = UlKeyEvent::MODIFIERS.get(env, j_event); j_modifiers.is_valid()) {
            auto j_modifiers_array = ujr::SET_TO_ARRAY.invoke(env, j_modifiers);
            auto modifiers_size = env->GetArrayLength(j_modifiers_array);

            for (jint i = 0; i < modifiers_size; i++) {
                auto j_modifier = env->GetObjectArrayElement(j_modifiers_array, i);
                if (j_modifier == UlKeyEventModifiers::ALT.get(env)) {
                    ul_event.modifiers |= ultralight::KeyEvent::kMod_AltKey;
                } else if (j_modifier == UlKeyEventModifiers::CTRL.get(env)) {
                    ul_event.modifiers |= ultralight::KeyEvent::kMod_CtrlKey;
                } else if (j_modifier == UlKeyEventModifiers::META.get(env)) {
                    ul_event.modifiers |= ultralight::KeyEvent::kMod_MetaKey;
                } else if (j_modifier == UlKeyEventModifiers::SHIFT.get(env)) {
                    ul_event.modifiers |= ultralight::KeyEvent::kMod_ShiftKey;
                } else {
                    throw std::runtime_error("Invalid key event modifier");
                }
            }
        }

        ul_event.virtual_key_code = UlKeyEvent::VIRTUAL_KEY_CODE.get(env, j_event);
        ul_event.native_key_code = UlKeyEvent::NATIVE_KEY_CODE.get(env, j_event);

        if (auto j_key_identifier = UlKeyEvent::KEY_IDENTIFIER.get(env, j_event); j_key_identifier.is_valid()) {
            ul_event.key_identifier = j_key_identifier.to_utf16();
        }

        if (auto j_text = UlKeyEvent::TEXT.get(env, j_event); j_text.is_valid()) {
            ul_event.text = j_text.to_utf16();
        }

        if (auto j_unmodified_text = UlKeyEvent::UNMODIFIED_TEXT.get(env, j_event); j_unmodified_text.is_valid()) {
            ul_event.unmodified_text = j_unmodified_text.to_utf16();
        }

        ul_event.is_keypad = UlKeyEvent::IS_KEYPAD.get(env, j_event);
        ul_event.is_auto_repeat = UlKeyEvent::IS_AUTO_REPEAT.get(env, j_event);
        ul_event.is_system_key = UlKeyEvent::IS_SYSTEM_KEY.get(env, j_event);

        view->FireKeyEvent(ul_event);
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeFireMouseEvent(JNIEnv *env, jobject self, jobject event) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;
        using ujr::native_access::UlMouseEvent;
        using ujr::native_access::UlMouseEventType;
        using ujr::native_access::UlMouseButton;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        auto j_event = env.wrap_argument(event);

        ultralight::MouseEvent ul_event {};

        auto j_type = UlMouseEvent::TYPE.get(env, j_event).require_non_null_argument("event.type");
        if (j_type == UlMouseEventType::MOVED.get(env)) {
            ul_event.type = ultralight::MouseEvent::kType_MouseMoved;
        } else if (j_type == UlMouseEventType::DOWN.get(env)) {
            ul_event.type = ultralight::MouseEvent::kType_MouseDown;
        } else if (j_type == UlMouseEventType::UP.get(env)) {
            ul_event.type = ultralight::MouseEvent::kType_MouseUp;
        } else {
            throw std::runtime_error("Invalid mouse event type");
        }

        ul_event.x = UlMouseEvent::X.get(env, j_event);
        ul_event.y = UlMouseEvent::Y.get(env, j_event);

        if (auto j_button = UlMouseEvent::BUTTON.get(env, j_event); j_button.is_valid()) {
            if (j_button == UlMouseButton::LEFT.get(env)) {
                ul_event.button = ultralight::MouseEvent::kButton_Left;
            } else if (j_button == UlMouseButton::MIDDLE.get(env)) {
                ul_event.button = ultralight::MouseEvent::kButton_Middle;
            } else if (j_button == UlMouseButton::RIGHT.get(env)) {
                ul_event.button = ultralight::MouseEvent::kButton_Right;
            } else {
                throw std::runtime_error("Invalid mouse event button");
            }

        } else {
            ul_event.button = ultralight::MouseEvent::kButton_None;
        }

        view->FireMouseEvent(ul_event);
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeFireScrollEvent(JNIEnv *env, jobject self, jobject event) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;
        using ujr::native_access::UlScrollEvent;
        using ujr::native_access::UlScrollEventType;

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        auto j_event = env.wrap_argument(event);

        ultralight::ScrollEvent ul_event {};

        auto j_type = UlScrollEvent::TYPE.get(env, j_event).require_non_null_argument("event.type");
        if (j_type == UlScrollEventType::BY_PIXEL.get(env)) {
            ul_event.type = ultralight::ScrollEvent::kType_ScrollByPixel;
        } else if (j_type == UlScrollEventType::BY_PAGE.get(env)) {
            ul_event.type = ultralight::ScrollEvent::kType_ScrollByPage;
        } else {
            throw std::runtime_error("Invalid scroll event type");
        }

        ul_event.delta_x = UlScrollEvent::DELTA_X.get(env, j_event);
        ul_event.delta_y = UlScrollEvent::DELTA_Y.get(env, j_event);

        view->FireScrollEvent(ul_event);
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeSetViewListener(JNIEnv *env, jobject self, jobject listener) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *collector = reinterpret_cast<ujr::ViewCollector *>(JNIUlView::NATIVE_COLLECTOR.get(env, self));

        // Clear the field in case an exception is thrown before we set it again
        delete collector->view_listener;
        collector->view_listener = nullptr;

        auto j_listener = env.wrap_argument(listener);
        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        ultralight::ViewListener *new_native_listener = nullptr;

        if (j_listener.is_valid()) {
            new_native_listener = new ujr::ViewListener(std::move(j_listener.clone_as_global()));
        }

        // Set the native listener field
        view->set_view_listener(new_native_listener);
        collector->view_listener = new_native_listener;
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeViewListener(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) -> jobject {
        using ujr::native_access::JNIUlView;
        using ujr::native_access::JNIUlViewListenerNative;

        auto *collector = reinterpret_cast<ujr::ViewCollector *>(JNIUlView::NATIVE_COLLECTOR.get(env, self));
        if (collector->view_listener == nullptr) {
            // No listener set, return null
            return nullptr;
        }

        // Test if the listener is a JNI listener
        auto *jni_listener = dynamic_cast<ujr::ViewListener *>(collector->view_listener);
        if (jni_listener) {
            // It is, we can return the Java listener
            return jni_listener->get_j_listener().get();
        }

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        // We have to construct a Java listener wrapper
        auto jni_listener_ref = JNIUlViewListenerNative::CLAZZ.alloc_object(env);
        JNIUlViewListenerNative::HANDLE.set(env, jni_listener_ref, reinterpret_cast<jlong>(collector->view_listener));
        JNIUlViewListenerNative::VIEW.set(env, jni_listener_ref, reinterpret_cast<jlong>(view));

        return jni_listener_ref.leak();
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeSetLoadListener(JNIEnv *env, jobject self, jobject listener) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlView;

        auto *collector = reinterpret_cast<ujr::ViewCollector *>(JNIUlView::NATIVE_COLLECTOR.get(env, self));

        // Clear the field in case an exception is thrown before we set it again
        delete collector->load_listener;
        collector->load_listener = nullptr;

        auto j_listener = env.wrap_argument(listener);
        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        ultralight::LoadListener *new_native_listener = nullptr;

        if (j_listener.is_valid()) {
            new_native_listener = new ujr::LoadListener(std::move(j_listener.clone_as_global()));
        }

        // Set the native listener field
        view->set_load_listener(new_native_listener);
        collector->load_listener = new_native_listener;
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlView_nativeLoadListener(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) -> jobject {
        using ujr::native_access::JNIUlView;
        using ujr::native_access::JNIUlLoadListenerNative;

        auto *collector = reinterpret_cast<ujr::ViewCollector *>(JNIUlView::NATIVE_COLLECTOR.get(env, self));
        if (collector->load_listener == nullptr) {
            // No listener set, return null
            return nullptr;
        }

        // Test if the listener is a JNI listener
        auto *jni_listener = dynamic_cast<ujr::LoadListener *>(collector->load_listener);
        if (jni_listener) {
            // It is, we can return the Java listener
            return jni_listener->get_j_listener().get();
        }

        auto *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, self));

        // We have to construct a Java listener wrapper
        auto jni_listener_ref = JNIUlLoadListenerNative::CLAZZ.alloc_object(env);
        JNIUlLoadListenerNative::HANDLE.set(env, jni_listener_ref, reinterpret_cast<jlong>(collector->load_listener));
        JNIUlLoadListenerNative::VIEW.set(env, jni_listener_ref, reinterpret_cast<jlong>(view));

        return jni_listener_ref.leak();
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

namespace ujr {
    ViewCollector::ViewCollector(ultralight::View *view)
        : view(view)
        , view_listener(nullptr)
        , load_listener(nullptr) {}

    void ViewCollector::collect() {
        delete view_listener;
        delete load_listener;
        view->Release();
    }
} // namespace ujr
