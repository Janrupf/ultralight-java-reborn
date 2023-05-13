#include "net_janrupf_ujr_api_cursor_UlCursor_native_access.hpp"
#include "net_janrupf_ujr_api_listener_UlMessageLevel_native_access.hpp"
#include "net_janrupf_ujr_api_listener_UlMessageSource_native_access.hpp"
#include "net_janrupf_ujr_api_math_IntRect_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_JNIUlView_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlViewListenerNative.h"
#include "net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlViewListenerNative_native_access.hpp"

#include <Ultralight/Listener.h>
#include <Ultralight/View.h>

#include <stdexcept>

#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/View.hpp"

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlViewListenerNative_nativeOnChangeTitle(
    JNIEnv *env, jobject self, jobject view, jstring title
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlViewListenerNative;
        using ujr::native_access::JNIUlView;

        auto j_title = env.wrap_argument(title);

        auto *listener = reinterpret_cast<ultralight::ViewListener *>(JNIUlViewListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        listener->OnChangeTitle(ul_view, j_title.to_utf16());
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlViewListenerNative_nativeOnChangeURL(
    JNIEnv *env, jobject self, jobject view, jstring url
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlViewListenerNative;
        using ujr::native_access::JNIUlView;

        auto j_url = env.wrap_argument(url);

        auto *listener = reinterpret_cast<ultralight::ViewListener *>(JNIUlViewListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        listener->OnChangeURL(ul_view, j_url.to_utf16());
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlViewListenerNative_nativeOnChangeTooltip(
    JNIEnv *env, jobject self, jobject view, jstring tooltip
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlViewListenerNative;
        using ujr::native_access::JNIUlView;

        auto j_tooltip = env.wrap_argument(tooltip);

        auto *listener = reinterpret_cast<ultralight::ViewListener *>(JNIUlViewListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        listener->OnChangeTooltip(ul_view, j_tooltip.to_utf16());
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlViewListenerNative_nativeOnChangeCursor(
    JNIEnv *env, jobject self, jobject view, jobject cursor
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlViewListenerNative;
        using ujr::native_access::UlCursor;
        using ujr::native_access::JNIUlView;

        auto *listener = reinterpret_cast<ultralight::ViewListener *>(JNIUlViewListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        auto j_cursor = env.wrap_argument(cursor);

        ultralight::Cursor ul_cursor;

        if (j_cursor == UlCursor::POINTER.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Pointer;
        } else if (j_cursor == UlCursor::CROSS.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Cross;
        } else if (j_cursor == UlCursor::HAND.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Hand;
        } else if (j_cursor == UlCursor::I_BEAM.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_IBeam;
        } else if (j_cursor == UlCursor::WAIT.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Wait;
        } else if (j_cursor == UlCursor::HELP.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Help;
        } else if (j_cursor == UlCursor::EAST_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_EastResize;
        } else if (j_cursor == UlCursor::NORTH_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_NorthResize;
        } else if (j_cursor == UlCursor::NORTH_EAST_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_NorthEastResize;
        } else if (j_cursor == UlCursor::NORTH_WEST_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_NorthWestResize;
        } else if (j_cursor == UlCursor::SOUTH_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_SouthResize;
        } else if (j_cursor == UlCursor::SOUTH_EAST_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_SouthEastResize;
        } else if (j_cursor == UlCursor::SOUTH_WEST_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_SouthWestResize;
        } else if (j_cursor == UlCursor::WEST_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_WestResize;
        } else if (j_cursor == UlCursor::NORTH_SOUTH_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_NorthSouthResize;
        } else if (j_cursor == UlCursor::EAST_WEST_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_EastWestResize;
        } else if (j_cursor == UlCursor::NORTH_EAST_SOUTH_WEST_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_NorthEastSouthWestResize;
        } else if (j_cursor == UlCursor::NORTH_WEST_SOUTH_EAST_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_NorthWestSouthEastResize;
        } else if (j_cursor == UlCursor::COLUMN_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_ColumnResize;
        } else if (j_cursor == UlCursor::ROW_RESIZE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_RowResize;
        } else if (j_cursor == UlCursor::MIDDLE_PANNING.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_MiddlePanning;
        } else if (j_cursor == UlCursor::EAST_PANNING.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_EastPanning;
        } else if (j_cursor == UlCursor::NORTH_PANNING.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_NorthPanning;
        } else if (j_cursor == UlCursor::NORTH_EAST_PANNING.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_NorthEastPanning;
        } else if (j_cursor == UlCursor::NORTH_WEST_PANNING.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_NorthWestPanning;
        } else if (j_cursor == UlCursor::SOUTH_PANNING.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_SouthPanning;
        } else if (j_cursor == UlCursor::SOUTH_EAST_PANNING.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_SouthEastPanning;
        } else if (j_cursor == UlCursor::SOUTH_WEST_PANNING.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_SouthWestPanning;
        } else if (j_cursor == UlCursor::WEST_PANNING.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_WestPanning;
        } else if (j_cursor == UlCursor::MOVE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Move;
        } else if (j_cursor == UlCursor::VERTICAL_TEXT.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_VerticalText;
        } else if (j_cursor == UlCursor::CELL.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Cell;
        } else if (j_cursor == UlCursor::CONTEXT_MENU.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_ContextMenu;
        } else if (j_cursor == UlCursor::ALIAS.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Alias;
        } else if (j_cursor == UlCursor::PROGRESS.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Progress;
        } else if (j_cursor == UlCursor::NO_DROP.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_NoDrop;
        } else if (j_cursor == UlCursor::COPY.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Copy;
        } else if (j_cursor == UlCursor::NONE.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_None;
        } else if (j_cursor == UlCursor::NOT_ALLOWED.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_NotAllowed;
        } else if (j_cursor == UlCursor::ZOOM_IN.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_ZoomIn;
        } else if (j_cursor == UlCursor::ZOOM_OUT.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_ZoomOut;
        } else if (j_cursor == UlCursor::GRAB.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Grab;
        } else if (j_cursor == UlCursor::GRABBING.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Grabbing;
        } else if (j_cursor == UlCursor::CUSTOM.get(env)) {
            ul_cursor = ultralight::Cursor::kCursor_Custom;
        } else {
            throw std::runtime_error("Unknown cursor type");
        }

        listener->OnChangeCursor(ul_view, ul_cursor);
    });
}

JNIEXPORT void JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlViewListenerNative_nativeOnAddConsoleMessage(
    JNIEnv *env,
    jobject self,
    jobject view,
    jobject source,
    jobject level,
    jstring message,
    jlong line_number,
    jlong column_number,
    jstring source_id
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlViewListenerNative;
        using ujr::native_access::UlMessageSource;
        using ujr::native_access::UlMessageLevel;
        using ujr::native_access::JNIUlView;

        auto j_message = env.wrap_argument(message);
        auto j_source_id = env.wrap_argument(source_id);

        auto *listener = reinterpret_cast<ultralight::ViewListener *>(JNIUlViewListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        ultralight::MessageSource ul_source;

        if (source == UlMessageSource::XML.get(env)) {
            ul_source = ultralight::MessageSource::kMessageSource_XML;
        } else if (source == UlMessageSource::JS.get(env)) {
            ul_source = ultralight::MessageSource::kMessageSource_JS;
        } else if (source == UlMessageSource::NETWORK.get(env)) {
            ul_source = ultralight::MessageSource::kMessageSource_Network;
        } else if (source == UlMessageSource::CONSOLE_API.get(env)) {
            ul_source = ultralight::MessageSource::kMessageSource_ConsoleAPI;
        } else if (source == UlMessageSource::STORAGE.get(env)) {
            ul_source = ultralight::MessageSource::kMessageSource_Storage;
        } else if (source == UlMessageSource::APP_CACHE.get(env)) {
            ul_source = ultralight::MessageSource::kMessageSource_AppCache;
        } else if (source == UlMessageSource::RENDERING.get(env)) {
            ul_source = ultralight::MessageSource::kMessageSource_Rendering;
        } else if (source == UlMessageSource::CSS.get(env)) {
            ul_source = ultralight::MessageSource::kMessageSource_CSS;
        } else if (source == UlMessageSource::SECURITY.get(env)) {
            ul_source = ultralight::MessageSource::kMessageSource_Security;
        } else if (source == UlMessageSource::CONTENT_BLOCKER.get(env)) {
            ul_source = ultralight::MessageSource::kMessageSource_ContentBlocker;
        } else if (source == UlMessageSource::OTHER.get(env)) {
            ul_source = ultralight::MessageSource::kMessageSource_Other;
        } else {
            throw std::runtime_error("Unknown message source");
        }

        ultralight::MessageLevel ul_level;

        if (level == UlMessageLevel::LOG.get(env)) {
            ul_level = ultralight::MessageLevel::kMessageLevel_Log;
        } else if (level == UlMessageLevel::WARNING.get(env)) {
            ul_level = ultralight::MessageLevel::kMessageLevel_Warning;
        } else if (level == UlMessageLevel::ERROR.get(env)) {
            ul_level = ultralight::MessageLevel::kMessageLevel_Error;
        } else if (level == UlMessageLevel::DEBUG.get(env)) {
            ul_level = ultralight::MessageLevel::kMessageLevel_Debug;
        } else if (level == UlMessageLevel::INFO.get(env)) {
            ul_level = ultralight::MessageLevel::kMessageLevel_Info;
        } else {
            throw std::runtime_error("Unknown message level");
        }

        listener->OnAddConsoleMessage(
            ul_view,
            ul_source,
            ul_level,
            j_message.to_utf16(),
            line_number,
            column_number,
            j_source_id.to_utf16()
        );
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlViewListenerNative_nativeOnCreateChildView(
    JNIEnv *env,
    jobject self,
    jobject view,
    jstring opener_url,
    jstring target_url,
    jboolean is_popup,
    jobject popup_rect
) {
    return ujr::jni_entry_guard(env, [&](auto env) -> jobject {
        using ujr::native_access::JNIUlViewListenerNative;
        using ujr::native_access::IntRect;
        using ujr::native_access::JNIUlView;

        auto j_opener_url = env.wrap_argument(opener_url);
        auto j_target_url = env.wrap_argument(target_url);
        auto j_popup_rect = env.wrap_argument(popup_rect);

        auto *listener = reinterpret_cast<ultralight::ViewListener *>(JNIUlViewListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        ultralight::IntRect rect { IntRect::LEFT.get(env, j_popup_rect),
                                   IntRect::TOP.get(env, j_popup_rect),
                                   IntRect::RIGHT.get(env, j_popup_rect),
                                   IntRect::BOTTOM.get(env, j_popup_rect) };

        auto popup_view
            = listener->OnCreateChildView(ul_view, j_opener_url.to_utf16(), j_target_url.to_utf16(), is_popup, rect);

        if (!popup_view) {
            return nullptr;
        }

        auto j_view = ujr::View::wrap(env, ultralight::RefPtr(ul_view));
        return j_view.leak();
    });
}

JNIEXPORT jobject JNICALL
Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlViewListenerNative_nativeOnCreateInspectorView(
    JNIEnv *env, jobject self, jobject view, jboolean is_local, jstring inspected_url
) {
    return ujr::jni_entry_guard(env, [&](auto env) -> jobject {
        using ujr::native_access::JNIUlViewListenerNative;
        using ujr::native_access::JNIUlView;

        auto j_inspected_url = env.wrap_argument(inspected_url);

        auto *listener = reinterpret_cast<ultralight::ViewListener *>(JNIUlViewListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        auto inspector_view = listener->OnCreateInspectorView(ul_view, is_local, j_inspected_url.to_utf16());

        if (!inspector_view) {
            return nullptr;
        }

        auto j_view = ujr::View::wrap(env, inspector_view);
        return j_view.leak();
    });
}

JNIEXPORT void JNICALL Java_net_janrupf_ujr_platform_jni_wrapper_listener_JNIUlViewListenerNative_nativeOnRequestClose(
    JNIEnv *env, jobject self, jobject view
) {
    ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIUlViewListenerNative;
        using ujr::native_access::JNIUlView;

        auto *listener = reinterpret_cast<ultralight::ViewListener *>(JNIUlViewListenerNative::HANDLE.get(env, self));
        auto *ul_view = reinterpret_cast<ultralight::View *>(JNIUlView::HANDLE.get(env, view));

        listener->OnRequestClose(ul_view);
    });
}
