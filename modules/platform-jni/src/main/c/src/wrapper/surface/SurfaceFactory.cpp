#include "ujr/wrapper/surface/SurfaceFactory.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_surface_JNIUlSurfaceFactory_native_access.hpp"

#include "ujr/wrapper/surface/Surface.hpp"

namespace ujr {
    SurfaceFactory::SurfaceFactory(JniGlobalRef<jobject> j_surface_factory)
        : j_surface_factory(std::move(j_surface_factory)) {}

    ultralight::Surface *SurfaceFactory::CreateSurface(uint32_t width, uint32_t height) {
        auto env = JniEnv::require_existing_from_thread();

        auto j_surface = native_access::JNIUlSurfaceFactory::CREATE_SURFACE
                             .invoke(env, j_surface_factory, static_cast<jlong>(width), static_cast<jlong>(height));

        return new Surface(j_surface.clone_as_global());
    }

    void SurfaceFactory::DestroySurface(ultralight::Surface *surface) {
        auto env = JniEnv::require_existing_from_thread();

        auto *ujr_surface = dynamic_cast<Surface *>(surface);
        if (ujr_surface) {
            native_access::JNIUlSurfaceFactory::DESTROY_SURFACE
                .invoke(env, j_surface_factory, ujr_surface->get_j_surface().get());
        }

        delete surface;
    }

    const JniGlobalRef<jobject> &SurfaceFactory::get_j_surface_factory() const { return j_surface_factory; }
} // namespace ujr