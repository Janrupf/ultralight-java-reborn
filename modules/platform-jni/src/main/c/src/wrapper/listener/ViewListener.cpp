#include "ujr/wrapper/listener/ViewListener.hpp"
#include "net_janrupf_ujr_api_cursor_UlCursor_native_access.hpp"
#include "net_janrupf_ujr_api_listener_UlMessageLevel_native_access.hpp"
#include "net_janrupf_ujr_api_listener_UlMessageSource_native_access.hpp"
#include "net_janrupf_ujr_api_math_IntRect_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlView_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlViewListener_native_access.hpp"

#include <Ultralight/View.h>

namespace ujr {
    ViewListener::ViewListener(JniGlobalRef<jobject> j_listener)
        : j_listener(std::move(j_listener)) {}

    void ViewListener::OnChangeTitle(ultralight::View *caller, const ultralight::String &title) {
        auto env = JniEnv::require_existing_from_thread();

        // Translate the title
        auto j_title = JniLocalRef<jstring>::from_utf16(env, title.utf16());

        // Call the Java instance
        native_access::JNIUlViewListener::ON_CHANGE_TITLE.invoke(env, j_listener, j_title);
    }

    void ViewListener::OnChangeURL(ultralight::View *caller, const ultralight::String &url) {
        auto env = JniEnv::require_existing_from_thread();

        // Translate the url
        auto j_url = JniLocalRef<jstring>::from_utf16(env, url.utf16());

        // Call the Java instance
        native_access::JNIUlViewListener::ON_CHANGE_URL.invoke(env, j_listener, j_url);
    }

    void ViewListener::OnChangeTooltip(ultralight::View *caller, const ultralight::String &tooltip) {
        auto env = JniEnv::require_existing_from_thread();

        // Translate the tooltip
        auto j_tooltip = JniLocalRef<jstring>::from_utf16(env, tooltip.utf16());

        // Call the Java instance
        native_access::JNIUlViewListener::ON_CHANGE_TOOLTIP.invoke(env, j_listener, j_tooltip);
    }

