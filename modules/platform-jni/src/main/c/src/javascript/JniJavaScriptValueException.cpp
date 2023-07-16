#include "ujr/javascript/JniJavaScriptValueException.hpp"
#include "net_janrupf_ujr_platform_jni_exception_JniJavaScriptValueException_native_access.hpp"

#include "ujr/javascript/JSValue.hpp"
#include "ujr/util/JniEnv.hpp"

namespace ujr {
    void JniJavaScriptValueException::throw_if_valid(JSContextRef context, JSValueRef exception) {
        if (exception) {
            JSGlobalContextRef global_context = JSContextGetGlobalContext(context);
            JSGlobalContextRetain(global_context);

            throw JniJavaScriptValueException(global_context, exception);
        }
    }

    JniJavaScriptValueException::JniJavaScriptValueException(JSGlobalContextRef context, JSValueRef value)
        : context(context)
        , value(value) {}

    JniJavaScriptValueException::~JniJavaScriptValueException() {
        JSValueUnprotect(context, value);
        JSGlobalContextRelease(context);
    }

    JniLocalRef<jthrowable> JniJavaScriptValueException::translate_to_java() const {
        using native_access::JniJavaScriptValueException;

        auto env = JniEnv::require_existing_from_thread();
        auto j_js_value = JSValue::wrap(env, context, value);

        return JniJavaScriptValueException::CONSTRUCTOR.invoke(env, j_js_value);
    }

    JSGlobalContextRef JniJavaScriptValueException::get_context() const { return context; }

    JSValueRef JniJavaScriptValueException::get_value() const { return value; }
} // namespace ujr