#include "net_janrupf_ujr_api_config_UlConfig_native_access.hpp"
#include "net_janrupf_ujr_api_config_UlFaceWinding_native_access.hpp"
#include "net_janrupf_ujr_api_config_UlFontHinting_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlPlatform.h"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlPlatform_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_clipboard_JNIUlClipboardNative_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_filesystem_JNIUlFilesystemNative_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_logger_JNIUlLoggerNative_native_access.hpp"

#include <AppCore/Platform.h>
#include <Ultralight/platform/Config.h>
#include <Ultralight/platform/FileSystem.h>
#include <Ultralight/platform/Logger.h>
#include <Ultralight/platform/Platform.h>

#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/wrapper/clipboard/Clipboard.hpp"
#include "ujr/wrapper/filesystem/Filesystem.hpp"
#include "ujr/wrapper/logger/Logger.hpp"

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlPlatform_nativeSetConfig(JNIEnv *env, jobject self, jobject config) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::UlConfig;
        using ujr::native_access::UlFaceWinding;
        using ujr::native_access::UlFontHinting;
        using ujr::native_access::JNIUlPlatform;

        // Check that the config object is not null
        env.wrap_argument(config).require_non_null_argument("config");

        // Begin the translation
        ultralight::Config native_config;

        native_config.cache_path
            = UlConfig::CACHE_PATH.get(env, config).require_non_null_argument("config.cachePath").to_utf16();
        native_config.resource_path_prefix = UlConfig::RESOURCE_PATH_PREFIX.get(env, config)
                                                 .require_non_null_argument("config.resourcePathPrefix")
                                                 .to_utf16();

        auto face_winding = UlConfig::FACE_WINDING.get(env, config).require_non_null_argument("config.faceWinding");
        if (face_winding == UlFaceWinding::CLOCKWISE.get(env)) {
            native_config.face_winding = ultralight::FaceWinding::Clockwise;
        } else if (face_winding == UlFaceWinding::COUNTER_CLOCKWISE.get(env)) {
            native_config.face_winding = ultralight::FaceWinding::CounterClockwise;
        } else {
            throw std::runtime_error("Unexpected face winding value");
        }

        auto font_hinting = UlConfig::FONT_HINTING.get(env, config).require_non_null_argument("config.fontHinting");
        if (font_hinting == UlFontHinting::SMOOTH.get(env)) {
            native_config.font_hinting = ultralight::FontHinting::Smooth;
        } else if (font_hinting == UlFontHinting::NORMAL.get(env)) {
            native_config.font_hinting = ultralight::FontHinting::Normal;
        } else if (font_hinting == UlFontHinting::MONOCHROME.get(env)) {
            native_config.font_hinting = ultralight::FontHinting::Monochrome;
        } else if (font_hinting == UlFontHinting::NONE.get(env)) {
            native_config.font_hinting = ultralight::FontHinting::None;
        } else {
            throw std::runtime_error("Unexpected font hinting value");
        }

        native_config.font_gamma = UlConfig::FONT_GAMMA.get(env, config);
        native_config.user_stylesheet
            = UlConfig::USER_STYLESHEET.get(env, config).require_non_null_argument("config.userStylesheet").to_utf16();
        native_config.force_repaint = UlConfig::FORCE_REPAINT.get(env, config);
        native_config.animation_timer_delay = UlConfig::ANIMATION_TIMER_DELAY.get(env, config);
        native_config.scroll_timer_delay = UlConfig::SCROLL_TIMER_DELAY.get(env, config);
        native_config.recycle_delay = UlConfig::RECYCLE_DELAY.get(env, config);
        native_config.memory_cache_size = UlConfig::MEMORY_CACHE_SIZE.get(env, config);
        native_config.page_cache_size = UlConfig::PAGE_CACHE_SIZE.get(env, config);
        native_config.override_ram_size = UlConfig::OVERRIDE_RAM_SIZE.get(env, config);
        native_config.min_large_heap_size = UlConfig::MIN_LARGE_HEAP_SIZE.get(env, config);
        native_config.min_small_heap_size = UlConfig::MIN_SMALL_HEAP_SIZE.get(env, config);
        native_config.num_renderer_threads = UlConfig::NUM_RENDERER_THREADS.get(env, config);
        native_config.max_update_time = UlConfig::MAX_UPDATE_TIME.get(env, config);
        native_config.bitmap_alignment = UlConfig::BITMAP_ALIGNMENT.get(env, config);

        reinterpret_cast<ultralight::Platform *>(JNIUlPlatform::HANDLE.get(env, self))->set_config(native_config);
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlPlatform_nativeUsePlatformFontLoader(JNIEnv *env, jobject self) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlPlatform;

        auto *platform = reinterpret_cast<ultralight::Platform *>(JNIUlPlatform::HANDLE.get(env, self));
        platform->set_font_loader(ultralight::GetPlatformFontLoader());
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlPlatform_nativeSetLogger(JNIEnv *env, jobject self, jobject logger) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlPlatform;

        auto *existing_native_logger = reinterpret_cast<ultralight::Logger *>(JNIUlPlatform::LOGGER.get(env, self));
        // Clear the field in case an exception is thrown before we set it again
        JNIUlPlatform::LOGGER.set(env, self, 0);

        delete existing_native_logger;

        auto j_logger = env.wrap_argument(logger).clone_as_global();
        auto *platform = reinterpret_cast<ultralight::Platform *>(JNIUlPlatform::HANDLE.get(env, self));

        auto *new_native_logger = new ujr::Logger(std::move(j_logger));
        platform->set_logger(new_native_logger);

        // Set the native logger field
        JNIUlPlatform::LOGGER.set(env, self, reinterpret_cast<jlong>(new_native_logger));
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlPlatform_nativeGetLogger(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) -> jobject {
        using ujr::native_access::JNIUlPlatform;
        using ujr::native_access::JNIUlLoggerNative;

        auto *existing_native_logger = reinterpret_cast<ultralight::Logger *>(JNIUlPlatform::LOGGER.get(env, self));
        if (!existing_native_logger) {
            // No logger set, return null
            return nullptr;
        }

        // Test if the logger is a JNI logger
        auto *jni_logger = dynamic_cast<ujr::Logger *>(existing_native_logger);
        if (jni_logger) {
            // It is, we can return the Java logger
            return jni_logger->get_j_logger().get();
        }

        // We have to construct a Java logger wrapper
        auto jni_logger_ref = JNIUlLoggerNative::CLAZZ.alloc_object(env);
        JNIUlLoggerNative::HANDLE.set(env, jni_logger_ref, reinterpret_cast<jlong>(existing_native_logger));

        return jni_logger_ref.leak();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_impl_JNIUlPlatform_nativeSetFilesystem(
    JNIEnv *env, jobject self, jobject filesystem
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlPlatform;

        auto *existing_native_filesystem
            = reinterpret_cast<ultralight::FileSystem *>(JNIUlPlatform::FILESYSTEM.get(env, self));

        // Clear the field in case an exception is thrown before we set it again
        JNIUlPlatform::FILESYSTEM.set(env, self, 0);

        delete existing_native_filesystem;

        auto j_filesystem = env.wrap_argument(filesystem).clone_as_global();
        auto *platform = reinterpret_cast<ultralight::Platform *>(JNIUlPlatform::HANDLE.get(env, self));

        auto *new_native_filesystem = new ujr::Filesystem(std::move(j_filesystem));
        platform->set_file_system(new_native_filesystem);

        // Set the native filesystem field
        JNIUlPlatform::FILESYSTEM.set(env, self, reinterpret_cast<jlong>(new_native_filesystem));
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlPlatform_nativeGetFilesystem(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) -> jobject {
        using ujr::native_access::JNIUlPlatform;
        using ujr::native_access::JNIUlFilesystemNative;

        auto *existing_native_filesystem
            = reinterpret_cast<ultralight::FileSystem *>(JNIUlPlatform::FILESYSTEM.get(env, self));
        if (!existing_native_filesystem) {
            // No filesystem set, return null
            return nullptr;
        }

        // Test if the filesystem is a JNI filesystem
        auto *jni_filesystem = dynamic_cast<ujr::Filesystem *>(existing_native_filesystem);
        if (jni_filesystem) {
            // It is, we can return the Java filesystem
            return jni_filesystem->get_j_filesystem().get();
        }

        // We have to construct a Java filesystem wrapper
        auto jni_filesystem_ref = JNIUlFilesystemNative::CLAZZ.alloc_object(env);
        JNIUlFilesystemNative::HANDLE.set(env, jni_filesystem_ref, reinterpret_cast<jlong>(existing_native_filesystem));

        return jni_filesystem_ref.leak();
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlPlatform_nativeSetClipboard(JNIEnv *env, jobject self, jobject clipboard) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlPlatform;

        auto *existing_native_clipboard
            = reinterpret_cast<ultralight::Clipboard *>(JNIUlPlatform::CLIPBOARD.get(env, self));

        // Clear the field in case an exception is thrown before we set it again
        JNIUlPlatform::CLIPBOARD.set(env, self, 0);

        delete existing_native_clipboard;

        auto j_clipboard = env.wrap_argument(clipboard).clone_as_global();
        auto *platform = reinterpret_cast<ultralight::Platform *>(JNIUlPlatform::HANDLE.get(env, self));

        auto *new_native_clipboard = new ujr::Clipboard(std::move(j_clipboard));
        platform->set_clipboard(new_native_clipboard);

        // Set the native clipboard field
        JNIUlPlatform::CLIPBOARD.set(env, self, reinterpret_cast<jlong>(new_native_clipboard));
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_impl_JNIUlPlatform_nativeGetClipboard(JNIEnv *env, jobject self) {
    return ujr::jni_entry_guard(env, [&](auto env) -> jobject {
        using ujr::native_access::JNIUlPlatform;
        using ujr::native_access::JNIUlClipboardNative;

        auto *existing_native_clipboard
            = reinterpret_cast<ultralight::Clipboard *>(JNIUlPlatform::CLIPBOARD.get(env, self));
        if (!existing_native_clipboard) {
            // No clipboard set, return null
            return nullptr;
        }

        // Test if the clipboard is a JNI clipboard
        auto *jni_clipboard = dynamic_cast<ujr::Clipboard *>(existing_native_clipboard);
        if (jni_clipboard) {
            // It is, we can return the Java clipboard
            return jni_clipboard->get_j_clipboard().get();
        }

        // We have to construct a Java clipboard wrapper
        auto jni_clipboard_ref = JNIUlClipboardNative::CLAZZ.alloc_object(env);
        JNIUlClipboardNative::HANDLE.set(env, jni_clipboard_ref, reinterpret_cast<jlong>(existing_native_clipboard));

        return jni_clipboard_ref.leak();
    });
}
