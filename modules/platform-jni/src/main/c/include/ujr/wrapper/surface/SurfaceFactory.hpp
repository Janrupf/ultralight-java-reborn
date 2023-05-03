#pragma once

#include <Ultralight/platform/Surface.h>

#include "ujr/util/JniRef.hpp"

namespace ujr {
    class SurfaceFactory : public ultralight::SurfaceFactory {
    private:
        JniGlobalRef<jobject> j_surface_factory;

    public:
        explicit SurfaceFactory(JniGlobalRef<jobject> j_surface_factory);

        ultralight::Surface *CreateSurface(uint32_t width, uint32_t height) final;

        void DestroySurface(ultralight::Surface *surface) final;

        /**
         * Retrieves the underlying java surface factory instance.
         *
         * @return the java surface factory instance
         */
        [[nodiscard]] const JniGlobalRef<jobject> &get_j_surface_factory() const;
    };
} // namespace ujr
