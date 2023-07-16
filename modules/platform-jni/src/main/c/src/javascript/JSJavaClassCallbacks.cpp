#include "ujr/javascript/JSJavaClassCallbacks.hpp"
#include "net_janrupf_ujr_api_javascript_JSType_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_callbacks_JNIJSCJSObjectCallAsConstructorCallback_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_callbacks_JNIJSCJSObjectCallAsFunctionCallback_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_callbacks_JNIJSCJSObjectConvertToTypeCallback_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_callbacks_JNIJSCJSObjectDeletePropertyCallback_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_callbacks_JNIJSCJSObjectFinalizeCallback_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_callbacks_JNIJSCJSObjectGetPropertyCallback_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_callbacks_JNIJSCJSObjectGetPropertyNamesCallback_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_callbacks_JNIJSCJSObjectHasInstanceCallback_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_callbacks_JNIJSCJSObjectHasPropertyCallback_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_callbacks_JNIJSCJSObjectInitializeCallback_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_callbacks_JNIJSCJSObjectSetPropertyCallback_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_native_access.hpp"

#include <JavaScriptCore/JSStringRef.h>

#include <utility>

#include "ujr/javascript/JniJavaScriptValueException.hpp"
#include "ujr/javascript/JSContext.hpp"
#include "ujr/javascript/JSJavaPrivateData.hpp"
#include "ujr/javascript/JSObject.hpp"
#include "ujr/javascript/JSString.hpp"
#include "ujr/javascript/JSValue.hpp"
#include "ujr/util/JniEnv.hpp"

template<typename Cb>
std::invoke_result_t<Cb, ujr::JniEnv> js_entry_guard(JSContextRef ctx, JSValueRef *exception, Cb &&cb)
    requires(std::is_invocable_v<Cb, ujr::JniEnv> && !std::is_same_v<std::invoke_result_t<Cb, ujr::JniEnv>, void>)
{
    if (exception) {
        *exception = nullptr;
    } else {
        try {
            return std::forward<std::invoke_result_t<Cb, ujr::JniEnv>>(cb(ujr::JniEnv::from_thread()));
        } catch (...) {}

        return {};
    }

    try {
        return std::forward<std::invoke_result_t<Cb, ujr::JniEnv>>(cb(ujr::JniEnv::from_thread()));
    } catch (const ujr::JniJavaScriptValueException &e) {
        // We already have a JavaScript exception, so we can just use that one
        JSValueRef existing_exception = e.get_value();
        JSValueProtect(ctx, existing_exception);

        *exception = existing_exception;

        return {};
    } catch (const std::exception &e) {
        // Translate the C++ exception to a JavaScript exception
        const auto *message = e.what();

        JSStringRef js_message_str = JSStringCreateWithUTF8CString(message);
        JSValueRef js_message = JSValueMakeString(ctx, js_message_str);
        JSStringRelease(js_message_str);

        JSValueRef value = JSObjectMakeError(ctx, 1, &js_message, exception);
        if (value) {
            *exception = value;
            JSValueUnprotect(ctx, js_message);
        } else if (!*exception) {
            // If we could not create an error object, we just throw the message
            *exception = js_message;
        } else {
            // Something went seriously wrong
            std::abort();
        }

        return {};
    }
}

template<typename Cb>
void js_entry_guard(JSContextRef ctx, JSValueRef *exception, Cb &&cb)
    requires(std::is_invocable_v<Cb, ujr::JniEnv> && std::is_same_v<std::invoke_result_t<Cb, ujr::JniEnv>, void>)
{
    if (exception) {
        *exception = nullptr;
    } else {
        try {
            cb(ujr::JniEnv::from_thread());
        } catch (...) {}

        return;
    }

    try {
        cb(ujr::JniEnv::from_thread());
    } catch (const ujr::JniJavaScriptValueException &e) {
        // We already have a JavaScript exception, so we can just use that one
        JSValueRef existing_exception = e.get_value();
        JSValueProtect(ctx, existing_exception);

        *exception = existing_exception;
    } catch (const std::exception &e) {
        // Translate the C++ exception to a JavaScript exception
        const auto *message = e.what();

        JSStringRef js_message_str = JSStringCreateWithUTF8CString(message);
        JSValueRef js_message = JSValueMakeString(ctx, js_message_str);
        JSStringRelease(js_message_str);

        JSValueRef value = JSObjectMakeError(ctx, 1, &js_message, exception);
        if (value) {
            *exception = value;
            JSValueUnprotect(ctx, js_message);
        } else if (!*exception) {
            // If we could not create an error object, we just throw the message
            *exception = js_message;
        } else {
            // Something went seriously wrong
            std::abort();
        }
    }
}

