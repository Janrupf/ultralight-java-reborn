#include "ujr/javascript/JSValue.hpp"
#include "net_janrupf_ujr_api_javascript_JSType_native_access.hpp"
#include "net_janrupf_ujr_api_javascript_JSTypedArrayType_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSClass_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue.h"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_native_access.hpp"

#include "ujr/javascript/JniJavaScriptValueException.hpp"
#include "ujr/javascript/JSContext.hpp"
#include "ujr/javascript/JSString.hpp"
#include "ujr/util/JniEntryGuard.hpp"

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeGetType(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;
        using ujr::native_access::JSType;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        auto type = JSValueGetType(context, value);

        auto j_type = ujr::JniLocalRef<jobject>::null(env);

        switch (type) {
            case kJSTypeUndefined:
                j_type = JSType::UNDEFINED.get(env);
                break;
            case kJSTypeNull:
                j_type = JSType::NULL_.get(env);
                break;
            case kJSTypeBoolean:
                j_type = JSType::BOOLEAN.get(env);
                break;
            case kJSTypeNumber:
                j_type = JSType::NUMBER.get(env);
                break;
            case kJSTypeString:
                j_type = JSType::STRING.get(env);
                break;
            case kJSTypeObject:
                j_type = JSType::OBJECT.get(env);
                break;
            case kJSTypeSymbol:
                j_type = JSType::SYMBOL.get(env);
                break;
        }

        return j_type.leak();
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsUndefined(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSValueIsUndefined(context, value);
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsNull(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSValueIsNull(context, value);
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsBoolean(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSValueIsBoolean(context, value);
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsNumber(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSValueIsNumber(context, value);
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsString(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSValueIsString(context, value);
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsSymbol(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSValueIsSymbol(context, value);
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsObject(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSValueIsObject(context, value);
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsObjectOfClass(
    JNIEnv *env, jobject self, jobject clazz
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;
        using ujr::native_access::JNIJSCJSClass;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        auto js_class = reinterpret_cast<JSClassRef>(JNIJSCJSClass::HANDLE.get(env, clazz));

        return JSValueIsObjectOfClass(context, value, js_class);
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsArray(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSValueIsArray(context, value);
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsDate(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSValueIsDate(context, value);
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeGetTypedArrayType(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;
        using ujr::native_access::JSTypedArrayType;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        JSValueRef exception = nullptr;
        auto type = JSValueGetTypedArrayType(context, value, &exception);
        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        auto j_type = ujr::JniLocalRef<jobject>::null(env);

        switch (type) {
            case kJSTypedArrayTypeInt8Array:
                j_type = JSTypedArrayType::INT8_ARRAY.get(env);
                break;
            case kJSTypedArrayTypeInt16Array:
                j_type = JSTypedArrayType::INT16_ARRAY.get(env);
                break;
            case kJSTypedArrayTypeInt32Array:
                j_type = JSTypedArrayType::INT32_ARRAY.get(env);
                break;
            case kJSTypedArrayTypeUint8Array:
                j_type = JSTypedArrayType::UINT8_ARRAY.get(env);
                break;
            case kJSTypedArrayTypeUint8ClampedArray:
                j_type = JSTypedArrayType::UINT8_CLAMPED_ARRAY.get(env);
                break;
            case kJSTypedArrayTypeUint16Array:
                j_type = JSTypedArrayType::UINT16_ARRAY.get(env);
                break;
            case kJSTypedArrayTypeUint32Array:
                j_type = JSTypedArrayType::UINT32_ARRAY.get(env);
                break;
            case kJSTypedArrayTypeFloat32Array:
                j_type = JSTypedArrayType::FLOAT32_ARRAY.get(env);
                break;
            case kJSTypedArrayTypeFloat64Array:
                j_type = JSTypedArrayType::FLOAT64_ARRAY.get(env);
                break;
            case kJSTypedArrayTypeArrayBuffer:
                j_type = JSTypedArrayType::ARRAY_BUFFER.get(env);
                break;
            case kJSTypedArrayTypeNone:
                j_type = JSTypedArrayType::NONE.get(env);
                break;
        }

        return j_type.leak();
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsEqual(
    JNIEnv *env, jobject self, jobject other
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto other_value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, other));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        JSValueRef exception = nullptr;
        auto result = JSValueIsEqual(context, value, other_value, &exception);
        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        return result;
    });
}

JNIEXPORT jboolean JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsStrictEqual(
    JNIEnv *env, jobject self, jobject other
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto other_value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, other));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSValueIsStrictEqual(context, value, other_value);
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeIsInstanceOfConstructor(
    JNIEnv *env, jobject self, jobject object
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto object_value = reinterpret_cast<JSObjectRef>(JNIJSCJSValue::HANDLE.get(env, object));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        JSValueRef exception = nullptr;
        auto result = JSValueIsInstanceOfConstructor(context, value, object_value, &exception);
        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        return result;
    });
}

JNIEXPORT jstring JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeCreateJSONString(
    JNIEnv *env, jobject self, jint indent
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        JSValueRef exception = nullptr;
        auto string = JSValueCreateJSONString(context, value, indent, &exception);
        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        auto j_string = ujr::JSString::to_java(env, string);
        JSStringRelease(string);

        return j_string.leak();
    });
}

JNIEXPORT jboolean JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeToBoolean(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        return JSValueToBoolean(context, value);
    });
}

JNIEXPORT jdouble JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeToNumber(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        JSValueRef exception = nullptr;
        auto result = JSValueToNumber(context, value, &exception);
        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        return result;
    });
}

