#include "ujr/javascript/JSContext.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSClass_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext.h"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_native_access.hpp"

#include "ujr/javascript/JniJavaScriptValueException.hpp"
#include "ujr/javascript/JSContextGroup.hpp"
#include "ujr/javascript/JSGlobalContext.hpp"
#include "ujr/javascript/JSObject.hpp"
#include "ujr/javascript/JSString.hpp"
#include "ujr/javascript/JSUtil.hpp"
#include "ujr/javascript/JSValue.hpp"
#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeGetGlobalContext(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        auto global_context = JSContextGetGlobalContext(context);

        JSGlobalContextRetain(global_context);
        return ujr::JSGlobalContext::wrap(env, global_context).leak();
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeGetGroup(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        auto group = JSContextGetGroup(context);

        JSContextGroupRetain(group);
        return ujr::JSContextGroup::wrap(env, group).leak();
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeGetGlobalObject(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        auto global_object = JSContextGetGlobalObject(context);

        JSValueProtect(context, global_object);
        return ujr::JSObject::wrap(env, context, global_object).leak();
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeUndefined(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        auto value = JSValueMakeUndefined(context);

        return ujr::JSValue::wrap(env, context, value).leak();
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeNull(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        auto value = JSValueMakeNull(context);

        return ujr::JSValue::wrap(env, context, value).leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeBoolean(
    JNIEnv *env, jobject self, jboolean value
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        auto js_value = JSValueMakeBoolean(context, value);

        return ujr::JSValue::wrap(env, context, js_value).leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeNumber(
    JNIEnv *env, jobject self, jdouble value
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        auto js_value = JSValueMakeNumber(context, value);

        return ujr::JSValue::wrap(env, context, js_value).leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeString(
    JNIEnv *env, jobject self, jstring value
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto j_value = env.wrap_argument(value);
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));

        auto js_string = ujr::JSString::from_java(env, j_value);
        auto js_value = JSValueMakeString(context, js_string);
        JSStringRelease(js_string);

        return ujr::JSValue::wrap(env, context, js_value).leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeSymbol(
    JNIEnv *env, jobject self, jstring description
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto j_description = env.wrap_argument(description);
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));

        auto js_string = ujr::JSString::from_java(env, j_description);
        auto js_value = JSValueMakeSymbol(context, js_string);
        JSStringRelease(js_string);

        return ujr::JSValue::wrap(env, context, js_value).leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeFromJSONString(
    JNIEnv *env, jobject self, jstring json_value
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto j_json_value = env.wrap_argument(json_value);
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));

        auto js_string = ujr::JSString::from_java(env, j_json_value);
        auto js_value = JSValueMakeFromJSONString(context, js_string);
        JSStringRelease(js_string);

        return ujr::JSValue::wrap(env, context, js_value).leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeObject(
    JNIEnv *env, jobject self, jobject clazz
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;
        using ujr::native_access::JNIJSCJSObject;
        using ujr::native_access::JNIJSCJSClass;

        auto j_clazz = env.wrap_argument(clazz);
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));

        JSClassRef js_class = nullptr;
        if (j_clazz.is_valid()) {
            js_class = reinterpret_cast<JSClassRef>(JNIJSCJSClass::HANDLE.get(env, clazz));
        }

        auto js_object = JSObjectMake(context, js_class, nullptr);

        auto j_object = ujr::JSObject::wrap(env, context, js_object).leak();

        return j_object;
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeArray(
    JNIEnv *env, jobject self, jobjectArray arguments
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto j_arguments = env.wrap_argument(arguments);

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        auto js_arguments = ujr::JSUtil::value_array_to_vector(env, j_arguments);

        JSValueRef exception = nullptr;
        auto js_array = JSObjectMakeArray(
            context,
            js_arguments.size(),
            js_arguments.size() > 0 ? js_arguments.data() : nullptr,
            &exception
        );

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        return ujr::JSObject::wrap(env, context, js_array).leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeDate(
    JNIEnv *env, jobject self, jobjectArray arguments
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto j_arguments = env.wrap_argument(arguments);

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        auto js_arguments = ujr::JSUtil::value_array_to_vector(env, j_arguments);

        JSValueRef exception = nullptr;
        auto js_date = JSObjectMakeDate(
            context,
            js_arguments.size(),
            js_arguments.size() > 0 ? js_arguments.data() : nullptr,
            &exception
        );

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        return ujr::JSObject::wrap(env, context, js_date).leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeError(
    JNIEnv *env, jobject self, jobjectArray arguments
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto j_arguments = env.wrap_argument(arguments);

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        auto js_arguments = ujr::JSUtil::value_array_to_vector(env, j_arguments);

        JSValueRef exception = nullptr;
        auto js_error = JSObjectMakeError(
            context,
            js_arguments.size(),
            js_arguments.size() > 0 ? js_arguments.data() : nullptr,
            &exception
        );

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        return ujr::JSObject::wrap(env, context, js_error).leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeRegExp(
    JNIEnv *env, jobject self, jobjectArray arguments
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto j_arguments = env.wrap_argument(arguments);

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        auto js_arguments = ujr::JSUtil::value_array_to_vector(env, j_arguments);

        JSValueRef exception = nullptr;
        auto js_regexp = JSObjectMakeRegExp(
            context,
            js_arguments.size(),
            js_arguments.size() > 0 ? js_arguments.data() : nullptr,
            &exception
        );

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        return ujr::JSObject::wrap(env, context, js_regexp).leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeMakeFunction(
    JNIEnv *env,
    jobject self,
    jstring name,
    jobjectArray parameter_names,
    jstring body,
    jstring source_url,
    jint starting_line_number
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto j_name = env.wrap_argument(name);
        auto j_parameter_names = env.wrap_argument(parameter_names);
        auto j_body = env.wrap_argument(body);
        auto j_source_url = env.wrap_argument(source_url);

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));

        jsize js_parameter_names_length = env->GetArrayLength(j_parameter_names);

        auto *js_parameter_names = js_parameter_names_length > 0 ? new JSStringRef[js_parameter_names_length] : nullptr;
        for (jsize i = 0; i < js_parameter_names_length; i++) {
            auto j_parameter_name
                = env.wrap_argument(reinterpret_cast<jstring>(env->GetObjectArrayElement(j_parameter_names, i)));
            js_parameter_names[i] = ujr::JSString::from_java(env, j_parameter_name);
        }

        auto js_name = j_name.is_valid() ? ujr::JSString::from_java(env, j_name) : nullptr;
        auto js_body = ujr::JSString::from_java(env, j_body);
        auto js_source_url = j_source_url.is_valid() ? ujr::JSString::from_java(env, j_source_url) : nullptr;

        JSValueRef exception = nullptr;
        auto js_function = JSObjectMakeFunction(
            context,
            js_name,
            js_parameter_names_length,
            js_parameter_names_length > 0 ? js_parameter_names : nullptr,
            js_body,
            js_source_url,
            starting_line_number,
            &exception
        );

        delete[] js_parameter_names;

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        return ujr::JSObject::wrap(env, context, js_function).leak();
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeEvaluateScript(
    JNIEnv *env, jobject self, jstring script, jobject this_object, jstring source_url, jint starting_line_number
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto j_script = env.wrap_argument(script);
        auto j_this_object = env.wrap_argument(this_object);
        auto j_source_url = env.wrap_argument(source_url);
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));

        auto js_string = ujr::JSString::from_java(env, j_script);
        auto js_this = j_this_object.is_valid()
            ? reinterpret_cast<JSObjectRef>(ujr::native_access::JNIJSCJSValue::HANDLE.get(env, j_this_object))
            : nullptr;
        auto js_source_url = j_source_url.is_valid() ? ujr::JSString::from_java(env, j_source_url) : nullptr;

        JSValueRef exception = nullptr;
        auto js_value = JSEvaluateScript(context, js_string, js_this, js_source_url, starting_line_number, &exception);

        JSStringRelease(js_string);

        if (js_source_url) {
            JSStringRelease(js_source_url);
        }

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        return ujr::JSValue::wrap(env, context, js_value).leak();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeCheckScriptSyntax(
    JNIEnv *env, jobject self, jstring script, jstring source_url, jint starting_line_number
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto j_script = env.wrap_argument(script);
        auto j_source_url = env.wrap_argument(source_url);
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));

        auto js_string = ujr::JSString::from_java(env, j_script);
        auto js_source_url = j_source_url.is_valid() ? ujr::JSString::from_java(env, j_source_url) : nullptr;

        JSValueRef exception = nullptr;
        JSCheckScriptSyntax(context, js_string, js_source_url, starting_line_number, &exception);

        JSStringRelease(js_string);

        if (js_source_url) {
            JSStringRelease(js_source_url);
        }

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_nativeCollectGarbage(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        JSGarbageCollect(context);
    });
}

namespace ujr {
    JniLocalRef<jobject> JSContext::wrap(const JniEnv &env, JSContextRef context) {
        using native_access::JNIJSCJSContext;

        auto j_context = JNIJSCJSContext::CLAZZ.alloc_object(env);
        JNIJSCJSContext::HANDLE.set(env, j_context, reinterpret_cast<jlong>(context));

        return j_context;
    }
} // namespace ujr