namespace ujr {
    JSValueRef JSJavaClassCallbacks::get_static_property(
        JSContextRef ctx, JSClassRef js_class, JSObjectRef object, JSStringRef property_name, JSValueRef *exception
    ) {
        return js_entry_guard(ctx, exception, [&](auto env) {
            using native_access::JNIJSCJSObjectGetPropertyCallback;
            using native_access::JNIJSCJSValue;

            auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));
            auto cpp_property_name = JSString::to_cpp(property_name);

            if (auto it = shared_data->static_property_callbacks.find(cpp_property_name);
                it != shared_data->static_property_callbacks.end() && it->second.get_property.is_valid(env)) {
                JSValueProtect(ctx, object);

                auto j_context = JSContext::wrap(env, ctx);
                auto j_object = JSObject::wrap(env, ctx, object);
                auto j_property_name = JniLocalRef<jstring>::from_utf8(env, cpp_property_name.c_str());

                auto j_result = JNIJSCJSObjectGetPropertyCallback::GET_PROPERTY
                                    .invoke(env, it->second.get_property, j_context, j_object, j_property_name);

                return reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, j_result));
            }

            return static_cast<JSValueRef>(nullptr);
        });
    }

    bool JSJavaClassCallbacks::set_static_property(
        JSContextRef ctx,
        JSClassRef js_class,
        JSObjectRef object,
        JSStringRef property_name,
        JSValueRef value,
        JSValueRef *exception
    ) {
        return js_entry_guard(ctx, exception, [&](auto env) {
            using native_access::JNIJSCJSObjectSetPropertyCallback;
            using native_access::JNIJSCJSValue;

            auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));
            auto cpp_property_name = JSString::to_cpp(property_name);

            if (auto it = shared_data->static_property_callbacks.find(cpp_property_name);
                it != shared_data->static_property_callbacks.end() && it->second.set_property.is_valid(env)) {
                JSValueProtect(ctx, object);
                JSValueProtect(ctx, value);

                auto j_context = JSContext::wrap(env, ctx);
                auto j_object = JSObject::wrap(env, ctx, object);
                auto j_property_name = JniLocalRef<jstring>::from_utf8(env, cpp_property_name.c_str());
                auto j_value = JSValue::wrap(env, ctx, value);

                auto j_result
                    = JNIJSCJSObjectSetPropertyCallback::SET_PROPERTY
                          .invoke(env, it->second.set_property, j_context, j_object, j_property_name, j_value);

                return static_cast<bool>(j_result);
            }

            return false;
        });
    }

    void JSJavaClassCallbacks::initialize(JSContextRef ctx, JSClassRef js_class, JSObjectRef object) {
        js_entry_guard(ctx, nullptr, [&](auto env) {
            using native_access::JNIJSCJSObjectInitializeCallback;

            auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));

            if (shared_data->initialize_callback.is_valid(env)) {
                JSValueProtect(ctx, object);

                auto j_context = JSContext::wrap(env, ctx);
                auto j_object = JSObject::wrap(env, ctx, object);

                JNIJSCJSObjectInitializeCallback::INITIALIZE
                    .invoke(env, shared_data->initialize_callback, j_context, j_object);
            }
        });
    }

    void JSJavaClassCallbacks::finalize(JSClassRef js_class, JSObjectRef object) {
        using native_access::JNIJSCJSObjectFinalizeCallback;

        auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));
        auto env = JniEnv::from_thread();

        if (shared_data->finalize_callback.is_valid(env)) {
            auto j_object = JSObject::wrap(env, nullptr, object);
            JNIJSCJSObjectFinalizeCallback::DO_FINALIZE.invoke(env, shared_data->finalize_callback, j_object);
        }
    }

    bool JSJavaClassCallbacks::has_property(
        JSContextRef ctx, JSClassRef js_class, JSObjectRef object, JSStringRef property_name
    ) {
        return js_entry_guard(ctx, nullptr, [&](auto env) {
            using native_access::JNIJSCJSObjectHasPropertyCallback;

            auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));

            if (shared_data->has_property_callback.is_valid(env)) {
                JSValueProtect(ctx, object);

                auto j_context = JSContext::wrap(env, ctx);
                auto j_object = JSObject::wrap(env, ctx, object);
                auto j_property_name = JSString::to_java(env, property_name);

                auto j_result
                    = JNIJSCJSObjectHasPropertyCallback::HAS_PROPERTY
                          .invoke(env, shared_data->has_property_callback, j_context, j_object, j_property_name);

                return static_cast<bool>(j_result);
            }

            return false;
        });
    }

    JSValueRef JSJavaClassCallbacks::get_property(
        JSContextRef ctx, JSClassRef js_class, JSObjectRef object, JSStringRef property_name, JSValueRef *exception
    ) {
        return js_entry_guard(ctx, exception, [&](auto env) {
            using native_access::JNIJSCJSObjectGetPropertyCallback;
            using native_access::JNIJSCJSValue;

            auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));

            if (shared_data->get_property_callback.is_valid(env)) {
                JSValueProtect(ctx, object);

                auto j_context = JSContext::wrap(env, ctx);
                auto j_object = JSObject::wrap(env, ctx, object);
                auto j_property_name = JSString::to_java(env, property_name);

                auto j_result
                    = JNIJSCJSObjectGetPropertyCallback::GET_PROPERTY
                          .invoke(env, shared_data->get_property_callback, j_context, j_object, j_property_name);

                return reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, j_result));
            }

            return static_cast<JSValueRef>(nullptr);
        });
    }

    bool JSJavaClassCallbacks::set_property(
        JSContextRef ctx,
        JSClassRef js_class,
        JSObjectRef object,
        JSStringRef property_name,
        JSValueRef value,
        JSValueRef *exception
    ) {
        return js_entry_guard(ctx, exception, [&](auto env) {
            using native_access::JNIJSCJSObjectSetPropertyCallback;
            using native_access::JNIJSCJSValue;

            auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));

            if (shared_data->set_property_callback.is_valid(env)) {
                JSValueProtect(ctx, object);
                JSValueProtect(ctx, value);

                auto j_context = JSContext::wrap(env, ctx);
                auto j_object = JSObject::wrap(env, ctx, object);
                auto j_property_name = JSString::to_java(env, property_name);
                auto j_value = JSValue::wrap(env, ctx, value);

                auto j_result = JNIJSCJSObjectSetPropertyCallback::SET_PROPERTY.invoke(
                    env,
                    shared_data->set_property_callback,
                    j_context,
                    j_object,
                    j_property_name,
                    j_value
                );

                return static_cast<bool>(j_result);
            }

            return false;
        });
    }

    bool JSJavaClassCallbacks::delete_property(
        JSContextRef ctx, JSClassRef js_class, JSObjectRef object, JSStringRef property_name, JSValueRef *exception
    ) {
        return js_entry_guard(ctx, exception, [&](auto env) {
            using native_access::JNIJSCJSObjectDeletePropertyCallback;

            auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));

            if (shared_data->delete_property_callback.is_valid(env)) {
                JSValueProtect(ctx, object);

                auto j_context = JSContext::wrap(env, ctx);
                auto j_object = JSObject::wrap(env, ctx, object);
                auto j_property_name = JSString::to_java(env, property_name);

                auto j_result
                    = JNIJSCJSObjectDeletePropertyCallback::DELETE_PROPERTY
                          .invoke(env, shared_data->delete_property_callback, j_context, j_object, j_property_name);

                return static_cast<bool>(j_result);
            }

            return false;
        });
    }

    void JSJavaClassCallbacks::get_property_names(
        JSContextRef ctx, JSClassRef js_class, JSObjectRef object, JSPropertyNameAccumulatorRef property_names
    ) {
        js_entry_guard(ctx, nullptr, [&](auto env) {
            using native_access::JNIJSCJSObjectGetPropertyNamesCallback;

            auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));

            if (shared_data->get_property_names_callback.is_valid(env)) {
                JSValueProtect(ctx, object);

                auto j_context = JSContext::wrap(env, ctx);
                auto j_object = JSObject::wrap(env, ctx, object);

                auto result = JNIJSCJSObjectGetPropertyNamesCallback::GET_PROPERTY_NAMES
                                  .invoke(env, shared_data->get_property_names_callback, j_context, j_object);

                jsize result_length = env->GetArrayLength(result);
                for (jsize i = 0; i < result_length; i++) {
                    auto j_property_name
                        = env.wrap_argument(static_cast<jstring>(env->GetObjectArrayElement(result, i)));

                    JSStringRef property_name = JSString::from_java(env, j_property_name);
                    JSPropertyNameAccumulatorAddName(property_names, property_name);
                    JSStringRelease(property_name);
                }
            }
        });
    }

    JSValueRef JSJavaClassCallbacks::call_as_function(
        JSContextRef ctx,
        JSClassRef js_class,
        JSStringRef class_name,
        JSObjectRef function,
        JSObjectRef this_object,
        size_t argument_count,
        const JSValueRef *arguments,
        JSValueRef *exception
    ) {
        return js_entry_guard(ctx, exception, [&](auto env) {
            using native_access::JNIJSCJSObjectCallAsFunctionCallback;
            using native_access::JNIJSCJSValue;

            auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));

            if (shared_data->call_as_function_callback.is_valid(env)) {
                env->PushLocalFrame(argument_count + 16);

                JSValueRef result;
                try {
                    JSValueProtect(ctx, function);
                    JSValueProtect(ctx, this_object);

                    auto j_context = JSContext::wrap(env, ctx);
                    auto j_class_name = JSString::to_java(env, class_name);
                    auto j_function = JSObject::wrap(env, ctx, function);
                    auto j_this_object = JSObject::wrap(env, ctx, this_object);

                    auto j_arguments
                        = env.wrap_argument(env->NewObjectArray(argument_count, JNIJSCJSValue::CLAZZ.get(env), nullptr)
                        );
                    for (size_t i = 0; i < argument_count; i++) {
                        JSValueProtect(ctx, arguments[i]);
                        auto j_argument = JSValue::wrap(env, ctx, arguments[i]);
                        env->SetObjectArrayElement(j_arguments, i, j_argument);
                    }

                    auto j_result = JNIJSCJSObjectCallAsFunctionCallback::CALL_AS_FUNCTION.invoke(
                        env,
                        shared_data->call_as_function_callback,
                        j_context,
                        j_class_name,
                        j_function,
                        j_this_object,
                        j_arguments
                    );

                    result = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, j_result));
                } catch (...) {
                    env->PopLocalFrame(nullptr);
                    throw;
                }

                env->PopLocalFrame(nullptr);
                return result;
            }

            return static_cast<JSValueRef>(nullptr);
        });
    }

    JSObjectRef JSJavaClassCallbacks::call_as_constructor(
        JSContextRef ctx,
        JSClassRef js_class,
        JSObjectRef constructor,
        size_t argument_count,
        const JSValueRef *arguments,
        JSValueRef *exception
    ) {
        return js_entry_guard(ctx, exception, [&](auto env) {
            using native_access::JNIJSCJSObjectCallAsConstructorCallback;
            using native_access::JNIJSCJSValue;

            auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));

            if (shared_data->call_as_constructor_callback.is_valid(env)) {
                env->PushLocalFrame(argument_count + 16);

                JSObjectRef result;
                try {
                    JSValueProtect(ctx, constructor);

                    auto j_context = JSContext::wrap(env, ctx);
                    auto j_constructor = JSObject::wrap(env, ctx, constructor);

                    auto j_arguments
                        = env.wrap_argument(env->NewObjectArray(argument_count, JNIJSCJSValue::CLAZZ.get(env), nullptr)
                        );
                    for (size_t i = 0; i < argument_count; i++) {
                        JSValueProtect(ctx, arguments[i]);
                        auto j_argument = JSValue::wrap(env, ctx, arguments[i]);
                        env->SetObjectArrayElement(j_arguments, i, j_argument);
                    }

                    auto j_result = JNIJSCJSObjectCallAsConstructorCallback::CALL_AS_CONSTRUCTOR.invoke(
                        env,
                        shared_data->call_as_constructor_callback,
                        j_context,
                        j_constructor,
                        j_arguments
                    );

                    result = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, j_result));
                } catch (...) {
                    env->PopLocalFrame(nullptr);
                    throw;
                }

                env->PopLocalFrame(nullptr);
                return result;
            }

            return static_cast<JSObjectRef>(nullptr);
        });
    }

    bool JSJavaClassCallbacks::has_instance(
        JSContextRef ctx,
        JSClassRef js_class,
        JSObjectRef constructor,
        JSValueRef possible_instance,
        JSValueRef *exception
    ) {
        return js_entry_guard(ctx, exception, [&](auto env) {
            using native_access::JNIJSCJSObjectHasInstanceCallback;
            using native_access::JNIJSCJSValue;

            auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));

            if (shared_data->has_instance_callback.is_valid(env)) {
                JSValueProtect(ctx, constructor);
                JSValueProtect(ctx, possible_instance);

                auto j_context = JSContext::wrap(env, ctx);
                auto j_constructor = JSObject::wrap(env, ctx, constructor);
                auto j_possible_instance = JSValue::wrap(env, ctx, possible_instance);

                auto result = JNIJSCJSObjectHasInstanceCallback::HAS_INSTANCE.invoke(
                    env,
                    shared_data->has_instance_callback,
                    j_context,
                    j_constructor,
                    j_possible_instance
                );

                return static_cast<bool>(result);
            }

            return false;
        });
    }

    JSValueRef JSJavaClassCallbacks::convert_to_type(
        JSContextRef ctx, JSClassRef js_class, JSObjectRef object, JSType type, JSValueRef *exception
    ) {
        return js_entry_guard(ctx, exception, [&](auto env) {
            using native_access::JNIJSCJSObjectConvertToTypeCallback;
            using native_access::JNIJSCJSValue;
            using native_access::JSType;

            auto *shared_data = reinterpret_cast<const JSClassJavaSharedData *>(JSClassGetPrivate(js_class));

            if (shared_data->convert_to_type_callback.is_valid(env)) {
                JSValueProtect(ctx, object);

                auto j_context = JSContext::wrap(env, ctx);
                auto j_object = JSObject::wrap(env, ctx, object);

                auto j_type = JniLocalRef<jobject>::null(env);

                switch (type) {
                    case kJSTypeUndefined:
                        j_type = JSType::UNDEFINED.get(env);
                        break;
                    case kJSTypeNull:
                        j_type = JSType::NULL_.get(env);
                        break;
                    case kJSTypeBoolean:
                        j_type = JSType::BOOLEAN.get(env);
                        break;
                    case kJSTypeNumber:
                        j_type = JSType::NUMBER.get(env);
                        break;
                    case kJSTypeString:
                        j_type = JSType::STRING.get(env);
                        break;
                    case kJSTypeObject:
                        j_type = JSType::OBJECT.get(env);
                        break;
                    case kJSTypeSymbol:
                        j_type = JSType::SYMBOL.get(env);
                        break;
                }

                auto j_result = JNIJSCJSObjectConvertToTypeCallback::CONVERT_TO_TYPE
                                    .invoke(env, shared_data->convert_to_type_callback, j_context, j_object, j_type);

                return reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, j_result));
            }

            return static_cast<JSValueRef>(nullptr);
        });
    }
} // namespace ujr
