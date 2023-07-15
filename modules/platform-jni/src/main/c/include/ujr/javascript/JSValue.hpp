#pragma once

#include <JavaScriptCore/JSContextRef.h>
#include <JavaScriptCore/JSValueRef.h>

#include "ujr/support/GC.hpp"
#include "ujr/util/JniRef.hpp"

namespace ujr {
    class JSValue {
    public:
        explicit JSValue() = delete;

        static JniLocalRef<jobject> wrap(const JniEnv &env, JSContextRef context, JSValueRef value);
    };

    class JSValueCollector : public NativeCollector {
    private:
        JSGlobalContextRef context;
        JSValueRef value;

    public:
        explicit JSValueCollector(JSGlobalContextRef context, JSValueRef value);

        void collect() final;
    };
} // namespace ujr
