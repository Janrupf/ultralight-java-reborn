#include "ujr/javascript/JSContext.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext.h"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_native_access.hpp"

#include "ujr/javascript/JSContextGroup.hpp"
#include "ujr/javascript/JSGlobalContext.hpp"
#include "ujr/javascript/JSString.hpp"
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

namespace ujr {
    JniLocalRef<jobject> JSCContext::wrap(const JniEnv &env, JSContextRef context) {
        using native_access::JNIJSCJSContext;

        auto j_context = JNIJSCJSContext::CLAZZ.alloc_object(env);
        JNIJSCJSContext::HANDLE.set(env, j_context, reinterpret_cast<jlong>(context));

        return j_context;
    }
} // namespace ujr
