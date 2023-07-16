#pragma once

#include "ujr/support/GC.hpp"

#include <JavaScriptCore/JSContextRef.h>

namespace ujr {
    class JSContext {
    public:
        explicit JSContext() = delete;

        static JniLocalRef<jobject> wrap(const JniEnv &env, JSContextRef context);
    };
}
