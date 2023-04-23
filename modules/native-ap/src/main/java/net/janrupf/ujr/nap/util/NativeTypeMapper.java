package net.janrupf.ujr.nap.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Helper to map Java types to native (JNI) types.
 */
public class NativeTypeMapper {
    private final ProcessingEnvironment environment;

    /**
     * Creates a new native type mapper.
     *
     * @param environment the environment to use
     */
    public NativeTypeMapper(ProcessingEnvironment environment) {
        this.environment = environment;
    }

    /**
     * Converts the given type element to a native type.
     *
     * @param element the element to convert
     * @return the JNI type
     */
    public String toJniType(TypeElement element) {
        return toJniType(element, true);
    }

    /**
     * Converts the given type element to a native type.
     *
     * @param element               the element to convert
     * @param enableJniWrapperTypes whether to enable C++ JNI wrapper types
     * @return the JNI type
     */
    public String toJniType(TypeElement element, boolean enableJniWrapperTypes) {
        TypeMirror type = element.asType();

        // First check for primitive types
        String primitive = toPrimitiveJniType(type);
        if (primitive != null) {
            return primitive;
        }

        PackageElement pkg = environment.getElementUtils().getPackageOf(element);
        if (pkg != null && pkg.getQualifiedName().toString().startsWith("java.lang.")) {
            // Disable JNI wrapper types for Java builtin types
            enableJniWrapperTypes = false;
        }

        // Non-primitive type
        if (enableJniWrapperTypes) {
            return toJniClassType(element);
        } else if (isInstanceOf(element, "java.lang.String")) {
            return "jstring";
        } else if (isInstanceOf(element, "java.lang.Throwable")) {
            return "jthrowable";
        } else if (isInstanceOf(element, "java.lang.Class")) {
            return "jclass";
        } else {
            return "jobject";
        }
    }

    /**
     * Converts the given type mirror to a primitive JNI native type.
     *
     * @param type the type to convert
     * @return the primitive JNI type, or null, if not a primitive type
     */
    public String toPrimitiveJniType(TypeMirror type) {
        switch (type.getKind()) {
            case BOOLEAN:
                return "jboolean";
            case BYTE:
                return "jbyte";
            case SHORT:
                return "jshort";
            case INT:
                return "jint";
            case LONG:
                return "jlong";
            case CHAR:
                return "jchar";
            case FLOAT:
                return "jfloat";
            case DOUBLE:
                return "jdouble";
            case VOID:
                return "void";
            default:
                return null;
        }
    }

    /**
     * Determines whether the given element has the given superclass.
     *
     * @param element the element to check
     * @param name    the name of the superclass
     * @return true if the element has the given superclass, false otherwise
     */
    private boolean isInstanceOf(TypeElement element, String name) {
        TypeElement target = environment.getElementUtils().getTypeElement(name);

        TypeMirror elementMirror = element.asType();
        TypeMirror targetMirror = target.asType();

        return environment.getTypeUtils().isAssignable(elementMirror, targetMirror);
    }

    /**
     * Converts a type element to a JNI class type.
     *
     * @param clazz the element to convert
     * @return the JNI class type
     */
    public String toJniClassType(TypeElement clazz) {
        String binaryClassName = environment.getElementUtils().getBinaryName(clazz).toString();
        String jniType = toJniType(clazz, false);

        return "::ujr::JniClass<\"" + binaryClassName.replace('.', '/') + "\", " + jniType + ">";
    }
}
