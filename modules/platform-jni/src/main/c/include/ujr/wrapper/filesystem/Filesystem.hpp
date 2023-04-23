#pragma once

#include <Ultralight/platform/FileSystem.h>

#include "ujr/util/JniRef.hpp"

namespace ujr {
    /**
     * Filesystem adapter for Ultralight delegating to a Java instance.
     */
    class Filesystem : public ultralight::FileSystem {
    private:
        JniGlobalRef<jobject> j_filesystem;

    public:
        explicit Filesystem(JniGlobalRef<jobject> j_filesystem);

        bool FileExists(const ultralight::String &file_path) final;

        ultralight::String GetFileMimeType(const ultralight::String &file_path) final;

        ultralight::String GetFileCharset(const ultralight::String &file_path) final;

        ultralight::RefPtr<ultralight::Buffer> OpenFile(const ultralight::String &file_path) final;

        /**
         * Retrieves the underlying java filesystem instance.
         *
         * @return the java filesystem instance
         */
        [[nodiscard]] const JniGlobalRef<jobject> &get_j_filesystem() const;
    };
} // namespace ujr