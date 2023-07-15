#pragma once

#include <JavaScriptCore/JSContextRef.h>
#include <JavaScriptCore/JSValueRef.h>

#include "ujr/support/GC.hpp"
#include "ujr/util/JniRef.hpp"

namespace ujr {
    class JSObject {
    public:
        explicit JSObject() = delete;

        static JniLocalRef<jobject> wrap(const JniEnv &env, JSContextRef context, JSObjectRef value);
    };
} // namespace ujr
