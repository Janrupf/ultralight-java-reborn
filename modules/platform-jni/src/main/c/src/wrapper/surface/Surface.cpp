#include "ujr/wrapper/surface/Surface.hpp"
#include "net_janrupf_ujr_api_math_IntRect_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_buffer_JNIUlDelegatedBuffer_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurface_native_access.hpp"

#include "ujr/wrapper/buffer/Buffer.hpp"

namespace ujr {
    Surface::Surface(JniGlobalRef<jobject> j_surface)
        : j_surface(std::move(j_surface))
        , j_locked_pixels(JniGlobalRef<jobject>::null())
        , locked_pixels(nullptr) {}

    const JniGlobalRef<jobject> &Surface::get_j_surface() const { return j_surface; }

    uint32_t Surface::width() const {
        auto env = JniEnv::require_existing_from_thread();

        return static_cast<uint32_t>(native_access::JNIUlSurface::WIDTH.invoke(env, j_surface));
    }

    uint32_t Surface::height() const {
        auto env = JniEnv::require_existing_from_thread();

        return static_cast<uint32_t>(native_access::JNIUlSurface::HEIGHT.invoke(env, j_surface));
    }

    uint32_t Surface::row_bytes() const {
        auto env = JniEnv::require_existing_from_thread();

        return static_cast<uint32_t>(native_access::JNIUlSurface::ROW_BYTES.invoke(env, j_surface));
    }

    size_t Surface::size() const {
        auto env = JniEnv::require_existing_from_thread();

        return static_cast<size_t>(native_access::JNIUlSurface::SIZE.invoke(env, j_surface));
    }

    void *Surface::LockPixels() {
        auto env = JniEnv::require_existing_from_thread();

        j_locked_pixels = native_access::JNIUlSurface::LOCK_PIXELS.invoke(env, j_surface).clone_as_global();
        locked_pixels = Buffer::wrap_delegated_buffer(env, j_locked_pixels, true);

        return locked_pixels->data();
    }

    void Surface::UnlockPixels() {
        auto env = JniEnv::require_existing_from_thread();

        if (j_locked_pixels.is_null(env)) {
            return; // TODO: Throw exception
        }

        locked_pixels.reset();
        native_access::JNIUlDelegatedBuffer::RELEASE.invoke(env, j_locked_pixels);
        j_locked_pixels = JniGlobalRef<jobject>::null();
    }

    void Surface::Resize(uint32_t width, uint32_t height) {
        auto env = JniEnv::require_existing_from_thread();

        native_access::JNIUlSurface::RESIZE
            .invoke(env, j_surface, static_cast<jlong>(width), static_cast<jlong>(height));
    }

    void Surface::set_dirty_bounds(const ultralight::IntRect &bounds) {
        auto env = JniEnv::require_existing_from_thread();

        auto j_int_rect = native_access::IntRect::CLAZZ.alloc_object(env);
        native_access::IntRect::LEFT.set(env, j_int_rect, static_cast<jlong>(bounds.left));
        native_access::IntRect::TOP.set(env, j_int_rect, static_cast<jlong>(bounds.top));
        native_access::IntRect::RIGHT.set(env, j_int_rect, static_cast<jlong>(bounds.right));
        native_access::IntRect::BOTTOM.set(env, j_int_rect, static_cast<jlong>(bounds.bottom));

        native_access::JNIUlSurface::SET_DIRTY_BOUNDS.invoke(env, j_surface, j_int_rect);
    }

    ultralight::IntRect Surface::dirty_bounds() const {
        auto env = JniEnv::require_existing_from_thread();

        auto j_int_rect = native_access::JNIUlSurface::DIRTY_BOUNDS.invoke(env, j_surface);

        return ultralight::IntRect { static_cast<int32_t>(native_access::IntRect::LEFT.get(env, j_int_rect)),
                                     static_cast<int32_t>(native_access::IntRect::TOP.get(env, j_int_rect)),
                                     static_cast<int32_t>(native_access::IntRect::RIGHT.get(env, j_int_rect)),
                                     static_cast<int32_t>(native_access::IntRect::BOTTOM.get(env, j_int_rect)) };
    }

    void Surface::ClearDirtyBounds() {
        auto env = JniEnv::require_existing_from_thread();

        native_access::JNIUlSurface::CLEAR_DIRTY_BOUNDS.invoke(env, j_surface);
    }

} // namespace ujr