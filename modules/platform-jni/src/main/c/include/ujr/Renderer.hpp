#pragma once

#include <Ultralight/Renderer.h>

#include "ujr/support/GC.hpp"

namespace ujr {
    class Renderer {
    public:
        explicit Renderer() = delete;

        static JniLocalRef<jobject> wrap(const JniEnv &env, ultralight::RefPtr<ultralight::Renderer> renderer);
    };

    class RendererCollector : public NativeCollector {
    private:
        ultralight::Renderer *renderer;

    public:
        explicit RendererCollector(ultralight::Renderer *renderer);

        void collect() final;
    };
} // namespace ujr
