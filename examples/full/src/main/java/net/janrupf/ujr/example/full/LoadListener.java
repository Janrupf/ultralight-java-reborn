package net.janrupf.ujr.example.full;

import net.janrupf.ujr.api.listener.UltralightLoadListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A load listener can observe state changes that happen in a view. This includes
 * loading events, DOM ready events and history updates.
 */
public class LoadListener implements UltralightLoadListener {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onBeginLoading(long frameId, boolean isMainFrame, String url) {
        LOGGER.info("Frame id {} started loading {}", frameId, url);
    }

    @Override
    public void onFinishLoading(long frameId, boolean isMainFrame, String url) {
        LOGGER.info("Frame id {} finished loading {}", frameId, url);
    }

    @Override
    public void onFailLoading(
            long frameId,
            boolean isMainFrame,
            String url,
            String description,
            String errorDomain,
            int errorCode
    ) {
        LOGGER.error("Frame id {} failed loading {} with error {}: {}", frameId, url, errorCode, description);
    }

    @Override
    public void onWindowObjectReady(long frameId, boolean isMainFrame, String url) {
        // This will be called when the window object has been initialized by the
        // JavaScript engine. This will only happen if any JavaScript is used on the
        // page.
        //
        // This is the earliest possible point to start using JavaScript objects.
        //
        // If you need to inject JavaScript into a page which does not have JavaScript
        // on its own, you can use the onDOMReady method. Generally, onDOMReady will
        // probably always be early enough, unless your JavaScript relies on objects
        // injected by Java code to finish loading the DOM.

        LOGGER.info("Frame id {} window object ready {}", frameId, url);
    }

    @Override
    public void onDOMReady(long frameId, boolean isMainFrame, String url) {
        // This will be called when the DOM has finished loading. This means that
        // all elements are available and can be accessed.
        //
        // This is the recommended point to start using JavaScript objects.
        LOGGER.info("Frame id {} DOM ready {}", frameId, url);
    }

    @Override
    public void onUpdateHistory() {
        LOGGER.info("View history updated");
    }
}
