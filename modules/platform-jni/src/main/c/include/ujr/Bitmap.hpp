#pragma once

#include <Ultralight/Bitmap.h>

#include "ujr/support/GC.hpp"

namespace ujr {
    class BitmapCollector : public NativeCollector {
    private:
        ultralight::Bitmap *bitmap;

    public:
        explicit BitmapCollector(ultralight::Bitmap *bitmap);

        void collect() final;
    };
} // namespace ujr
