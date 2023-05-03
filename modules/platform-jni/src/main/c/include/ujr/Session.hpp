#pragma once

#include <Ultralight/Session.h>

#include "ujr/support/GC.hpp"

namespace ujr {
    class SessionCollector : public NativeCollector {
    public:
        ultralight::Session *session;

        explicit SessionCollector(ultralight::Session *session);

        void collect() final;
    };
} // namespace ujr
