#pragma once

#include "ujr/support/GC.hpp"

#include <JavaScriptCore/JSContextRef.h>

namespace ujr {
    class JSContextGroup {
    public:
        explicit JSContextGroup() = delete;

        static JniLocalRef<jobject> wrap(const JniEnv &env, JSContextGroupRef group);
    };

    class JSContextGroupCollector : public NativeCollector {
    private:
        JSContextGroupRef group;

    public:
        explicit JSContextGroupCollector(JSContextGroupRef group);

        void collect() final;
    };
}
