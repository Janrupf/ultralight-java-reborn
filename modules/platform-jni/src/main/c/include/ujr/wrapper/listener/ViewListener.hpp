#pragma once

#include <Ultralight/Listener.h>

#include "ujr/util/JniRef.hpp"

namespace ujr {
    /**
     * Listener adapter for Ultralight delegating to a Java instance.
     */
    class ViewListener : public ultralight::ViewListener {
    private:
        JniGlobalRef<jobject> j_listener;

    public:
        explicit ViewListener(JniGlobalRef<jobject> j_listener);

        void OnChangeTitle(ultralight::View *caller, const ultralight::String &title) final;

        void OnChangeURL(ultralight::View *caller, const ultralight::String &url) final;

        void OnChangeTooltip(ultralight::View *caller, const ultralight::String &tooltip) final;

        void OnChangeCursor(ultralight::View *caller, ultralight::Cursor cursor) final;

        void OnAddConsoleMessage(
            ultralight::View *caller,
            ultralight::MessageSource source,
            ultralight::MessageLevel level,
            const ultralight::String &message,
            uint32_t line_number,
            uint32_t column_number,
            const ultralight::String &source_id
        ) final;

        ultralight::RefPtr<ultralight::View> OnCreateChildView(
            ultralight::View *caller,
            const ultralight::String &opener_url,
            const ultralight::String &target_url,
            bool is_popup,
            const ultralight::IntRect &popup_rect
        ) final;

        ultralight::RefPtr<ultralight::View>
        OnCreateInspectorView(ultralight::View *caller, bool is_local, const ultralight::String &inspected_url) final;

        void OnRequestClose(ultralight::View *caller) final;

        /**
         * Retrieves the underlying java listener instance.
         *
         * @return the java listener instance
         */
        [[nodiscard]] const JniGlobalRef<jobject> &get_j_listener() const;
    };
} // namespace ujr
