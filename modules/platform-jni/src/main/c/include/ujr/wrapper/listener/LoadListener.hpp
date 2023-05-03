#pragma once

#include <Ultralight/Listener.h>

#include "ujr/util/JniRef.hpp"

namespace ujr {
    /**
     * Listener adapter for Ultralight delegating to a Java instance.
     */
    class LoadListener : public ultralight::LoadListener {
    private:
        JniGlobalRef<jobject> j_listener;

    public:
        explicit LoadListener(JniGlobalRef<jobject> j_listener);

        void OnBeginLoading(
            ultralight::View *caller, uint64_t frame_id, bool is_main_frame, const ultralight::String &url
        ) final;

        void OnFinishLoading(
            ultralight::View *caller, uint64_t frame_id, bool is_main_frame, const ultralight::String &url
        ) final;

        void OnFailLoading(
            ultralight::View *caller,
            uint64_t frame_id,
            bool is_main_frame,
            const ultralight::String &url,
            const ultralight::String &description,
            const ultralight::String &error_domain,
            int error_code
        ) final;

        void OnWindowObjectReady(
            ultralight::View *caller, uint64_t frame_id, bool is_main_frame, const ultralight::String &url
        ) final;

        void OnDOMReady(ultralight::View *caller, uint64_t frame_id, bool is_main_frame, const ultralight::String &url)
            final;

        void OnUpdateHistory(ultralight::View *caller) final;

        /**
         * Retrieves the underlying java listener instance.
         *
         * @return the java listener instance
         */
        [[nodiscard]] const JniGlobalRef<jobject> &get_j_listener() const;
    };
} // namespace ujr
