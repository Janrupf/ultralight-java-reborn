#include "ujr/support/GC.hpp"
#include "net_janrupf_ujr_platform_jni_gc_ObjectCollector.h"
#include "net_janrupf_ujr_platform_jni_gc_ObjectCollector_native_access.hpp"

#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_gc_ObjectCollector_nativeCollect(
    JNIEnv *env, jclass /* self */, jlongArray collectors
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        // The contract of collectors states that it is never null, has at least one element
        // and that the last element is a 0.
        //
        // We can't use critical access here as collectors may interact with JNI
        jlong *collectors_ptr = env->GetLongArrayElements(collectors, nullptr);

        for (size_t i = 0; collectors_ptr[i] != 0; i++) {
            auto collector = reinterpret_cast<ujr::NativeCollector *>(collectors_ptr[i]);

            try {
                collector->collect();
                delete collector;
            } catch (...) {
                // Collectors are never allowed to throw exceptions
                //
                // TODO: Deal with this better
                std::abort();
            }
        }

        env->ReleaseLongArrayElements(collectors, collectors_ptr, JNI_ABORT);
    });
}

namespace ujr {
    NativeCollector::NativeCollector() = default;

    NativeCollector::~NativeCollector() = default;

    void GCSupport::attach_collector(const JniEnv &env, const JniStrongRef<jobject> &obj, NativeCollector *collector) {
        using native_access::ObjectCollector;

        ObjectCollector::ATTACH_COLLECTOR.invoke(env, obj.get(), reinterpret_cast<jlong>(collector));
    }
} // namespace ujr