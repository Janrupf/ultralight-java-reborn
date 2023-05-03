#pragma once

#include <Ultralight/View.h>

#include "ujr/support/GC.hpp"

namespace ujr {
    class ViewCollector : public NativeCollector {
    private:
        ultralight::View *view;

    public:
        ultralight::ViewListener *view_listener;
        ultralight::LoadListener *load_listener;

        explicit ViewCollector(ultralight::View *view);

        void collect() final;
    };
} // namespace ujr
