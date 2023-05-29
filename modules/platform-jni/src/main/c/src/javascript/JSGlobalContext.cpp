#include "ujr/javascript/JSGlobalContext.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContext_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSGlobalContext.h"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSGlobalContext_native_access.hpp"

#include "ujr/javascript/JSString.hpp"
#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSGlobalContext_nativeSetName(
    JNIEnv *env, jobject self, jstring new_name
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto context = reinterpret_cast<JSGlobalContextRef>(JNIJSCJSContext::HANDLE.get(env, self));

        auto j_new_name = env.wrap_argument(new_name);
        if (j_new_name.is_valid()) {
            JSGlobalContextSetName(context, ujr::JSString::from_java(env, j_new_name));
        } else {
            JSGlobalContextSetName(context, nullptr);
        }
    });
}

JNIEXPORT jstring JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSGlobalContext_nativeGetName(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSContext;

        auto j_name = ujr::JniLocalRef<jstring>::null(env);

        auto context = reinterpret_cast<JSGlobalContextRef>(JNIJSCJSContext::HANDLE.get(env, self));
        auto js_name = JSGlobalContextCopyName(context);
        if (js_name) {
            j_name = ujr::JSString::to_java(env, js_name);
            JSStringRelease(js_name);
        }

        return j_name.leak();
    });
}

namespace ujr {
    JniLocalRef<jobject> JSGlobalContext::wrap(const JniEnv &env, JSGlobalContextRef context) {
        using native_access::JNIJSCJSContext;
        using native_access::JNIJSCJSGlobalContext;

        auto j_context = JNIJSCJSGlobalContext::CLAZZ.alloc_object(env);
        JNIJSCJSContext::HANDLE.set(env, j_context, reinterpret_cast<jlong>(context));
        GCSupport::attach_collector(env, j_context, new JSGlobalContextCollector(context));

        return j_context;
    }

    JSGlobalContextCollector::JSGlobalContextCollector(JSGlobalContextRef context)
        : context(context) {}

    void JSGlobalContextCollector::collect() { JSGlobalContextRelease(context); }
} // namespace ujr