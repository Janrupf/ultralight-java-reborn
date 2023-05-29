#pragma once

#include "ujr/support/GC.hpp"

#include <JavaScriptCore/JSContextRef.h>

namespace ujr {
    class JSCContext {
    public:
        explicit JSCContext() = delete;

        static JniLocalRef<jobject> wrap(const JniEnv &env, JSContextRef context);
    };
}
