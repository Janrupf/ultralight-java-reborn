package net.janrupf.ujr.api.config;

/**
 * Configuration of specific Ultralight views.
 */
public class UlViewConfig {
    // TODO: Fix doc comment references
    /**
     * Whether to render using the GPU renderer (accelerated) or the CPU renderer (unaccelerated).
     * <p>
     * When true, the View will be rendered to an offscreen GPU texture using the GPU driver set in
     * Platform::set_gpu_driver. You can fetch details for the texture via View::render_target.
     * <p>
     * When false (the default), the View will be rendered to an offscreen pixel buffer using the
     * multithreaded CPU renderer. This pixel buffer can optionally be provided by the user.
     */
    public boolean isAccelerated = false;

    /**
     * Whether or not this View should support transparency.
     * <p>
     * Make sure to also set the following CSS on the page:
     * {@code html, body { background: transparent; }}
     */
    public boolean isTransparent = false;

    /**
     * The initial device scale, ie. the amount to scale page units to screen pixels. This should
     * be set to the scaling factor of the device that the View is displayed on.
     * <p>
     * 1.0 is equal to 100% zoom (no scaling), 2.0 is equal to 200% zoom (2x scaling)
     */
    public double initialDeviceScale = 1.0;

    // TODO: Fix doc comment references
    /**
     * Whether or not the View should initially have input focus, @see View::Focus()
     */
    public boolean initialFocus = true;

    /**
     * Whether or not images should be enabled.
     */
    public boolean enableImages = true;

    /**
     * Whether or not JavaScript should be enabled.
     */
    public boolean enableJavascript = true;

    /**
     * Default font-family to use.
     */
    public String fontFamilyStandard = "Times New Roman";

    /**
     * Default font-family to use for fixed fonts. (pre/code)
     */
    public String fontFamilyFixed = "Courier New";

    /**
     * Default font-family to use for serif fonts.
     */
    public String fontFamilySerif = "Times New Roman";

    /**
     * Default font-family to use for sans-serif fonts.
     */
    public String fontFamilySansSerif = "Arial";

    /**
     * Default user-agent string to use.
     */
    public String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/605.1.15 (KHTML, like Gecko) " +
            "Ultralight/1.3.0 Version/13.0.3 Safari/605.1.15 " +
            "UltralightJava/0.0.1";
}
