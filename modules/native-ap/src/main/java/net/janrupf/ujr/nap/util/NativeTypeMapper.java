package net.janrupf.ujr.nap.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
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
     * @param type the type to convert
     * @return the JNI type
     */
    public String toJniType(TypeMirror type) {
        return toJniType(type, true);
    }

    /**
     * Converts the given type element to a native type.
     *
     * @param type                  the type to convert
     * @param enableJniWrapperTypes whether to enable C++ JNI wrapper types
     * @return the JNI type
     */
    public String toJniType(TypeMirror type, boolean enableJniWrapperTypes) {
        // First check for primitive types
        String primitive = toPrimitiveJniType(type);
        if (primitive != null) {
            return primitive;
        }

        TypeElement element = (TypeElement) environment.getTypeUtils().asElement(type);

        if (element != null) {
            PackageElement pkg = environment.getElementUtils().getPackageOf(element);
            if (pkg != null && pkg.getQualifiedName().toString().startsWith("java.lang.")) {
                // Disable JNI wrapper types for Java builtin types
                enableJniWrapperTypes = false;
            }
        }

        // Non-primitive type
        if (enableJniWrapperTypes) {
            return toJniClassType(type);
        } else if (type.getKind() == TypeKind.ARRAY) {
            return toJniArrayType((ArrayType) type);
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
     * Converts the given array type to a JNI array type.
     *
     * @param type the type to convert
     * @return the JNI array type
     */
    public String toJniArrayType(ArrayType type) {
        TypeMirror componentType = type.getComponentType();

        switch (componentType.getKind()) {
            case BOOLEAN:
                return "jbooleanArray";
            case BYTE:
                return "jbyteArray";
            case SHORT:
                return "jshortArray";
            case INT:
                return "jintArray";
            case LONG:
                return "jlongArray";
            case CHAR:
                return "jcharArray";
            case FLOAT:
                return "jfloatArray";
            case DOUBLE:
                return "jdoubleArray";
            default:
                return "jobjectArray";
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
     * @param type the type to convert
     * @return the JNI class type
     */
    public String toJniClassType(TypeMirror type) {
        boolean isArray = type.getKind() == TypeKind.ARRAY;

        String binaryClassName = toJavaBinaryTypeName(type);

        String jniType = toJniType(type, false);

        return "::ujr::JniClass" +
                "<\"" + binaryClassName + "\"," +
                " " + jniType + ", " +
                arrayDepth(type) +
                ">";
    }

    /**
     * Calculates the depth of the given array type.
     *
     * @param type the type to calculate the depth for
     * @return the depth of the given array type
     */
    public int arrayDepth(TypeMirror type) {
        int depth = 0;

        while (type.getKind() == TypeKind.ARRAY) {
            type = ((ArrayType) type).getComponentType();
            depth++;
        }

        return depth;
    }

    /**
     * Converts the given type to a Java binary type name.
     * <p>
     * Please note that this functions strips the array type from the given type.
     *
     * @param type the type to convert
     * @return the Java binary type name
     */
    public String toJavaBinaryTypeName(TypeMirror type) {
        StringBuilder binaryClassName = new StringBuilder();

        while (type.getKind() == TypeKind.ARRAY) {
            type = ((ArrayType) type).getComponentType();
        }

        switch (type.getKind()) {
            case BOOLEAN:
                binaryClassName.append("Z");
                break;
            case BYTE:
                binaryClassName.append("B");
                break;
            case SHORT:
                binaryClassName.append("S");
                break;
            case INT:
                binaryClassName.append("I");
                break;
            case LONG:
                binaryClassName.append("J");
                break;
            case CHAR:
                binaryClassName.append("C");
                break;
            case FLOAT:
                binaryClassName.append("F");
                break;
            case DOUBLE:
                binaryClassName.append("D");
                break;
            case VOID:
                binaryClassName.append("V");
                break;
            default:
                TypeElement element = (TypeElement) environment.getTypeUtils().asElement(type);
                binaryClassName.append(environment.getElementUtils().getBinaryName(element).toString().replace('.', '/'));
                break;
        }

        return binaryClassName.toString();
    }
}
