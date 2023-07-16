#include "ujr/util/JniIllegalArgumentException.hpp"

#include "ujr/util/JniClass.hpp"
#include "ujr/util/JniMethod.hpp"

namespace ujr {
    static JniClass<"java/lang/IllegalArgumentException", jthrowable> ILLEGAL_ARGUMENT_EXCEPTION_CLASS;
    static JniConstructor<decltype(ILLEGAL_ARGUMENT_EXCEPTION_CLASS), jstring>
        ILLEGAL_ARGUMENT_EXCEPTION_CONSTRUCTOR(ILLEGAL_ARGUMENT_EXCEPTION_CLASS);

    JniIllegalArgumentException::JniIllegalArgumentException(std::string argument, std::string message)
        : argument(std::move(argument))
        , message(std::move(message)) {}

    JniGlobalRef<jthrowable> JniIllegalArgumentException::translate_to_java() const {
        auto env = JniEnv::require_existing_from_thread();

        auto final_message = this->argument + ": " + this->message;
        auto j_message = JniLocalRef<jstring>::from_utf8(env, final_message.c_str());

        return ILLEGAL_ARGUMENT_EXCEPTION_CONSTRUCTOR.invoke(env, j_message).clone_as_global();
    }
} // namespace ujr