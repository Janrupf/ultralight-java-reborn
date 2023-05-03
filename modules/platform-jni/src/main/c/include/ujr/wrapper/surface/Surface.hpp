#pragma once

#include <Ultralight/platform/Surface.h>
#include <Ultralight/Buffer.h>

#include "ujr/util/JniRef.hpp"

namespace ujr {
    class Surface : public ultralight::Surface {
    private:
        JniGlobalRef<jobject> j_surface;
        JniGlobalRef<jobject> j_locked_pixels;
        ultralight::RefPtr<ultralight::Buffer> locked_pixels;

    public:
        explicit Surface(JniGlobalRef<jobject> j_surface);

        [[nodiscard]] uint32_t width() const final;

        [[nodiscard]] uint32_t height() const final;

        [[nodiscard]] uint32_t row_bytes() const final;

        [[nodiscard]] size_t size() const final;

        void *LockPixels() final;

        void UnlockPixels() final;

        void Resize(uint32_t width, uint32_t height) final;

        void set_dirty_bounds(const ultralight::IntRect &bounds) final;

        [[nodiscard]] ultralight::IntRect dirty_bounds() const final;

        void ClearDirtyBounds() final;

        /**
         * Retrieves the underlying java surface instance.
         *
         * @return the java surface instance
         */
        [[nodiscard]] const JniGlobalRef<jobject> &get_j_surface() const;
    };
} // namespace ujr
