package net.janrupf.ujr.nap;

import javax.lang.model.element.Element;
import java.util.HashSet;
import java.util.Set;

/**
 * Helper to store the collected targets.
 */
public class TargetCollector {
    private final Set<Element> nativeAccessElements;

    public TargetCollector() {
        this.nativeAccessElements = new HashSet<>();
    }

    /**
     * Adds an element which has been annotated with @NativeAccess.
     *
     * @param element the element to add
     */
    public void collectNativeAccess(Element element) {
        nativeAccessElements.add(element);
    }

    /**
     * Retrieves all elements which have been annotated with @NativeAccess.
     *
     * @return the elements
     */
    public Set<Element> getNativeAccessElements() {
        return nativeAccessElements;
    }
}
