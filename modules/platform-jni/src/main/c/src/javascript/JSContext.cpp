#include "ujr/javascript/JSContext.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext.h"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_native_access.hpp"

#include "ujr/javascript/JSContextGroup.hpp"
#include "ujr/javascript/JSGlobalContext.hpp"
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

namespace ujr {
    JniLocalRef<jobject> JSCContext::wrap(const JniEnv &env, JSContextRef context) {
        using native_access::JNIJSCJSContext;

        auto j_context = JNIJSCJSContext::CLAZZ.alloc_object(env);
        JNIJSCJSContext::HANDLE.set(env, j_context, reinterpret_cast<jlong>(context));

        return j_context;
    }
} // namespace ujr
