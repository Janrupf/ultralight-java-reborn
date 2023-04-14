#include "ujr/util/JniRef.hpp"

namespace ujr::_internal {
    thread_local jclass CLASS_NOT_FOUND_EXCEPTION = nullptr;

    jclass get_class_not_found_exception_class(const JniEnv &env) {
        // The error checking below would be insufficient for normal cases, but we are dealing with a
        // JVM built in class here, so we can assume that it is always available and loading never fails.

        if (env->IsSameObject(CLASS_NOT_FOUND_EXCEPTION, nullptr)) {
            auto ref_class_not_found_exception_class = env->FindClass("java/lang/ClassNotFoundException");
            CLASS_NOT_FOUND_EXCEPTION
                = reinterpret_cast<jclass>(env->NewGlobalRef(ref_class_not_found_exception_class));
            env->DeleteLocalRef(ref_class_not_found_exception_class);
        }

        return CLASS_NOT_FOUND_EXCEPTION;
    }
} // namespace ujr::__internal
