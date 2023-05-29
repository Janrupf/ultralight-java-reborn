package net.janrupf.ujr.example.glfw;

import net.janrupf.ujr.example.glfw.web.WebController;
import net.janrupf.ujr.example.glfw.web.WebWindow;
import net.janrupf.ujr.example.glfw.window.WindowController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

// This example demonstrates how to use Ultralight Java Reborn with GLFW.
//
// For a way more in-depth example and explanation of the Ultralight Java Reborn API, please
// refer to the examples/png module.
public class GLFWExample {
    private static final Logger LOGGER = LogManager.getLogger(GLFWExample.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Ultralight Java Reborn GLFW example...");

        try (
                WindowController windowController = new WindowController();
                WebController webController = new WebController(windowController);
        ) {
            WebWindow window = webController.createWindow(1280, 720, "Ultralight Java Reborn GLFW Example");
            window.getView().loadURL("https://google.com");

            double maxUpdateTime = 1.0 / 50.0;

            // Main loop
            while (windowController.windowsOpen()) {
                double current = GLFW.glfwGetTime();
                double deadline = current + maxUpdateTime;

                // Drive the update loop until it is nearly time to
                // render the next frame.
                while (current < deadline) {
                    windowController.processEvents();
                    webController.update();
                    current = GLFW.glfwGetTime();
                }

                webController.update();
                webController.render();
            }
        }
    }

}
