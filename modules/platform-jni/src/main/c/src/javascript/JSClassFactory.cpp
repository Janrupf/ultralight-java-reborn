#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCClassDefinition_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSClass_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSClassFactory.h"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSStaticFunction_native_access.hpp"
#include "net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSStaticValue_native_access.hpp"

#include <JavaScriptCore/JSObjectRef.h>

#include <cstring>
#include <stdexcept>
#include <vector>

#include "ujr/javascript/JSClass.hpp"
#include "ujr/javascript/JSJavaClassCallbacks.hpp"
#include "ujr/javascript/JSJavaPrivateData.hpp"
#include "ujr/javascript/JSUtil.hpp"
#include "ujr/util/JniEntryGuard.hpp"
#include "ujr/util/JniEnv.hpp"

JNIEXPORT jobject JNICALL Java_net_janrupf_ujr_platform_jni_impl_javascript_JNIJSCJSClassFactory_nativeNewClass(
    JNIEnv *env, jclass, jobject definition
) {
    return ujr::jni_entry_guard(env, [&](auto env) {
        using ujr::native_access::JNIJSCClassDefinition;
        using ujr::native_access::JNIJSCJSClass;
        using ujr::native_access::JNIJSCJSStaticValue;
        using ujr::native_access::JNIJSCJSStaticFunction;

        auto shared_data = new ujr::JSClassJavaSharedData();

        auto name = JNIJSCClassDefinition::NAME.get(env, definition).to_utf8();

        JSClassDefinition js_definition = kJSClassDefinitionEmpty;
        js_definition.className = name.c_str();
        js_definition.version = 1000;
        js_definition.attributes
            = ujr::JSUtil::property_attributes_to_js(env, JNIJSCClassDefinition::ATTRIBUTES.get(env, definition));

        auto j_parent_class = JNIJSCClassDefinition::PARENT_CLASS.get(env, definition);
        if (j_parent_class.is_valid()) {
            js_definition.parentClass = reinterpret_cast<JSClassRef>(JNIJSCJSClass::HANDLE.get(env, j_parent_class));
        }

        auto j_static_values = JNIJSCClassDefinition::STATIC_VALUES.get(env, definition);
        if (j_static_values.is_valid()) {
            jsize static_value_count = env->GetArrayLength(j_static_values);
            shared_data->static_function_callbacks.reserve(static_value_count);

            auto *static_values = new JSStaticValueEx[static_value_count + 1];
            std::memset(&static_values[static_value_count], 0, sizeof(JSStaticValueEx));

            for (jsize i = 0; i < static_value_count; i++) {
                auto j_static_value = env.wrap_argument(env->GetObjectArrayElement(j_static_values, i));

                auto value_name = JNIJSCJSStaticValue::NAME.get(env, j_static_value).to_utf8();
                auto value_attributes = JNIJSCJSStaticValue::ATTRIBUTES.get(env, j_static_value);

                auto value_getter = JNIJSCJSStaticValue::GET_PROPERTY.get(env, j_static_value);
                auto value_setter = JNIJSCJSStaticValue::SET_PROPERTY.get(env, j_static_value);

                auto inserted = shared_data->static_property_callbacks.emplace(
                    std::move(value_name),
                    ujr::JSClassJavaProperty { value_getter.clone_as_global(), value_setter.clone_as_global() }
                );

                static_values[i].name = inserted.first->first.c_str();
                static_values[i].attributes = ujr::JSUtil::property_attributes_to_js(env, value_attributes);
                static_values[i].getPropertyEx
                    = value_getter.is_valid() ? &ujr::JSJavaClassCallbacks::get_static_property : nullptr;
                static_values[i].setPropertyEx
                    = value_setter.is_valid() ? &ujr::JSJavaClassCallbacks::set_static_property : nullptr;
            }

            js_definition.staticValuesEx = static_values;
        }

        auto j_static_functions = JNIJSCClassDefinition::STATIC_FUNCTIONS.get(env, definition);
        if (j_static_functions.is_valid()) {
            jsize static_function_count = env->GetArrayLength(j_static_functions);
            shared_data->static_function_callbacks.reserve(static_function_count);

            auto *static_functions = new JSStaticFunctionEx[static_function_count + 1];
            std::memset(&static_functions[static_function_count], 0, sizeof(JSStaticFunctionEx));

            for (jsize i = 0; i < static_function_count; i++) {
                auto j_static_function = env.wrap_argument(env->GetObjectArrayElement(j_static_functions, i));

                auto function_name = JNIJSCJSStaticFunction::NAME.get(env, j_static_function).to_utf8();
                auto function_attributes = JNIJSCJSStaticFunction::ATTRIBUTES.get(env, j_static_function);

                auto function_callback = JNIJSCJSStaticFunction::CALL_AS_FUNCTION.get(env, j_static_function);

                auto inserted = shared_data->static_function_callbacks.emplace(
                    std::move(function_name),
                    std::move(function_callback.clone_as_global())
                );

                static_functions[i].name = inserted.first->first.c_str();
                static_functions[i].attributes = ujr::JSUtil::property_attributes_to_js(env, function_attributes);
                static_functions[i].callAsFunctionEx = &ujr::JSJavaClassCallbacks::call_as_function;
            }

            js_definition.staticFunctionsEx = static_functions;
        }

        {
            auto j_initialize = JNIJSCClassDefinition::INITIALIZE.get(env, definition);
            if (j_initialize.is_valid()) {
                shared_data->initialize_callback = j_initialize.clone_as_global();
                js_definition.initializeEx = &ujr::JSJavaClassCallbacks::initialize;
            }
        }

        {
            auto j_finalize = JNIJSCClassDefinition::FINALIZE.get(env, definition);
            if (j_finalize.is_valid()) {
                shared_data->finalize_callback = j_finalize.clone_as_global();
                js_definition.finalizeEx = &ujr::JSJavaClassCallbacks::finalize;
            }
        }

        {
            auto j_has_property = JNIJSCClassDefinition::HAS_PROPERTY.get(env, definition);
            if (j_has_property.is_valid()) {
                shared_data->has_property_callback = j_has_property.clone_as_global();
                js_definition.hasPropertyEx = &ujr::JSJavaClassCallbacks::has_property;
            }
        }

        {
            auto j_get_property = JNIJSCClassDefinition::GET_PROPERTY.get(env, definition);
            if (j_get_property.is_valid()) {
                shared_data->get_property_callback = j_get_property.clone_as_global();
                js_definition.getPropertyEx = &ujr::JSJavaClassCallbacks::get_property;
            }
        }

        {
            auto j_set_property = JNIJSCClassDefinition::SET_PROPERTY.get(env, definition);
            if (j_set_property.is_valid()) {
                shared_data->set_property_callback = j_set_property.clone_as_global();
                js_definition.setPropertyEx = &ujr::JSJavaClassCallbacks::set_property;
            }
        }

        {
            auto j_delete_property = JNIJSCClassDefinition::DELETE_PROPERTY.get(env, definition);
            if (j_delete_property.is_valid()) {
                shared_data->delete_property_callback = j_delete_property.clone_as_global();
                js_definition.deletePropertyEx = &ujr::JSJavaClassCallbacks::delete_property;
            }
        }

        {
            auto j_get_property_names = JNIJSCClassDefinition::GET_PROPERTY_NAMES.get(env, definition);
            if (j_get_property_names.is_valid()) {
                shared_data->get_property_names_callback = j_get_property_names.clone_as_global();
                js_definition.getPropertyNamesEx = &ujr::JSJavaClassCallbacks::get_property_names;
            }
        }

        {
            auto j_call_as_function = JNIJSCClassDefinition::CALL_AS_FUNCTION.get(env, definition);
            if (j_call_as_function.is_valid()) {
                shared_data->call_as_function_callback = j_call_as_function.clone_as_global();
                js_definition.callAsFunctionEx = &ujr::JSJavaClassCallbacks::call_as_function;
            }
        }

        {
            auto j_call_as_constructor = JNIJSCClassDefinition::CALL_AS_CONSTRUCTOR.get(env, definition);
            if (j_call_as_constructor.is_valid()) {
                shared_data->call_as_constructor_callback = j_call_as_constructor.clone_as_global();
                js_definition.callAsConstructorEx = &ujr::JSJavaClassCallbacks::call_as_constructor;
            }
        }

        {
            auto j_has_instance = JNIJSCClassDefinition::HAS_INSTANCE.get(env, definition);
            if (j_has_instance.is_valid()) {
                shared_data->has_instance_callback = j_has_instance.clone_as_global();
                js_definition.hasInstanceEx = &ujr::JSJavaClassCallbacks::has_instance;
            }
        }

        {
            auto j_convert_to_type = JNIJSCClassDefinition::CONVERT_TO_TYPE.get(env, definition);
            if (j_convert_to_type.is_valid()) {
                shared_data->convert_to_type_callback = j_convert_to_type.clone_as_global();
                js_definition.convertToTypeEx = &ujr::JSJavaClassCallbacks::convert_to_type;
            }
        }

        js_definition.privateData = shared_data;

        // TODO: Memory leak! The shared data pointer can never be freed, because WebCore does not provide
        //      a finalizer for JSClassRef.
        JSClassRef js_class = JSClassCreate(&js_definition);

        delete[] js_definition.staticValuesEx;
        delete[] js_definition.staticFunctionsEx;

        if (!js_class) {
            delete shared_data;
            throw std::runtime_error("Class could not be created");
        }

        return ujr::JSClass::wrap(env, js_class).leak();
    });
}