JNIEXPORT jstring JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeToString
    (JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto value = reinterpret_cast<JSValueRef>(JNIJSCJSValue::HANDLE.get(env, self));
        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));

        JSValueRef exception = nullptr;
        auto string = JSValueToStringCopy(context, value, &exception);
        ujr::JniJavaScriptValueException::throw_if_valid(context, exception);

        auto j_string = ujr::JSString::to_java(env, string);
        JSStringRelease(string);

        return j_string.leak();
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeToObject(JNIEnv *env, jobject self) {
    return nullptr; // TODO
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_nativeGetContext(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCJSValue;

        auto context = reinterpret_cast<JSContextRef>(JNIJSCJSValue::CONTEXT.get(env, self));
        return ujr::JSCContext::wrap(env, context).leak();
    });
}

namespace ujr {
    JniLocalRef<jobject> JSValue::wrap(const JniEnv &env, JSContextRef context, JSValueRef value) {
        using native_access::JNIJSCJSValue;

        if (!value) {
            return JniLocalRef<jobject>::null(env);
        }

        JSGlobalContextRef global_ctx = JSContextGetGlobalContext(context);
        JSGlobalContextRetain(global_ctx);

        auto j_value = JNIJSCJSValue::CLAZZ.alloc_object(env);
        JNIJSCJSValue::HANDLE.set(env, j_value, reinterpret_cast<jlong>(value));
        JNIJSCJSValue::CONTEXT.set(env, j_value, reinterpret_cast<jlong>(global_ctx));

        auto *collector = new JSValueCollector(global_ctx, value);
        JNIJSCJSValue::COLLECTOR.set(env, j_value, reinterpret_cast<jlong>(collector));

        GCSupport::attach_collector(env, j_value, collector);

        return j_value;
    }

    JSValueCollector::JSValueCollector(JSGlobalContextRef context, JSValueRef value)
        : context(context)
        , value(value) {}

    void JSValueCollector::collect() {
        if (context && value) {
            JSValueUnprotect(context, value);
            JSGlobalContextRelease(const_cast<JSGlobalContextRef>(context));

            context = nullptr;
            value = nullptr;
        }
    }
} // namespace ujr
