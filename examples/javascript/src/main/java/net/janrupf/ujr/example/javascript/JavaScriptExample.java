package net.janrupf.ujr.example.javascript;

import net.janrupf.ujr.api.javascript.*;
import net.janrupf.ujr.core.UltralightJavaReborn;
import net.janrupf.ujr.core.platform.PlatformEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavaScriptExample {
    public static void main(String[] args) {
        // For setup explanation, see PNG example
        Logger logger = LogManager.getLogger(JavaScriptExample.class);
        logger.info("Starting Ultralight Java Reborn JavaScript example...");

        PlatformEnvironment environment = PlatformEnvironment.load();
        logger.info("Platform environment has been loaded!");

        try (UltralightJavaReborn ujr = new UltralightJavaReborn(environment)) {
            ujr.activate();
            logger.info("Ultralight Java Reborn has been activated on the thread {}!", Thread.currentThread().getName());

            // In this example we will only use JavaScript core directly, so no Ultralight view is ever created.
            // Usually you would obtain the JavaScript context using UltralightView#getJavaScriptContext(), but
            // here we manually create the context.

            JSGlobalContext context = new JSGlobalContext((JSClass) null);

            // We can set a name for the context. This is only useful for debugging.
            context.setName("TestContext");

            try {
                // Create the custom object and make it available on the global object
                JSObject customObject = context.makeObject(CustomJavaScriptClass.getJavaScriptClass());
                context.getGlobalObject().setProperty("customObject", customObject);

                // Evaluate some JavaScript code
                context.evaluateScript("customObject.test = 42;\n" +
                        "\n" +
                        "new customObject();\n" +
                        "customObject();\n" +
                        "\n" +
                        "delete customObject.test;\n" +
                        "delete customObject;\n", null, null, 1);
            } catch (JavaScriptValueException e) {
                logger.error("Failed to run demo JavaScript code", e);
            }
        }
    }

}
