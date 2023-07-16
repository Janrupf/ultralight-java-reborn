package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.api.javascript.JSClassAttribute;
import net.janrupf.ujr.api.javascript.JSClassBuilder;
import net.janrupf.ujr.api.javascript.JSPropertyAttribute;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSClass;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSClassFactory;
import net.janrupf.ujr.platform.jni.impl.javascript.callbacks.*;

public class JNIJSCJSClassFactory implements JSCJSClassFactory {
    @Override
    public JSCJSClass newClass(JSClassBuilder builder) {
        JNIJSCClassDefinition definition = new JNIJSCClassDefinition();

        definition.name = builder.getName();
        definition.attributes = builder.getAttributes().toArray(new JSClassAttribute[0]);
        definition.parentClass = builder.getParentClass() != null ? builder.getParentClass().getClazz() : null;

        if (!builder.getStaticValues().isEmpty()) {
            definition.staticValues = new JNIJSCJSStaticValue[builder.getStaticValues().size()];

            int i = 0;
            for (JSClassBuilder.JSStaticValue value : builder.getStaticValues()) {
                definition.staticValues[i] = new JNIJSCJSStaticValue();
                definition.staticValues[i].name = value.getName();
                definition.staticValues[i].attributes = value.getAttributes().toArray(new JSPropertyAttribute[0]);
                definition.staticValues[i].getProperty = new JNIJSCJSObjectGetPropertyCallback(value.getGetPropertyCallback());
                definition.staticValues[i].setProperty = new JNIJSCJSObjectSetPropertyCallback(value.getSetPropertyCallback());

                i++;
            }
        }

        if (!builder.getStaticFunctions().isEmpty()) {
            definition.staticFunctions = new JNIJSCJSStaticFunction[builder.getStaticFunctions().size()];

            int i = 0;
            for (JSClassBuilder.JSStaticFunction function : builder.getStaticFunctions()) {
                definition.staticFunctions[i] = new JNIJSCJSStaticFunction();
                definition.staticFunctions[i].name = function.getName();
                definition.staticFunctions[i].attributes = function.getAttributes().toArray(new JSPropertyAttribute[0]);
                definition.staticFunctions[i].callAsFunction = new JNIJSCJSObjectCallAsFunctionCallback(function.getCallAsFunctionCallback());

                i++;
            }
        }

        definition.initialize = builder.getInitializeCallback() != null ? new JNIJSCJSObjectInitializeCallback(builder.getInitializeCallback()) : null;
        definition.finalize = builder.getFinalizeCallback() != null ? new JNIJSCJSObjectFinalizeCallback(builder.getFinalizeCallback()) : null;
        definition.hasProperty = builder.getHasPropertyCallback() != null ? new JNIJSCJSObjectHasPropertyCallback(builder.getHasPropertyCallback()) : null;
        definition.getProperty = builder.getGetPropertyCallback() != null ? new JNIJSCJSObjectGetPropertyCallback(builder.getGetPropertyCallback()) : null;
        definition.setProperty = builder.getSetPropertyCallback() != null ? new JNIJSCJSObjectSetPropertyCallback(builder.getSetPropertyCallback()) : null;
        definition.deleteProperty = builder.getDeletePropertyCallback() != null ? new JNIJSCJSObjectDeletePropertyCallback(builder.getDeletePropertyCallback()) : null;
        definition.getPropertyNames = builder.getGetPropertyNamesCallback() != null ? new JNIJSCJSObjectGetPropertyNamesCallback(builder.getGetPropertyNamesCallback()) : null;
        definition.callAsFunction = builder.getCallAsFunctionCallback() != null ? new JNIJSCJSObjectCallAsFunctionCallback(builder.getCallAsFunctionCallback()) : null;
        definition.callAsConstructor = builder.getCallAsConstructorCallback() != null ? new JNIJSCJSObjectCallAsConstructorCallback(builder.getCallAsConstructorCallback()) : null;
        definition.hasInstance = builder.getHasInstanceCallback() != null ? new JNIJSCJSObjectHasInstanceCallback(builder.getHasInstanceCallback()) : null;
        definition.convertToType = builder.getConvertToTypeCallback() != null ? new JNIJSCJSObjectConvertToTypeCallback(builder.getConvertToTypeCallback()) : null;

        return nativeNewClass(definition);
    }

    private static native JSCJSClass nativeNewClass(JNIJSCClassDefinition definition);
}
