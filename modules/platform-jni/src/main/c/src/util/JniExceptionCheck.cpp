#include "ujr/util/JniExceptionCheck.hpp"

#include "ujr/util/JniException.hpp"

namespace ujr {
    void JniExceptionCheck::throw_if_pending(const JniEnv &env) { JniException::throw_if_pending(env); }
} // namespace ujr