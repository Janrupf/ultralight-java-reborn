package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.api.javascript.callbacks.*;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSClassFactory;
import net.janrupf.ujr.core.util.ApiProvider;

import java.util.*;

/**
 * This class is used to build {@link JSClass} instances.
 */
public class JSClassBuilder {
    private static final ApiProvider<JSCJSClassFactory> FACTORY_PROVIDER = new ApiProvider<>(JSCJSClassFactory.class);

    private final String name;
    private final Set<JSClassAttribute> attributes;
    private final Set<JSStaticValue> staticValues;
    private final Set<JSStaticFunction> staticFunctions;

    private JSClass parentClass;

    private JSObjectInitializeCallback initializeCallback;
    private JSObjectFinalizeCallback finalizeCallback;
    private JSObjectHasPropertyCallback hasPropertyCallback;
    private JSObjectGetPropertyCallback getPropertyCallback;
    private JSObjectSetPropertyCallback setPropertyCallback;
    private JSObjectDeletePropertyCallback deletePropertyCallback;
    private JSObjectGetPropertyNamesCallback getPropertyNamesCallback;
    private JSObjectCallAsFunctionCallback callAsFunctionCallback;
    private JSObjectCallAsConstructorCallback callAsConstructorCallback;
    private JSObjectHasInstanceCallback hasInstanceCallback;
    private JSObjectConvertToTypeCallback convertToTypeCallback;

    /**
     * Creates a new builder for the given class name.
     *
     * @param name the name of the class
     */
    public JSClassBuilder(String name) {
        Objects.requireNonNull(name, "The class name must not be null");

        this.name = name;
        this.attributes = EnumSet.noneOf(JSClassAttribute.class);
        this.staticValues = new HashSet<>();
        this.staticFunctions = new HashSet<>();
    }

    /**
     * Adds the given attribute to the class.
     *
     * @param attribute the attribute to add
     * @return this
     */
    public JSClassBuilder attribute(JSClassAttribute attribute) {
        attributes.add(attribute);
        return this;
    }

    /**
     * Sets the parent class of the class.
     *
     * @param parentClass the parent class
     * @return this
     */
    public JSClassBuilder parentClass(JSClass parentClass) {
        this.parentClass = parentClass;
        return this;
    }

    /**
     * Adds a static value to the class.
     *
     * @param name        the name of the property
     * @param getProperty the callback to get the property
     * @param setProperty the callback to set the property
     * @param attributes  the attributes of the property
     * @return this
     */
    public JSClassBuilder staticValue(
            String name,
            JSObjectGetPropertyCallback getProperty,
            JSObjectSetPropertyCallback setProperty,
            JSPropertyAttribute... attributes
    ) {
        EnumSet<JSPropertyAttribute> attributeSet = EnumSet.noneOf(JSPropertyAttribute.class);
        if (attributes != null) {
            attributeSet.addAll(Arrays.asList(attributes));
        }

        return staticValue(name, getProperty, setProperty, attributeSet);
    }

    /**
     * Adds a static value to the class.
     *
     * @param name        the name of the property
     * @param getProperty the callback to get the property
     * @param setProperty the callback to set the property
     * @param attributes  the attributes of the property
     * @return this
     */
    public JSClassBuilder staticValue(
            String name,
            JSObjectGetPropertyCallback getProperty,
            JSObjectSetPropertyCallback setProperty,
            Set<JSPropertyAttribute> attributes
    ) {
        Objects.requireNonNull(name, "The property name must not be null");

        staticValues.add(new JSStaticValue(
                name,
                getProperty,
                setProperty,
                (attributes == null || attributes.isEmpty())
                        ? EnumSet.noneOf(JSPropertyAttribute.class)
                        : EnumSet.copyOf(attributes)
        ));

        return this;
    }

    /**
     * Adds a static function to the class.
     *
     * @param name           the name of the function
     * @param callAsFunction the callback to call the function
     * @param attributes     the attributes of the function
     * @return this
     */
    public JSClassBuilder staticFunction(
            String name,
            JSObjectCallAsFunctionCallback callAsFunction,
            JSPropertyAttribute... attributes
    ) {
        EnumSet<JSPropertyAttribute> attributeSet = EnumSet.noneOf(JSPropertyAttribute.class);
        if (attributes != null) {
            attributeSet.addAll(Arrays.asList(attributes));
        }

        return staticFunction(name, callAsFunction, attributeSet);
    }

    /**
     * Adds a static function to the class.
     *
     * @param name           the name of the function
     * @param callAsFunction the callback to call the function
     * @param attributes     the attributes of the function
     * @return this
     */
    public JSClassBuilder staticFunction(
            String name,
            JSObjectCallAsFunctionCallback callAsFunction,
            Set<JSPropertyAttribute> attributes
    ) {
        Objects.requireNonNull(name, "The property name must not be null");
        Objects.requireNonNull(callAsFunction, "The callAsFunction callback must not be null");

        staticFunctions.add(new JSStaticFunction(
                name,
                callAsFunction,
                (attributes == null || attributes.isEmpty())
                        ? EnumSet.noneOf(JSPropertyAttribute.class)
                        : EnumSet.copyOf(attributes)
        ));

        return this;
    }

