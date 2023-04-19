#include "ujr/util/JniExceptionCheck.hpp"

#include "ujr/util/JniException.hpp"
#include "ujr/util/JniIllegalArgumentException.hpp"

namespace ujr {
    void JniExceptionCheck::throw_if_pending(const JniEnv &env) { JniException::throw_if_pending(env); }

    void JniExceptionCheck::throw_illegal_argument(std::string argument, std::string message) {
        throw JniIllegalArgumentException(std::move(argument), std::move(message));
    }
} // namespace ujr