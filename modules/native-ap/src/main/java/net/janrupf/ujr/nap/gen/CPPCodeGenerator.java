package net.janrupf.ujr.nap.gen;

import net.janrupf.ujr.nap.TargetCollector;
import net.janrupf.ujr.nap.util.NameMapper;
import net.janrupf.ujr.nap.util.NativeTypeMapper;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Generates C++ code for the collected targets.
 */
public class CPPCodeGenerator {
    private ProcessingEnvironment environment;
    private NativeTypeMapper typeMapper;

    public CPPCodeGenerator(ProcessingEnvironment environment) {
        this.environment = environment;
        this.typeMapper = new NativeTypeMapper(environment);
    }

    /**
     * Generates C++ code for the collected targets.
     *
     * @param environment     the processing environment
     * @param targetCollector the target collector to retrieve the collected targets from
     */
    public void generate(ProcessingEnvironment environment, TargetCollector targetCollector) {
        Set<Element> nativeAccessElements = targetCollector.getNativeAccessElements();
        Map<TypeElement, Set<Element>> nativeAccessClasses = new HashMap<>();

        for (Element element : nativeAccessElements) {
            Element enclosing = element.getEnclosingElement();

            if (!(enclosing instanceof TypeElement)) {
                // Should never happen, but we better check
                environment.getMessager().printMessage(Diagnostic.Kind.ERROR, "Enclosing element is not a type", element);
                continue;
            }

            Set<Element> elementsInClass = nativeAccessClasses.computeIfAbsent(
                    (TypeElement) enclosing, k -> new LinkedHashSet<>() // Preserve insertion order
            );

            // Add the element so we know about it
            elementsInClass.add(element);
        }

        for (Map.Entry<TypeElement, Set<Element>> entry : nativeAccessClasses.entrySet()) {
            try {
                generateNativeAccessClass(entry.getKey(), entry.getValue());
            } catch (IOException e) {
                // Failed to write the file
                environment.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "Failed to write native access file",
                        entry.getKey()
                );
            }
        }
    }

    /**
     * Generates a native access class for the given type element.
     *
     * @param typeElement the type element to generate the class for
     * @param elements    the elements in the class to generate
     * @throws IOException if an I/O error occurs
     */
    private void generateNativeAccessClass(
            TypeElement typeElement,
            Set<Element> elements
    ) throws IOException {
        Name qualifiedName = typeElement.getQualifiedName();

        // Create a new header file
        String headerName = qualifiedName.toString().replace(".", "_") + "_native_access.hpp";
        FileObject output = environment.getFiler().createResource(
                StandardLocation.NATIVE_HEADER_OUTPUT,
                "",
                headerName,
                typeElement
        );

        // Build the class
        CPPNativeAccessClassBuilder builder = new CPPNativeAccessClassBuilder(
                environment,
                typeMapper,
                "ujr::native_access",
                typeElement.getSimpleName().toString()
        );


        // Add the class itself
        builder.addClass(typeElement);

        // Now add all the members
        for (Element element : elements) {
            if (element instanceof VariableElement) {
                // It's a field
                builder.addField(
                        (VariableElement) element,
                        NameMapper.toScreamingSnakeCase(element.getSimpleName().toString())
                );
            } else if (element instanceof ExecutableElement) {
                String name = element.getSimpleName().toString();
                if (name.equals("<init>")) {
                    name = "constructor";
                }

                // It's a method
                builder.addMethod(
                        (ExecutableElement) element,
                        NameMapper.toScreamingSnakeCase(name)
                );
            } else {
                // ???
                environment.getMessager().printMessage(Diagnostic.Kind.ERROR, "Unknown element type", element);
            }
        }

        // Build the class and write it to the file
        try (Writer writer = output.openWriter()) {
            writer.write(builder.build());
        }
    }
}