    /**
     * Sets the callback to call when an object of the class is initialized.
     *
     * @param initializeCallback the callback to call when an object of the class is initialized
     * @return this
     */
    public JSClassBuilder onInitialize(JSObjectInitializeCallback initializeCallback) {
        this.initializeCallback = initializeCallback;
        return this;
    }

    /**
     * Sets the callback to call when an object of the class is finalized.
     *
     * @param finalizeCallback the callback to call when an object of the class is finalized
     * @return this
     */
    public JSClassBuilder onFinalize(JSObjectFinalizeCallback finalizeCallback) {
        this.finalizeCallback = finalizeCallback;
        return this;
    }

    /**
     * Sets the callback to call when checking of an object of the class has a property.
     *
     * @param hasPropertyCallback the callback to call when checking of an object of the class has a property
     * @return this
     */
    public JSClassBuilder onHasProperty(JSObjectHasPropertyCallback hasPropertyCallback) {
        this.hasPropertyCallback = hasPropertyCallback;
        return this;
    }

    /**
     * Sets the callback to call when a property of an object of the class is retrieved.
     *
     * @param getPropertyCallback the callback to call when a property of an object of the class is retrieved
     * @return this
     */
    public JSClassBuilder onGetProperty(JSObjectGetPropertyCallback getPropertyCallback) {
        this.getPropertyCallback = getPropertyCallback;
        return this;
    }

    /**
     * Sets the callback to call when a property of an object of the class is set.
     *
     * @param setPropertyCallback the callback to call when a property of an object of the class is set
     * @return this
     */
    public JSClassBuilder onSetProperty(JSObjectSetPropertyCallback setPropertyCallback) {
        this.setPropertyCallback = setPropertyCallback;
        return this;
    }

    /**
     * Sets the callback to call when a property of an object of the class is deleted.
     *
     * @param deletePropertyCallback the callback to call when a property of an object of the class is deleted
     * @return this
     */
    public JSClassBuilder onDeleteProperty(JSObjectDeletePropertyCallback deletePropertyCallback) {
        this.deletePropertyCallback = deletePropertyCallback;
        return this;
    }

    /**
     * Sets the callback to call when the properties of an object of the class are enumerated.
     *
     * @param getPropertyNamesCallback the callback to call when the properties of an object of the class are enumerated
     * @return this
     */
    public JSClassBuilder onGetPropertyNames(JSObjectGetPropertyNamesCallback getPropertyNamesCallback) {
        this.getPropertyNamesCallback = getPropertyNamesCallback;
        return this;
    }

    /**
     * Sets the callback to call when an object of the class is called as a function.
     *
     * @param callAsFunctionCallback the callback to call when an object of the class is called as a function
     * @return this
     */
    public JSClassBuilder onCallAsFunction(JSObjectCallAsFunctionCallback callAsFunctionCallback) {
        this.callAsFunctionCallback = callAsFunctionCallback;
        return this;
    }

    /**
     * Sets the callback to call when an object of the class is called as a constructor.
     *
     * @param callAsConstructorCallback the callback to call when an object of the class is called as a constructor
     * @return this
     */
    public JSClassBuilder onCallAsConstructor(JSObjectCallAsConstructorCallback callAsConstructorCallback) {
        this.callAsConstructorCallback = callAsConstructorCallback;
        return this;
    }

    /**
     * Sets the callback to call when checking of an object of this class is an instance of another class.
     *
     * @param hasInstanceCallback the callback to call when checking of an object of this class is an instance of another class
     * @return this
     */
    public JSClassBuilder onHasInstance(JSObjectHasInstanceCallback hasInstanceCallback) {
        this.hasInstanceCallback = hasInstanceCallback;
        return this;
    }

    /**
     * Sets the callback to call when an object of the class is converted to another type.
     *
     * @param convertToTypeCallback the callback to call when an object of the class is converted to another type
     * @return this
     */
    public JSClassBuilder onConvertToType(JSObjectConvertToTypeCallback convertToTypeCallback) {
        this.convertToTypeCallback = convertToTypeCallback;
        return this;
    }

    /**
     * Retrieves the name of the class.
     *
     * @return the name of the class
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the parent class of the class.
     *
     * @return the parent class of the class
     */
    public JSClass getParentClass() {
        return parentClass;
    }

    /**
     * Retrieves the attributes of the class.
     *
     * @return the attributes of the class
     */
    public Set<JSClassAttribute> getAttributes() {
        return attributes;
    }

    /**
     * Retrieves the static values of the class.
     *
     * @return the static values of the class
     */
    public Set<JSStaticValue> getStaticValues() {
        return staticValues;
    }

