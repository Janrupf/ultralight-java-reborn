#pragma once

#include <JavaScriptCore/JSObjectRef.h>

#include "ujr/support/GC.hpp"
#include "ujr/util/JniEnv.hpp"

namespace ujr {
    class JSClass {
    public:
        explicit JSClass() = delete;

        static JniLocalRef<jobject> wrap(const JniEnv &env, JSClassRef clazz);
    };

    class JSClassCollector : public NativeCollector {
    private:
        JSClassRef clazz;

    public:
        explicit JSClassCollector(JSClassRef clazz);

        void collect() final;
    };

} // namespace ujr
