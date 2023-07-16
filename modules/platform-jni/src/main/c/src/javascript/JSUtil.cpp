#include "ujr/javascript/JSUtil.hpp"
#include "net_janrupf_ujr_api_javascript_JSClassAttribute_native_access.hpp"
#include "net_janrupf_ujr_api_javascript_JSPropertyAttribute_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_native_access.hpp"

#include <JavaScriptCore/JSObjectRef.h>

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

    unsigned JSUtil::property_attributes_to_js(const JniEnv &env, const JniStrongRef<jobjectArray> &attributes) {
        using native_access::JSPropertyAttribute;

        if (!attributes.is_valid(env)) {
            return kJSPropertyAttributeNone;
        }

        unsigned js_attributes = kJSPropertyAttributeNone;

        for (jsize i = 0; i < env->GetArrayLength(attributes); i++) {
            auto attribute = env->GetObjectArrayElement(attributes, i);

            if (attribute == JSPropertyAttribute::READ_ONLY.get(env)) {
                js_attributes |= kJSPropertyAttributeReadOnly;
            } else if (attribute == JSPropertyAttribute::DONT_ENUM.get(env)) {
                js_attributes |= kJSPropertyAttributeDontEnum;
            } else if (attribute == JSPropertyAttribute::DONT_DELETE.get(env)) {
                js_attributes |= kJSPropertyAttributeDontDelete;
            }
        }

        return js_attributes;
    }

    unsigned JSUtil::class_attributes_to_js(const JniEnv &env, const JniStrongRef<jobjectArray> &attributes) {
        using native_access::JSClassAttribute;

        if (!attributes.is_valid(env)) {
            return kJSClassAttributeNone;
        }

        unsigned js_attributes = kJSClassAttributeNone;

        for (jsize i = 0; i < env->GetArrayLength(attributes); i++) {
            auto attribute = env->GetObjectArrayElement(attributes, i);

            if (attribute == JSClassAttribute::NO_AUTOMATIC_PROTOTYPE.get(env)) {
                js_attributes |= kJSClassAttributeNoAutomaticPrototype;
            }
        }

        return js_attributes;
    }
} // namespace ujr
