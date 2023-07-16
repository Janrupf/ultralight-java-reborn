#include "ujr/javascript/JSObject.hpp"
#include "net_janrupf_ujr_api_javascript_JSPropertyAttribute_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject.h"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_native_access.hpp"

#include <vector>

#include "ujr/javascript/JniJavaScriptValueException.hpp"
#include "ujr/javascript/JSContext.hpp"
#include "ujr/javascript/JSString.hpp"
#include "ujr/javascript/JSUtil.hpp"
#include "ujr/javascript/JSValue.hpp"
#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeGetPrototype(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        auto prototype = JSObjectGetPrototype(context, object);
        JSValueProtect(context, prototype);

        return ujr::JSValue::wrap(env, context, prototype).leak();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeSetPrototype(
    JNIEnv *env, jobject self, jobject prototype
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        auto prototype_value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, prototype));

        JSObjectSetPrototype(context, object, prototype_value);
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeHasProperty(
    JNIEnv *env, jobject self, jstring name
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto j_name = env.wrap_argument(name);

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        auto name_string = ujr::JSString::from_java(env, j_name);
        auto result = JSObjectHasProperty(context, object, name_string);
        JSStringRelease(name_string);

        return result;
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeGetProperty(
    JNIEnv *env, jobject self, jstring name
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto j_name = env.wrap_argument(name);

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        JSValueRef exception = nullptr;

        auto name_string = ujr::JSString::from_java(env, j_name);
        auto result = JSObjectGetProperty(context, object, name_string, &exception);
        JSStringRelease(name_string);

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        JSValueProtect(context, result);
        return ujr::JSValue::wrap(env, context, result).leak();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeSetProperty(
    JNIEnv *env, jobject self, jstring name, jobject value, jobjectArray attributes
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto j_name = env.wrap_argument(name);
        auto j_value = env.wrap_argument(value);
        auto j_attributes = env.wrap_argument(attributes);

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        JSValueRef exception = nullptr;

        auto name_string = ujr::JSString::from_java(env, j_name);
        auto value_value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, j_value));
        auto js_attributes = ujr::JSUtil::property_attributes_to_js(env, j_attributes);

        JSObjectSetProperty(context, object, name_string, value_value, js_attributes, &exception);
        JSStringRelease(name_string);

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeDeleteProperty(
    JNIEnv *env, jobject self, jstring name
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto j_name = env.wrap_argument(name);

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        JSValueRef exception = nullptr;

        auto name_string = ujr::JSString::from_java(env, j_name);
        auto result = JSObjectDeleteProperty(context, object, name_string, &exception);
        JSStringRelease(name_string);

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        return result;
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeHasPropertyForKey(
    JNIEnv *env, jobject self, jobject key
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto j_key = env.wrap_argument(key);

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        auto key_value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, j_key));

        JSValueRef exception = nullptr;
        auto result = JSObjectHasPropertyForKey(context, object, key_value, &exception);

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        return result;
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeGetPropertyForKey(
    JNIEnv *env, jobject self, jobject key
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto j_key = env.wrap_argument(key);

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        auto key_value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, j_key));

        JSValueRef exception = nullptr;
        auto result = JSObjectGetPropertyForKey(context, object, key_value, &exception);

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        JSValueProtect(context, result);
        return ujr::JSValue::wrap(env, context, result).leak();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeSetPropertyForKey(
    JNIEnv *env, jobject self, jobject key, jobject value, jobjectArray attributes
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto j_key = env.wrap_argument(key);
        auto j_value = env.wrap_argument(value);
        auto j_attributes = env.wrap_argument(attributes);

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        auto key_value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, j_key));
        auto value_value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, j_value));
        auto js_attributes = ujr::JSUtil::property_attributes_to_js(env, j_attributes);

        JSValueRef exception = nullptr;
        JSObjectSetPropertyForKey(context, object, key_value, value_value, js_attributes, &exception);

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeDeletePropertyForKey(
    JNIEnv *env, jobject self, jobject key
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto j_key = env.wrap_argument(key);

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        auto key_value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, j_key));

        JSValueRef exception = nullptr;
        auto result = JSObjectDeletePropertyForKey(context, object, key_value, &exception);

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        return result;
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeGetPropertyAtIndex(
    JNIEnv *env, jobject self, jint index
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        JSValueRef exception = nullptr;
        auto result = JSObjectGetPropertyAtIndex(context, object, index, &exception);

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        JSValueProtect(context, result);
        return ujr::JSValue::wrap(env, context, result).leak();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeSetPropertyAtIndex(
    JNIEnv *env, jobject self, jint index, jobject value
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto j_value = env.wrap_argument(value);

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        auto value_value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, j_value));

        JSValueRef exception = nullptr;
        JSObjectSetPropertyAtIndex(context, object, index, value_value, &exception);

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeIsFunction(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSObjectIsFunction(context, object);
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeCallAsFunction(
    JNIEnv *env, jobject self, jobject this_object, jobjectArray arguments
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSObject;
        using ujr::native_access::JNIJSCJSValue;

        auto j_this_object = env.wrap_argument(this_object);
        auto j_arguments = env.wrap_argument(arguments);

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        auto this_object_value = j_this_object.is_valid()
            ? reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, j_this_object))
            : nullptr;
        auto js_arguments = ujr::JSUtil::value_array_to_vector(env, j_arguments);

        JSValueRef exception = nullptr;
        auto result = JSObjectCallAsFunction(
            context,
            object,
            this_object_value,
            js_arguments.size(),
            js_arguments.size() > 0 ? js_arguments.data() : nullptr,
            &exception
        );

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        JSValueProtect(context, result);
        return ujr::JSValue::wrap(env, context, result).leak();
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeIsConstructor(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSObjectIsConstructor(context, object);
    });
}

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeCallAsConstructor(
    JNIEnv *env, jobject self, jobjectArray arguments
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSObject;
        using ujr::native_access::JNIJSCJSValue;

        auto j_arguments = env.wrap_argument(arguments);

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        auto js_arguments = ujr::JSUtil::value_array_to_vector(env, j_arguments);

        JSValueRef exception = nullptr;
        auto result = JSObjectCallAsConstructor(
            context,
            object,
            js_arguments.size(),
            js_arguments.size() > 0 ? js_arguments.data() : nullptr,
            &exception
        );

        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        JSValueProtect(context, result);
        return ujr::JSValue::wrap(env, context, result).leak();
    });
}

