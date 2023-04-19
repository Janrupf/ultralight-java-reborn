#include "ujr/util/JniException.hpp"

#include "ujr/util/JniClass.hpp"
#include "ujr/util/JniMethod.hpp"

namespace ujr {
    static JniClass<"net/janrupf/ujr/platform/jni/exception/CPPException", jthrowable> CPP_EXCEPTION_CLASS;
    static JniConstructor<decltype(CPP_EXCEPTION_CLASS), jstring> CPP_EXCEPTION_CONSTRUCTOR(CPP_EXCEPTION_CLASS);

    JniException::JniException()
        : exception(nullptr) {}

    JniException::JniException(std::variant<JniLocalRef<jthrowable>, std::exception_ptr> exception)
        : exception(std::move(exception)) {}

    bool JniException::rethrow(const JniEnv &env) const {
        if (env->ExceptionCheck()) {
            return false;
        }

        auto java = this->translate_to_java();
        java.do_throw();

        return true;
    }

    JniLocalRef<jthrowable> JniException::translate_to_java() const { // NOLINT(misc-no-recursion)
        if (std::holds_alternative<JniLocalRef<jthrowable>>(exception)) {
            // No translation necessary
            return std::get<JniLocalRef<jthrowable>>(exception);
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

                    return JniLocalRef<jthrowable>::wrap(
                        env,
                        reinterpret_cast<_jthrowable *>(
                            env->NewObject(runtime_exception_class, constructor_id, message.get())
                        )
                    );
                }

                // And finally construct the actual exception
                return CPP_EXCEPTION_CONSTRUCTOR.invoke(env, message);
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
            throw_java_as_cpp(std::move(exception_ref));
        }
    }

    void JniException::throw_java_as_cpp(JniLocalRef<jthrowable> exception) {
        // Rethrow the exception
        throw JniException::from_java(std::move(exception));
    }

    JniException JniException::from_cpp(std::exception_ptr exception) {
        return JniException(std::variant<JniLocalRef<jthrowable>, std::exception_ptr>(std::move(exception)));
    }

    JniException JniException::from_java(JniLocalRef<jthrowable> exception) {
        return JniException(std::variant<JniLocalRef<jthrowable>, std::exception_ptr>(std::move(exception)));
    }
} // namespace ujr
