#pragma once

#include <Ultralight/Session.h>

#include "ujr/support/GC.hpp"

namespace ujr {
    class Session {
    public:
        explicit Session() = delete;

        static JniLocalRef<jobject> wrap(const JniEnv &env, ultralight::RefPtr<ultralight::Session> session);
    };

    class SessionCollector : public NativeCollector {
    public:
        ultralight::Session *session;

        explicit SessionCollector(ultralight::Session *session);

        void collect() final;
    };
} // namespace ujr
