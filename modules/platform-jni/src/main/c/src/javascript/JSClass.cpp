#include "ujr/javascript/JSClass.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSClass_native_access.hpp"

namespace ujr {
    JniLocalRef<jobject> JSClass::wrap(const JniEnv &env, JSClassRef clazz) {
        using native_access::JNIJSCJSClass;

        auto j_class = JNIJSCJSClass::CLAZZ.alloc_object(env);
        JNIJSCJSClass::HANDLE.set(env, j_class, reinterpret_cast<jlong>(clazz));
        GCSupport::attach_collector(env, j_class, new JSClassCollector(clazz));

        return j_class;
    }

    JSClassCollector::JSClassCollector(JSClassRef clazz)
        : clazz(clazz) {}

    void JSClassCollector::collect() { JSClassRelease(clazz); }
} // namespace ujr
