#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContextGroupFactory.h"

#include "ujr/javascript/JSContextGroup.hpp"
#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContextGroupFactory_nativeCreate(JNIEnv *env, jobject) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        JSContextGroupRef group = JSContextGroupCreate();

        return ujr::JSContextGroup::wrap(env, group).leak();
    });
}