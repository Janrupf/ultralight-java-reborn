#pragma once

#include <Ultralight/platform/Clipboard.h>
#include <Ultralight/platform/FileSystem.h>
#include <Ultralight/platform/Logger.h>

#include "ujr/support/GC.hpp"

namespace ujr {
    class PlatformCollector : public NativeCollector {
    public:
        ultralight::Logger *logger;
        ultralight::FileSystem *filesystem;
        ultralight::Clipboard *clipboard;

        explicit PlatformCollector();

        void collect() final;
    };
} // namespace ujr
