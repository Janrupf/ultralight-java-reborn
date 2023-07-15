#include "ujr/javascript/JSUtil.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_native_access.hpp"

namespace ujr {
    std::vector<JSValueRef> JSUtil::value_array_to_vector(const JniEnv &env, const JniStrongRef<jobjectArray> &array) {
        std::vector<JSValueRef> js_arguments;

        if (!array.is_valid(env)) {
            return js_arguments;
        }

        js_arguments.reserve(env->GetArrayLength(array));

        for (jsize i = 0; i < env->GetArrayLength(array); i++) {
            auto argument = env->GetObjectArrayElement(array, i);
            js_arguments.push_back(
                reinterpret_cast<JSValueRef>(ujr::native_access::JNIJSCJSValue::HANDLE.get(env, argument))
            );
        }

        return js_arguments;
    }
} // namespace ujr
