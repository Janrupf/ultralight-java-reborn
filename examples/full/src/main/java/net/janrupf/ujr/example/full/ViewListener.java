package net.janrupf.ujr.example.full;

import net.janrupf.ujr.api.UltralightView;
import net.janrupf.ujr.api.cursor.UlCursor;
import net.janrupf.ujr.api.listener.UlMessageLevel;
import net.janrupf.ujr.api.listener.UlMessageSource;
import net.janrupf.ujr.api.listener.UltralightViewListener;
import net.janrupf.ujr.api.math.IntRect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewListener implements UltralightViewListener {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onChangeTitle(String title) {
        LOGGER.info("View title changed to {}", title);
    }

    @Override
    public void onChangeURL(String url) {
        LOGGER.info("View URL changed to {}", url);
    }

    @Override
    public void onChangeTooltip(String tooltip) {
        LOGGER.info("View tooltip changed to {}", tooltip);
    }

    @Override
    public void onChangeCursor(UlCursor cursor) {
        LOGGER.info("View cursor changed to {}", cursor);
    }

    @Override
    public void onAddConsoleMessage(
            UlMessageSource source,
            UlMessageLevel level,
            String message,
            long lineNumber,
            long columnNumber,
            String sourceId
    ) {
        LOGGER.info("View console message: {} {} {}:{}:{}",
                source, level, sourceId, lineNumber, columnNumber);
    }

    @Override
    public UltralightView onCreateChildView(String openerUrl, String targetUrl, boolean isPopup, IntRect popupRect) {
        LOGGER.info("Denying creation of child view attempted by {} (tried to open {}, popup: {})", openerUrl, targetUrl, isPopup);
        return null;
    }

    @Override
    public UltralightView onCreateInspectorView(boolean isLocal, String inspectedUrl) {
        LOGGER.info("Denying creation of inspector view (local: {}, inspected: {})", isLocal, inspectedUrl);
        return null;
    }

    @Override
    public void onRequestClose() {
        LOGGER.info("View requested close");
    }
}
