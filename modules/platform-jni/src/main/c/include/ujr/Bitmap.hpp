#pragma once

#include <Ultralight/Bitmap.h>

#include "ujr/support/GC.hpp"
#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniRef.hpp"

namespace ujr {
    class Bitmap {
    public:
        explicit Bitmap() = delete;

        static JniLocalRef<jobject> wrap(const JniEnv &env, ultralight::RefPtr<ultralight::Bitmap> bitmap);
    };

    class BitmapCollector : public NativeCollector {
    private:
        ultralight::Bitmap *bitmap;

    public:
        explicit BitmapCollector(ultralight::Bitmap *bitmap);

        void collect() final;
    };
} // namespace ujr
