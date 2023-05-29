#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSClass_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContextGroup_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSGlobalContextFactory.h"

#include "ujr/javascript/JSGlobalContext.hpp"
#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSGlobalContextFactory_nativeCreate(
    JNIEnv *env, jobject, jobject global_class
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSClass;

        auto js_global_class = reinterpret_cast<JSClassRef>(JNIJSCJSClass::HANDLE.get(env, global_class));
        auto context = JSGlobalContextCreate(js_global_class);

        return ujr::JSGlobalContext::wrap(env, context).leak();
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSGlobalContextFactory_nativeCreateInGroup(
    JNIEnv *env, jobject, jobject group, jobject global_class
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSClass;
        using ujr::native_access::JNIJSCJSContextGroup;

        auto js_global_class = reinterpret_cast<JSClassRef>(JNIJSCJSClass::HANDLE.get(env, global_class));
        auto js_group = reinterpret_cast<JSContextGroupRef>(JNIJSCJSContextGroup::HANDLE.get(env, group));
        auto context = JSGlobalContextCreateInGroup(js_group, js_global_class);

        return ujr::JSGlobalContext::wrap(env, context).leak();
    });
}
