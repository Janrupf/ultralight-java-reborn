#include "ujr/util/JniException.hpp"
#include "net_janrupf_ujr_platform_jni_exception_JniJavaScriptValueException_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSValue_native_access.hpp"

#include <stdexcept>

#include "ujr/javascript/JniJavaScriptValueException.hpp"
#include "ujr/util/JniClass.hpp"
#include "ujr/util/JniMethod.hpp"

namespace ujr {
    static JniClass<"net/janrupf/ujr/platform/jni/exception/CPPException", jthrowable> CPP_EXCEPTION_CLASS;
    static JniConstructor<decltype(CPP_EXCEPTION_CLASS), jstring> CPP_EXCEPTION_CONSTRUCTOR(CPP_EXCEPTION_CLASS);

    static JniClass<"net/janrupf/ujr/api/javascript/JavaScriptValueException", jthrowable>
        JAVA_SCRIPT_VALUE_EXCEPTION_CLASS;

    static JniClass<"net/janrupf/ujr/api/javascript/JSValue", jobject> JS_VALUE_CLASS;
    static JniInstanceField<
        decltype(JS_VALUE_CLASS),
        "value",
        JniClass<"net/janrupf/ujr/core/platform/abstraction/javascript/JSCJSValue">>
        JS_VALUE_VALUE_FIELD(JS_VALUE_CLASS);

    static JniInstanceField<decltype(JAVA_SCRIPT_VALUE_EXCEPTION_CLASS), "value", decltype(JS_VALUE_CLASS)>
        JAVA_SCRIPT_VALUE_EXCEPTION_VALUE_FIELD(JAVA_SCRIPT_VALUE_EXCEPTION_CLASS);

    static JniClass<"java/io/IOException", jthrowable> IO_EXCEPTION_CLASS;

    JniException::JniException()
        : exception(nullptr) {}

    JniException::JniException(std::variant<JniGlobalRef<jthrowable>, std::exception_ptr> exception)
        : exception(std::move(exception)) {}

    bool JniException::rethrow(const JniEnv &env) const {
        if (env->ExceptionCheck()) {
            return false;
        }

        const auto &java = this->translate_to_java();
        java.do_throw(env);

        return true;
    }

    JniGlobalRef<jthrowable> JniException::translate_to_java() const { // NOLINT(misc-no-recursion)
        if (std::holds_alternative<JniGlobalRef<jthrowable>>(exception)) {
            // No translation necessary
            return std::get<JniGlobalRef<jthrowable>>(exception).clone_as_global(JniEnv::from_thread());
        } else if (std::holds_alternative<std::exception_ptr>(exception)) {
            auto exception_ptr = std::get<std::exception_ptr>(exception);

            try {
                // Rethrowing is the only way to obtain the exception back
                std::rethrow_exception(exception_ptr);
            } catch (const JniException &e) {
                // This is a JNI exception, so we can delegate the translation
                return e.translate_to_java();
            } catch (const std::exception &e) {
                // This is a C++ exception, so we need to translate it to a Java exception
                auto env = JniEnv::from_thread(false);
                if (!env.is_valid()) {
                    // Bad situation, this is essentially a bug
                    throw std::runtime_error("Attempted to throw JNI exception on a non JNI thread");
                }

                auto message = JniLocalRef<jstring>::from_utf8(env, e.what());

                auto clazz = CPP_EXCEPTION_CLASS.maybe_get(env);
                if (!clazz.is_valid()) {
                    // Could not load the class, so we need to throw a generic exception
                    // We assume that java.lang.RuntimeException is always available
                    auto runtime_exception_class
                        = JniLocalRef<jclass>::wrap(env, env->FindClass("java/lang/RuntimeException"));
                    auto constructor_id = env->GetMethodID(runtime_exception_class, "<init>", "(Ljava/lang/String;)V");

                    auto j_exception = JniLocalRef<jthrowable>::wrap(
                        env,
                        reinterpret_cast<jthrowable>(
                            env->NewObject(runtime_exception_class, constructor_id, message.get())
                        )
                    );

                    return j_exception.clone_as_global();
                }

                // And finally construct the actual exception
                return CPP_EXCEPTION_CONSTRUCTOR.invoke(env, message).clone_as_global();
            }
        } else {
            std::abort(); // UNREACHABLE
        }
    }

    JniException::JniException(JniException &&other) noexcept
        : exception(std::move(other.exception)) {}

    JniException &JniException::operator=(JniException &&other) noexcept {
        this->exception = std::move(other.exception);
        return *this;
    }

    void JniException::throw_if_pending(const JniEnv &env) {
        if (env->ExceptionCheck()) {
            // There is a pending exception, so we need to throw it
            auto exception_ref = JniLocalRef<jthrowable>::wrap(env, env->ExceptionOccurred());
            env->ExceptionClear(); // We effectively "consume" the exception

            // Rethrow the exception
            throw_java_as_cpp(exception_ref);
        }
    }

    void JniException::throw_java_as_cpp(const JniLocalRef<jthrowable> &exception) {
        const auto &env = exception.associated_env();

        // Special case, subclasses of JniException
        if (env->IsInstanceOf(exception.get(), JAVA_SCRIPT_VALUE_EXCEPTION_CLASS.get(env))) {
            // Translate directly to a JNI JavaScript value exception

            JSContextRef context = nullptr;
            JSValueRef js_value = nullptr;
            try {
                auto wrapper_value = JAVA_SCRIPT_VALUE_EXCEPTION_VALUE_FIELD.get(env, exception);
                auto value = JS_VALUE_VALUE_FIELD.get(env, wrapper_value);

                context = reinterpret_cast<JSContextRef>(native_access::JNIJSCJSValue::CONTEXT.get(env, value));
                js_value = reinterpret_cast<JSValueRef>(native_access::JNIJSCJSValue::HANDLE.get(env, value));
            } catch (...) {}

            if (context && js_value) {
                JSValueProtect(context, js_value);
                JniJavaScriptValueException::throw_if_valid(context, js_value);
            }
        }

        // Rethrow the exception
        throw JniException::from_java(exception);
    }

    JniException JniException::from_cpp(std::exception_ptr exception) {
        return JniException(std::variant<JniGlobalRef<jthrowable>, std::exception_ptr>(std::move(exception)));
    }

    JniException JniException::from_java(const JniLocalRef<jthrowable> &exception) {
        return JniException(std::variant<JniGlobalRef<jthrowable>, std::exception_ptr>(exception.clone_as_global()));
    }

    bool JniException::is_java_cpp_exception() const {
        if (std::holds_alternative<JniGlobalRef<jthrowable>>(exception)) {
            const auto &ref = std::get<JniGlobalRef<jthrowable>>(exception);
            auto env = JniEnv::from_thread();

            return env->IsInstanceOf(ref.get(), CPP_EXCEPTION_CLASS.get(env).get());
        }

        return false;
    }

    bool JniException::is_java_io_exception() const {
        if (std::holds_alternative<JniGlobalRef<jthrowable>>(exception)) {
            const auto &ref = std::get<JniGlobalRef<jthrowable>>(exception);
            auto env = JniEnv::from_thread();

            return env->IsInstanceOf(ref.get(), IO_EXCEPTION_CLASS.get(env).get());
        }

        return false;
    }
} // namespace ujr
