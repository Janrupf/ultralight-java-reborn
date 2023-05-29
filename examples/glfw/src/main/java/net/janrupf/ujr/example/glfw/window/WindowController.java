package net.janrupf.ujr.example.glfw.window;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengles.GLES;
import org.lwjgl.opengles.GLES32;
import org.lwjgl.opengles.GLESCapabilities;
import org.lwjgl.system.MemoryUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WindowController implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger(WindowController.class);

    private final long rootWindow;
    private final Set<Window> windows;
    private final Map<Integer, Long> standardCursors;

    public WindowController() {
        this.windows = new HashSet<>();

        LOGGER.debug("Initializing GLFW...");
        if (!GLFW.glfwInit()) {
            LOGGER.fatal("Failed to initialize GLFW!");
            throw new IllegalStateException("glfwInit() returned false");
        }

        LOGGER.info("GLFW initialized!");
        LOGGER.debug("Using GLFW {}", GLFW.glfwGetVersionString());

        // Create an invisible root window, this will hold the OpenGLES 3.0 context
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_OPENGL_ES_API);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_CREATION_API, GLFW.GLFW_NATIVE_CONTEXT_API);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 0);
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);

        rootWindow = GLFW.glfwCreateWindow(1, 1, "", 0, 0);
        LOGGER.debug("Created invisible root window {}", rootWindow);

        GLFW.glfwMakeContextCurrent(rootWindow);
        GLFW.glfwSwapInterval(1);

        // Initialize OpenGLES
        GLESCapabilities capabilities = GLES.createCapabilities();
        LOGGER.info("OpenGL ES initialized!");

        if (capabilities.GL_KHR_debug) {
            LOGGER.info("Enabling OpenGL ES debug output");
            GLES32.glEnable(GLES32.GL_DEBUG_OUTPUT);
            GLES32.glDebugMessageCallback((source, type, id, severity, length, message, userParam) -> {
                String messageString = MemoryUtil.memUTF8(message, length);
                LOGGER.debug("OpenGL ES debug message: {}", messageString);
            }, 0);
        }

        this.standardCursors = new HashMap<>();
        int[] cursors = {
                GLFW.GLFW_ARROW_CURSOR,
                GLFW.GLFW_IBEAM_CURSOR,
                GLFW.GLFW_CROSSHAIR_CURSOR,
                GLFW.GLFW_POINTING_HAND_CURSOR,
                GLFW.GLFW_RESIZE_EW_CURSOR,
                GLFW.GLFW_RESIZE_NS_CURSOR,
                GLFW.GLFW_RESIZE_NWSE_CURSOR,
                GLFW.GLFW_RESIZE_NESW_CURSOR,
                GLFW.GLFW_RESIZE_ALL_CURSOR,
                GLFW.GLFW_NOT_ALLOWED_CURSOR,
        };

        LOGGER.debug("Creating cursors...");
        for (int c : cursors) {
            long stdC = GLFW.glfwCreateStandardCursor(c);
            LOGGER.trace("Created cursor {} -> {}", c, stdC);
            standardCursors.put(c, stdC);
        }
    }

    public void terminate() {
        LOGGER.debug("Destroying cursors...");
        for (Map.Entry<Integer, Long> entry : standardCursors.entrySet()) {
            LOGGER.trace("Destroying cursor {} -> {}...", entry.getKey(), entry.getValue());
            GLFW.glfwDestroyCursor(entry.getValue());
        }

        LOGGER.debug("Terminating GLFW...");
        GLFW.glfwDestroyWindow(rootWindow);
        GLFW.glfwTerminate();
    }

    @Override
    public void close() {
        terminate();
    }

    public Window createWindow(long width, long height, String title) {
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_OPENGL_ES_API);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_CREATION_API, GLFW.GLFW_NATIVE_CONTEXT_API);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 0);
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);

        LOGGER.debug("Creating window with size {}x{} and title \"{}\"", width, height, title);

        // Create a new window which shares the existing OpenGLES context so that we can use
        // the Ultralight views backed by PBO's and render into the windows framebuffer
        long handle = GLFW.glfwCreateWindow((int) width, (int) height, title, 0, rootWindow);
        if (handle == 0) {
            throw new IllegalStateException("Failed to create window");
        }

        Window window = new Window(this, handle);
        windows.add(window);

        window.activateContext();
        GLFW.glfwSwapInterval(1);

        activateRootContext();

        return window;
    }

    public long getStandardCursor(int cursor) {
        return standardCursors.get(cursor);
    }

    /* package */ void notifyClose(Window window) {
        windows.remove(window);
    }

    public void activateRootContext() {
        GLFW.glfwMakeContextCurrent(rootWindow);
    }

    public void processEvents() {
        // Process some events until a timeout of 5ms is reached
        GLFW.glfwWaitEventsTimeout(0.005);
    }

    public boolean windowsOpen() {
        return !windows.isEmpty();
    }
}
