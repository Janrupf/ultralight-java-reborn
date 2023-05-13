package net.janrupf.ujr.example.png;

import net.janrupf.ujr.api.UltralightView;
import net.janrupf.ujr.api.cursor.UlCursor;
import net.janrupf.ujr.api.listener.UlMessageLevel;
import net.janrupf.ujr.api.listener.UlMessageSource;
import net.janrupf.ujr.api.listener.UltralightViewListener;
import net.janrupf.ujr.api.math.IntRect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A view listener can observe some changes that happen in a view. It will mostly be used
 * to update user-interface related elements, such as changing titles, tooltips and so on.
 * <p>
 * You can also monitor some state changes, such as the cursor or the URL. Additionally, this
 * listener can be used for debugging by monitoring message outputs.
 */
public class ViewListener implements UltralightViewListener {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onChangeTitle(UltralightView view, String title) {
        LOGGER.info("View title changed to {}", title);
    }

    @Override
    public void onChangeURL(UltralightView view, String url) {
        LOGGER.info("View URL changed to {}", url);
    }

    @Override
    public void onChangeTooltip(UltralightView view, String tooltip) {
        LOGGER.info("View tooltip changed to {}", tooltip);
    }

    @Override
    public void onChangeCursor(UltralightView view, UlCursor cursor) {
        LOGGER.info("View cursor changed to {}", cursor);
    }

    @Override
    public void onAddConsoleMessage(
            UltralightView view,
            UlMessageSource source,
            UlMessageLevel level,
            String message,
            long lineNumber,
            long columnNumber,
            String sourceId
    ) {
        // This will be called when a message is added to the console, for example by calling
        // console.log() in JavaScript. Other message sources are browser subsystems - effectively
        // all debug messages produced by the web content related to the view will end up here.

        LOGGER.info("View console message: {} {} {}:{}:{}",
                source, level, sourceId, lineNumber, columnNumber);
    }

    @Override
    public UltralightView onCreateChildView(
            UltralightView view,
            String openerUrl,
            String targetUrl,
            boolean isPopup,
            IntRect popupRect
    ) {
        // This will be called when a view requested a new view to be created, for example
        // by calling window.open() in JavaScript or by clicking a link with target="_blank".
        //
        // You are free to implement this logic in whichever way you want, including
        // ignoring the request like we do here.

        LOGGER.info("Denying creation of child view attempted by {} (tried to open {}, popup: {})", openerUrl, targetUrl, isPopup);
        return null; // Returning null cancels the creation
    }

    @Override
    public UltralightView onCreateInspectorView(UltralightView view, boolean isLocal, String inspectedUrl) {
        // This will be called as a result of calling UltralightView#createLocalInspectorView(),
        // here you should set up a new view that will be used to display the inspector.
        //
        // You are free to implement this logic in whichever way you want, including
        // ignoring the request like we do here.

        LOGGER.info("Denying creation of inspector view (local: {}, inspected: {})", isLocal, inspectedUrl);
        return null; // Returning null cancels the creation
    }

    @Override
    public void onRequestClose(UltralightView view) {
        // This will be called when the view requested self-closing, for example by calling
        // window.close() in JavaScript. You are free to do whatever, including ignoring
        // the request like we do here.
        LOGGER.info("View requested close");
    }
}
