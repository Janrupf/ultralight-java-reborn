package net.janrupf.ujr.example.glfw.window;

public interface WindowStateListener {
    void onFramebufferSizeChange(int width, int height);

    void onCursorPos(double x, double y);

    void onMouseButton(int button, int action, int mods);

    void onScroll(double x, double y);

    void onCharMods(int codepoint, int mods);

    void onKey(int key, int scancode, int action, int mods);

    void onFocusChange(boolean isFocused);
}
