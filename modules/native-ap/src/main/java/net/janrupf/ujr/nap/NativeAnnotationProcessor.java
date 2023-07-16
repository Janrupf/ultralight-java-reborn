package net.janrupf.ujr.nap;

import net.janrupf.ujr.nap.gen.CPPCodeGenerator;
import net.janrupf.ujr.nap.util.MemberUtil;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Annotation processor that generates native accessors for annotated elements.
 */
public class NativeAnnotationProcessor implements Processor {
    private final TargetCollector targetCollector;
    private CPPCodeGenerator codeGenerator;

    private ProcessingEnvironment processingEnvironment;

    public NativeAnnotationProcessor() {
        this.targetCollector = new TargetCollector();
    }

    @Override
    public Set<String> getSupportedOptions() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList(
                "net.janrupf.ujr.platform.jni.ffi.NativeAccess",
                "net.janrupf.ujr.platform.jni.ffi.NativeAccessOther"
        ));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        this.processingEnvironment = processingEnv;
        this.codeGenerator = new CPPCodeGenerator(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        boolean claim = false;

        for (TypeElement annotation : annotations) {
            if (annotation.getQualifiedName().contentEquals("net.janrupf.ujr.platform.jni.ffi.NativeAccess")) {
                // Collect all elements annotated with @NativeAccess
                for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                    targetCollector.collectNativeAccess(element);
                    claim = true;
                }
            } else if (annotation.getQualifiedName().contentEquals("net.janrupf.ujr.platform.jni.ffi.NativeAccessOther")) {
                // Collect all elements annotated with @NativeAccessOther
                // This is a bit special, since the annotated element is not the target

                for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                    List<? extends AnnotationMirror> mirrors = element.getAnnotationMirrors();

                    for (AnnotationMirror mirror : mirrors) {
                        if (!mirror.getAnnotationType().equals(annotation.asType())) {
                            // Not the annotation we are looking for
                            continue;
                        }

                        ExecutableElement valueMethod = MemberUtil.findMethod(annotation, "value");

                        // Get the value
                        Map<? extends ExecutableElement, ? extends AnnotationValue> values = mirror.getElementValues();
                        AnnotationValue targetClasses = values.get(valueMethod);

                        //noinspection unchecked
                        for (AnnotationValue targetClass : (List<? extends AnnotationValue>) targetClasses.getValue()) {
                            // The target class is always a TypeMirror
                            TypeMirror targetClassType = (TypeMirror) targetClass.getValue();
                            TypeElement targetClassElement =
                                    (TypeElement) processingEnvironment.getTypeUtils().asElement(targetClassType);

                            // Add all fields of the target type to the target collector
                            for (VariableElement field : ElementFilter.fieldsIn(targetClassElement.getEnclosedElements())) {
                                System.out.println("X");
                                targetCollector.collectNativeAccess(field);
                            }
                        }
                    }

                    claim = true;
                }
            }
        }

        if (roundEnv.processingOver()) {
            // Generate the native accessors
            codeGenerator.generate(processingEnvironment, targetCollector);
        }

        return claim;
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return null;
    }
}
