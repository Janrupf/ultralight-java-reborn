#pragma once

#include "ujr/support/GC.hpp"

#include <JavaScriptCore/JSContextRef.h>

namespace ujr {
    class JSGlobalContext {
    public:
        explicit JSGlobalContext() = delete;

        static JniLocalRef<jobject> wrap(const JniEnv &env, JSGlobalContextRef context);
    };

    class JSGlobalContextCollector : public NativeCollector {
    private:
        JSGlobalContextRef context;

    public:
        explicit JSGlobalContextCollector(JSGlobalContextRef context);

        void collect() final;
    };
}

