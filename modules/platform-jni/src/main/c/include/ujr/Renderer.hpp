#pragma once

#include <Ultralight/Renderer.h>

#include "ujr/support/GC.hpp"

namespace ujr {
    class RendererCollector : public NativeCollector {
    private:
        ultralight::Renderer *renderer;

    public:
        explicit RendererCollector(ultralight::Renderer *renderer);

        void collect() final;
    };
} // namespace ujr
