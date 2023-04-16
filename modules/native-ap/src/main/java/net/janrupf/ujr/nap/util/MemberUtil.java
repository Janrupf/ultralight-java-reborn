package net.janrupf.ujr.nap.util;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import java.util.Collections;

/**
 * Helper class for member-related operations.
 */
public class MemberUtil {
    /**
     * Finds a field by name in a given type.
     *
     * @param containing the type to search in
     * @param name       the name of the field
     * @return the field if it exists, null otherwise
     */
    public static VariableElement findField(TypeElement containing, String name) {
        for (VariableElement element : ElementFilter.fieldsIn(containing.getEnclosedElements())) {
            if (element.getSimpleName().contentEquals(name)) {
                return element;
            }
        }

        return null;
    }

    /**
     * Finds a method by name in a given type.
     * <p>
     * This method does not take into account the method's parameters.
     *
     * @param containing the type to search in
     * @param name       the name of the method
     * @return the method if it exists, null otherwise
     */
    public static ExecutableElement findMethod(TypeElement containing, String name) {
        for (ExecutableElement element : ElementFilter.methodsIn(containing.getEnclosedElements())) {
            if (element.getSimpleName().contentEquals(name)) {
                return element;
            }
        }

        return null;
    }
}
