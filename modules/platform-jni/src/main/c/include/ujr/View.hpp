#pragma once

#include <Ultralight/View.h>

#include "ujr/support/GC.hpp"

namespace ujr {
    class ViewCollector : public NativeCollector {
    private:
        ultralight::View *view;

    public:
        explicit ViewCollector(ultralight::View *view);

        void collect() final;
    };
} // namespace ujr