JNIEXPORT jobjectArray JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSObject_nativeGetPropertyNames(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSObject;
        using ujr::native_access::JNIJSCJSValue;

        auto object = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        JSPropertyNameArrayRef property_names = JSObjectCopyPropertyNames(context, object);
        size_t count = JSPropertyNameArrayGetCount(property_names);

        auto result = env->NewObjectArray(count, env->FindClass("java/lang/String"), nullptr);

        for (size_t i = 0; i < count; i++) {
            auto property_name = JSPropertyNameArrayGetNameAtIndex(property_names, i);
            auto j_property_name = ujr::JSString::to_java(env, property_name);

            env->SetObjectArrayElement(result, i, j_property_name);
        }

        JSPropertyNameArrayRelease(property_names);

        return result;
    });
}

namespace ujr {
    JniLocalRef<jobject> JSObject::wrap(const JniEnv &env, JSContextRef context, JSObjectRef value) {
        using native_access::JNIJSCJSObject;
        using native_access::JNIJSCJSValue;

        if (!value) {
            return JniLocalRef<jobject>::null(env);
        }

        auto j_value = JNIJSCJSObject::CLAZZ.alloc_object(env);
        JNIJSCJSValue::HANDLE.set(env, j_value, reinterpret_cast<jlong>(value));

        if (context) {
            JSGlobalContextRef global_ctx = JSContextGetGlobalContext(context);
            JSGlobalContextRetain(global_ctx);

            JNIJSCJSValue::CONTEXT.set(env, j_value, reinterpret_cast<jlong>(global_ctx));

            auto *collector = new JSValueCollector(global_ctx, value);
            JNIJSCJSValue::COLLECTOR.set(env, j_value, reinterpret_cast<jlong>(collector));

            GCSupport::attach_collector(env, j_value, collector);
        }

        return j_value;
    }
} // namespace ujr