    /**
     * Retrieves the static functions of the class.
     *
     * @return the static functions of the class
     */
    public Set<JSStaticFunction> getStaticFunctions() {
        return staticFunctions;
    }

    /**
     * Retrieves the callback to call when an object of the class is initialized.
     *
     * @return the callback to call when an object of the class is initialized
     */
    public JSObjectInitializeCallback getInitializeCallback() {
        return initializeCallback;
    }

    /**
     * Retrieves the callback to call when an object of the class is finalized.
     *
     * @return the callback to call when an object of the class is finalized
     */
    public JSObjectFinalizeCallback getFinalizeCallback() {
        return finalizeCallback;
    }

    /**
     * Retrieves the callback to call when checking of an object of the class has a property.
     *
     * @return the callback to call when checking of an object of the class has a property
     */
    public JSObjectHasPropertyCallback getHasPropertyCallback() {
        return hasPropertyCallback;
    }

    /**
     * Retrieves the callback to call when a property of an object of the class is retrieved.
     *
     * @return the callback to call when a property of an object of the class is retrieved
     */
    public JSObjectGetPropertyCallback getGetPropertyCallback() {
        return getPropertyCallback;
    }

    /**
     * Retrieves the callback to call when a property of an object of the class is set.
     *
     * @return the callback to call when a property of an object of the class is set
     */
    public JSObjectSetPropertyCallback getSetPropertyCallback() {
        return setPropertyCallback;
    }

    /**
     * Retrieves the callback to call when a property of an object of the class is deleted.
     *
     * @return the callback to call when a property of an object of the class is deleted
     */
    public JSObjectDeletePropertyCallback getDeletePropertyCallback() {
        return deletePropertyCallback;
    }

    /**
     * Retrieves the callback to call when the properties of an object of the class are enumerated.
     *
     * @return the callback to call when the properties of an object of the class are enumerated
     */
    public JSObjectGetPropertyNamesCallback getGetPropertyNamesCallback() {
        return getPropertyNamesCallback;
    }

    /**
     * Retrieves the callback to call when an object of the class is called as a function.
     *
     * @return the callback to call when an object of the class is called as a function
     */
    public JSObjectCallAsFunctionCallback getCallAsFunctionCallback() {
        return callAsFunctionCallback;
    }

    /**
     * Retrieves the callback to call when an object of the class is called as a constructor.
     *
     * @return the callback to call when an object of the class is called as a constructor
     */
    public JSObjectCallAsConstructorCallback getCallAsConstructorCallback() {
        return callAsConstructorCallback;
    }

    /**
     * Retrieves the callback to call when checking of an object of this class is an instance of another class.
     *
     * @return the callback to call when checking of an object of this class is an instance of another class
     */
    public JSObjectHasInstanceCallback getHasInstanceCallback() {
        return hasInstanceCallback;
    }

    /**
     * Retrieves the callback to call when an object of the class is converted to another type.
     *
     * @return the callback to call when an object of the class is converted to another type
     */
    public JSObjectConvertToTypeCallback getConvertToTypeCallback() {
        return convertToTypeCallback;
    }

    /**
     * Builds the class.
     *
     * @return the built class
     */
    public JSClass build() {
        return new JSClass(FACTORY_PROVIDER.require().newClass(this));
    }

    /**
     * Internal helper class to store static values.
     */
    public class JSStaticValue {
        private final String name;
        private final JSObjectGetPropertyCallback getProperty;
        private final JSObjectSetPropertyCallback setProperty;
        private final Set<JSPropertyAttribute> attributes;

        private JSStaticValue(String name, JSObjectGetPropertyCallback getProperty, JSObjectSetPropertyCallback setProperty, Set<JSPropertyAttribute> attributes) {
            this.name = name;
            this.getProperty = getProperty;
            this.setProperty = setProperty;
            this.attributes = attributes;
        }

        public String getName() {
            return name;
        }

        public JSObjectGetPropertyCallback getGetPropertyCallback() {
            return getProperty;
        }

        public JSObjectSetPropertyCallback getSetPropertyCallback() {
            return setProperty;
        }

        public Set<JSPropertyAttribute> getAttributes() {
            return attributes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof JSStaticValue)) return false;
            JSStaticValue that = (JSStaticValue) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Internal helper class to store static functions.
     */
    public class JSStaticFunction {
        private final String name;
        private final JSObjectCallAsFunctionCallback callAsFunction;
        private final Set<JSPropertyAttribute> attributes;

        private JSStaticFunction(String name, JSObjectCallAsFunctionCallback callAsFunction, Set<JSPropertyAttribute> attributes) {
            this.name = name;
            this.callAsFunction = callAsFunction;
            this.attributes = attributes;
        }

        public String getName() {
            return name;
        }

        public JSObjectCallAsFunctionCallback getCallAsFunctionCallback() {
            return callAsFunction;
        }

        public Set<JSPropertyAttribute> getAttributes() {
            return attributes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof JSStaticFunction)) return false;
            JSStaticFunction that = (JSStaticFunction) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
