#include "ujr/javascript/JSContextGroup.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSContextGroup_native_access.hpp"

namespace ujr {
    JniLocalRef<jobject> JSContextGroup::wrap(const JniEnv &env, JSContextGroupRef group) {
        using native_access::JNIJSCJSContextGroup;

        auto j_group = JNIJSCJSContextGroup::CLAZZ.alloc_object(env);
        JNIJSCJSContextGroup::HANDLE.set(env, j_group, reinterpret_cast<jlong>(group));
        ujr::GCSupport::attach_collector(env, j_group, new JSContextGroupCollector(group));

        return j_group;
    }

    JSContextGroupCollector::JSContextGroupCollector(JSContextGroupRef group)
        : group(group) {}

    void JSContextGroupCollector::collect() { JSContextGroupRelease(group); }
} // namespace ujr
