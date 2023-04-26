package net.janrupf.ujr.api;

import net.janrupf.ujr.api.config.UlViewConfig;

/**
 * Helper class to create a new {@link UlViewConfig} instance with a fluent API.
 */
public class UltralightViewConfigBuilder extends UlViewConfig {
    /**
     * Creates a new {@link UltralightViewConfigBuilder} instance with the default configuration.
     */
    public UltralightViewConfigBuilder() {
        super();
    }

    /**
     * Sets whether the view should be accelerated.
     * <p>
     * See {@link UlViewConfig#isAccelerated} for more information.
     *
     * @param isAccelerated whether the view should be accelerated
     * @return this
     */
    public UltralightViewConfigBuilder accelerated(boolean isAccelerated) {
        this.isAccelerated = isAccelerated;
        return this;
    }

    /**
     * Sets whether the view should be transparent.
     * <p>
     * See {@link UlViewConfig#isTransparent} for more information.
     *
     * @param isTransparent whether the view should be transparent
     * @return this
     */
    public UltralightViewConfigBuilder transparent(boolean isTransparent) {
        this.isTransparent = isTransparent;
        return this;
    }

    /**
     * Sets the initial device scale.
     * <p>
     * See {@link UlViewConfig#initialDeviceScale} for more information.
     *
     * @param initialDeviceScale the initial device scale to use
     * @return this
     */
    public UltralightViewConfigBuilder initialDeviceScale(float initialDeviceScale) {
        this.initialDeviceScale = initialDeviceScale;
        return this;
    }

    /**
     * Sets whether the view is initially focused.
     * <p>
     * See {@link UlViewConfig#initialFocus} for more information.
     *
     * @param initialFocus whether the view is initially focused
     * @return this
     */
    public UltralightViewConfigBuilder initialFocus(boolean initialFocus) {
        this.initialFocus = initialFocus;
        return this;
    }

    /**
     * Sets whether images are enabled.
     * <p>
     * See {@link UlViewConfig#enableImages} for more information.
     *
     * @param enableImages whether images are enabled
     * @return this
     */
    public UltralightViewConfigBuilder enableImages(boolean enableImages) {
        this.enableImages = enableImages;
        return this;
    }

    /**
     * Sets whether JavaScript is enabled.
     * <p>
     * See {@link UlViewConfig#enableJavascript} for more information.
     *
     * @param enableJavascript whether Javascript is enabled
     * @return this
     */
    public UltralightViewConfigBuilder enableJavascript(boolean enableJavascript) {
        this.enableJavascript = enableJavascript;
        return this;
    }

    /**
     * Sets the standard font family.
     * <p>
     * See {@link UlViewConfig#fontFamilyStandard} for more information.
     *
     * @param fontFamilyStandard the standard font family to use
     * @return this
     */
    public UltralightViewConfigBuilder fontFamilyStandard(String fontFamilyStandard) {
        this.fontFamilyStandard = fontFamilyStandard;
        return this;
    }

    /**
     * Sets the fixed font family.
     * <p>
     * See {@link UlViewConfig#fontFamilyFixed} for more information.
     *
     * @param fontFamilyFixed the fixed font family to use
     * @return this
     */
    public UltralightViewConfigBuilder fontFamilyFixed(String fontFamilyFixed) {
        this.fontFamilyFixed = fontFamilyFixed;
        return this;
    }

    /**
     * Sets the serif font family.
     * <p>
     * See {@link UlViewConfig#fontFamilySerif} for more information.
     *
     * @param fontFamilySerif the serif font family to use
     * @return this
     */
    public UltralightViewConfigBuilder fontFamilySerif(String fontFamilySerif) {
        this.fontFamilySerif = fontFamilySerif;
        return this;
    }

    /**
     * Sets the sans-serif font family.
     * <p>
     * See {@link UlViewConfig#fontFamilySansSerif} for more information.
     *
     * @param fontFamilySansSerif the sans-serif font family to use
     * @return this
     */
    public UltralightViewConfigBuilder fontFamilySansSerif(String fontFamilySansSerif) {
        this.fontFamilySansSerif = fontFamilySansSerif;
        return this;
    }

    /**
     * Sets the user agent.
     * <p>
     * See {@link UlViewConfig#userAgent} for more information.
     *
     * @param userAgent the user agent to use
     * @return this
     */
    public UltralightViewConfigBuilder userAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    /**
     * Finishes building the configuration.
     * <p>
     * This method is a no-op and is only present to for consistency with the other builders.
     *
     * @return this
     */
    public UlViewConfig build() {
        return this;
    }
}