    void ViewListener::OnChangeCursor(ultralight::View *caller, ultralight::Cursor cursor) {
        using native_access::UlCursor;

        auto env = JniEnv::require_existing_from_thread();

        // Translate the cursor
        auto j_cursor = JniLocalRef<jobject>::null(env);
        switch (cursor) {
            case ultralight::kCursor_Pointer:
                j_cursor = UlCursor::POINTER.get(env);
                break;
            case ultralight::kCursor_Cross:
                j_cursor = UlCursor::CROSS.get(env);
                break;
            case ultralight::kCursor_Hand:
                j_cursor = UlCursor::HAND.get(env);
                break;
            case ultralight::kCursor_IBeam:
                j_cursor = UlCursor::I_BEAM.get(env);
                break;
            case ultralight::kCursor_Wait:
                j_cursor = UlCursor::WAIT.get(env);
                break;
            case ultralight::kCursor_Help:
                j_cursor = UlCursor::HELP.get(env);
                break;
            case ultralight::kCursor_EastResize:
                j_cursor = UlCursor::EAST_RESIZE.get(env);
                break;
            case ultralight::kCursor_NorthResize:
                j_cursor = UlCursor::NORTH_RESIZE.get(env);
                break;
            case ultralight::kCursor_NorthEastResize:
                j_cursor = UlCursor::NORTH_EAST_RESIZE.get(env);
                break;
            case ultralight::kCursor_NorthWestResize:
                j_cursor = UlCursor::NORTH_WEST_RESIZE.get(env);
                break;
            case ultralight::kCursor_SouthResize:
                j_cursor = UlCursor::SOUTH_RESIZE.get(env);
                break;
            case ultralight::kCursor_SouthEastResize:
                j_cursor = UlCursor::SOUTH_EAST_RESIZE.get(env);
                break;
            case ultralight::kCursor_SouthWestResize:
                j_cursor = UlCursor::SOUTH_WEST_RESIZE.get(env);
                break;
            case ultralight::kCursor_WestResize:
                j_cursor = UlCursor::WEST_RESIZE.get(env);
                break;
            case ultralight::kCursor_NorthSouthResize:
                j_cursor = UlCursor::NORTH_SOUTH_RESIZE.get(env);
                break;
            case ultralight::kCursor_EastWestResize:
                j_cursor = UlCursor::EAST_WEST_RESIZE.get(env);
                break;
            case ultralight::kCursor_NorthEastSouthWestResize:
                j_cursor = UlCursor::NORTH_EAST_SOUTH_WEST_RESIZE.get(env);
                break;
            case ultralight::kCursor_NorthWestSouthEastResize:
                j_cursor = UlCursor::NORTH_WEST_SOUTH_EAST_RESIZE.get(env);
                break;
            case ultralight::kCursor_ColumnResize:
                j_cursor = UlCursor::COLUMN_RESIZE.get(env);
                break;
            case ultralight::kCursor_RowResize:
                j_cursor = UlCursor::ROW_RESIZE.get(env);
                break;
            case ultralight::kCursor_MiddlePanning:
                j_cursor = UlCursor::MIDDLE_PANNING.get(env);
                break;
            case ultralight::kCursor_EastPanning:
                j_cursor = UlCursor::EAST_PANNING.get(env);
                break;
            case ultralight::kCursor_NorthPanning:
                j_cursor = UlCursor::NORTH_PANNING.get(env);
                break;
            case ultralight::kCursor_NorthEastPanning:
                j_cursor = UlCursor::NORTH_EAST_PANNING.get(env);
                break;
            case ultralight::kCursor_NorthWestPanning:
                j_cursor = UlCursor::NORTH_WEST_PANNING.get(env);
                break;
            case ultralight::kCursor_SouthPanning:
                j_cursor = UlCursor::SOUTH_PANNING.get(env);
                break;
            case ultralight::kCursor_SouthEastPanning:
                j_cursor = UlCursor::SOUTH_EAST_PANNING.get(env);
                break;
            case ultralight::kCursor_SouthWestPanning:
                j_cursor = UlCursor::SOUTH_WEST_PANNING.get(env);
                break;
            case ultralight::kCursor_WestPanning:
                j_cursor = UlCursor::WEST_PANNING.get(env);
                break;
            case ultralight::kCursor_Move:
                j_cursor = UlCursor::MOVE.get(env);
                break;
            case ultralight::kCursor_VerticalText:
                j_cursor = UlCursor::VERTICAL_TEXT.get(env);
                break;
            case ultralight::kCursor_Cell:
                j_cursor = UlCursor::CELL.get(env);
                break;
            case ultralight::kCursor_ContextMenu:
                j_cursor = UlCursor::CONTEXT_MENU.get(env);
                break;
            case ultralight::kCursor_Alias:
                j_cursor = UlCursor::ALIAS.get(env);
                break;
            case ultralight::kCursor_Progress:
                j_cursor = UlCursor::PROGRESS.get(env);
                break;
            case ultralight::kCursor_NoDrop:
                j_cursor = UlCursor::NO_DROP.get(env);
                break;
            case ultralight::kCursor_Copy:
                j_cursor = UlCursor::COPY.get(env);
                break;
            case ultralight::kCursor_None:
                j_cursor = UlCursor::NONE.get(env);
                break;
            case ultralight::kCursor_NotAllowed:
                j_cursor = UlCursor::NOT_ALLOWED.get(env);
                break;
            case ultralight::kCursor_ZoomIn:
                j_cursor = UlCursor::ZOOM_IN.get(env);
                break;
            case ultralight::kCursor_ZoomOut:
                j_cursor = UlCursor::ZOOM_OUT.get(env);
                break;
            case ultralight::kCursor_Grab:
                j_cursor = UlCursor::GRAB.get(env);
                break;
            case ultralight::kCursor_Grabbing:
                j_cursor = UlCursor::GRABBING.get(env);
                break;
            case ultralight::kCursor_Custom:
                j_cursor = UlCursor::CUSTOM.get(env);
                break;
        }

        // Call the Java instance
        native_access::JNIUlViewListener::ON_CHANGE_CURSOR.invoke(env, j_listener, j_cursor);
    }

