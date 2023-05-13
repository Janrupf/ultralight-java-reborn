#pragma once

#include <Ultralight/platform/Clipboard.h>
#include <Ultralight/platform/FileSystem.h>
#include <Ultralight/platform/Logger.h>
#include <Ultralight/platform/Platform.h>

#include "ujr/support/GC.hpp"

namespace ujr {
    class Platform {
    public:
        explicit Platform() = delete;

        static JniLocalRef<jobject> wrap(const JniEnv &env, ultralight::Platform &platform);
    };

    class PlatformCollector : public NativeCollector {
    public:
        ultralight::Logger *logger;
        ultralight::FileSystem *filesystem;
        ultralight::Clipboard *clipboard;
        ultralight::SurfaceFactory *surface_factory;

        explicit PlatformCollector();

        void collect() final;
    };
} // namespace ujr