    void ViewListener::OnAddConsoleMessage(
        ultralight::View *caller,
        ultralight::MessageSource source,
        ultralight::MessageLevel level,
        const ultralight::String &message,
        uint32_t line_number,
        uint32_t column_number,
        const ultralight::String &source_id
    ) {
        using native_access::UlMessageLevel;
        using native_access::UlMessageSource;

        auto env = JniEnv::require_existing_from_thread();

        // Translate the source
        auto j_source = JniLocalRef<jobject>::null(env);

        switch (source) {
            case ultralight::kMessageSource_XML:
                j_source = UlMessageSource::XML.get(env);
                break;
            case ultralight::kMessageSource_JS:
                j_source = UlMessageSource::JS.get(env);
                break;
            case ultralight::kMessageSource_Network:
                j_source = UlMessageSource::NETWORK.get(env);
                break;
            case ultralight::kMessageSource_ConsoleAPI:
                j_source = UlMessageSource::CONSOLE_API.get(env);
                break;
            case ultralight::kMessageSource_Storage:
                j_source = UlMessageSource::STORAGE.get(env);
                break;
            case ultralight::kMessageSource_AppCache:
                j_source = UlMessageSource::APP_CACHE.get(env);
                break;
            case ultralight::kMessageSource_Rendering:
                j_source = UlMessageSource::RENDERING.get(env);
                break;
            case ultralight::kMessageSource_CSS:
                j_source = UlMessageSource::CSS.get(env);
                break;
            case ultralight::kMessageSource_Security:
                j_source = UlMessageSource::SECURITY.get(env);
                break;
            case ultralight::kMessageSource_ContentBlocker:
                j_source = UlMessageSource::CONTENT_BLOCKER.get(env);
                break;
            case ultralight::kMessageSource_Other:
                j_source = UlMessageSource::OTHER.get(env);
                break;
        }

        // Translate the level
        auto j_level = JniLocalRef<jobject>::null(env);
        switch (level) {
            case ultralight::kMessageLevel_Log:
                j_level = UlMessageLevel::LOG.get(env);
                break;
            case ultralight::kMessageLevel_Warning:
                j_level = UlMessageLevel::WARNING.get(env);
                break;
            case ultralight::kMessageLevel_Error:
                j_level = UlMessageLevel::ERROR.get(env);
                break;
            case ultralight::kMessageLevel_Debug:
                j_level = UlMessageLevel::DEBUG.get(env);
                break;
            case ultralight::kMessageLevel_Info:
                j_level = UlMessageLevel::INFO.get(env);
                break;
        }

        // Translate the message and source id
        auto j_message = JniLocalRef<jstring>::from_utf16(env, message.utf16());
        auto j_source_id = JniLocalRef<jstring>::from_utf16(env, source_id.utf16());

        // Call the Java instance
        native_access::JNIUlViewListener::ON_ADD_CONSOLE_MESSAGE.invoke(
            env,
            j_listener,
            j_source,
            j_level,
            j_message,
            static_cast<jlong>(line_number),
            static_cast<jlong>(column_number),
            j_source_id
        );
    }

    ultralight::RefPtr<ultralight::View> ViewListener::OnCreateChildView(
        ultralight::View *caller,
        const ultralight::String &opener_url,
        const ultralight::String &target_url,
        bool is_popup,
        const ultralight::IntRect &popup_rect
    ) {
        using native_access::IntRect;
        using native_access::JNIUlView;
        using native_access::JNIUlViewListener;

        auto env = JniEnv::require_existing_from_thread();

        // Translate the opener url
        auto j_opener_url = JniLocalRef<jstring>::from_utf16(env, opener_url.utf16());

        // Translate the target url
        auto j_target_url = JniLocalRef<jstring>::from_utf16(env, target_url.utf16());

        // Translate the popup rect
        auto j_popup_rect = IntRect::CLAZZ.alloc_object(env);
        IntRect::LEFT.set(env, j_popup_rect, popup_rect.left);
        IntRect::TOP.set(env, j_popup_rect, popup_rect.top);
        IntRect::RIGHT.set(env, j_popup_rect, popup_rect.right);
        IntRect::BOTTOM.set(env, j_popup_rect, popup_rect.bottom);

        // Call the Java instance
        auto j_child_view
            = JNIUlViewListener::ON_CREATE_CHILD_VIEW
                  .invoke(env, j_listener, j_opener_url, j_target_url, static_cast<jboolean>(is_popup), j_popup_rect);

        if (!j_child_view.is_valid()) {
            return { nullptr };
        }

        // Return the child view
        ultralight::View *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, j_child_view));

        return { view };
    }

    ultralight::RefPtr<ultralight::View> ViewListener::OnCreateInspectorView(
        ultralight::View *caller, bool is_local, const ultralight::String &inspected_url
    ) {
        using native_access::JNIUlView;
        using native_access::JNIUlViewListener;

        auto env = JniEnv::require_existing_from_thread();

        // Translate the inspected url
        auto j_inspected_url = JniLocalRef<jstring>::from_utf16(env, inspected_url.utf16());

        // Call the Java instance
        auto j_child_view = JNIUlViewListener::ON_CREATE_INSPECTOR_VIEW
                                .invoke(env, j_listener, static_cast<jboolean>(is_local), j_inspected_url);

        if (!j_child_view.is_valid()) {
            return { nullptr };
        }

        // Return the child view
        ultralight::View *view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, j_child_view));

        return { view };
    }

    void ViewListener::OnRequestClose(ultralight::View *caller) {
        using native_access::JNIUlViewListener;

        auto env = JniEnv::require_existing_from_thread();

        // Call the Java instance
        JNIUlViewListener::ON_REQUEST_CLOSE.invoke(env, j_listener);
    }

    const JniGlobalRef<jobject> &ViewListener::get_j_listener() const { return j_listener; }
} // namespace ujr