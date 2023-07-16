#pragma once

#include <memory>
#include <string>
#include <unordered_map>

#include "ujr/util/JniRef.hpp"

namespace ujr {
    /**
     * Callbacks for a JavaScript property.
     */
    struct JSClassJavaProperty {
        JniGlobalRef<jobject> get_property;
        JniGlobalRef<jobject> set_property;
    };

    /**
     * Callbacks and lookup data for a JavaScript class.
     */
    struct JSClassJavaSharedData {
        explicit JSClassJavaSharedData();

        /**
         * Map of property names to property callbacks.
         */
        std::unordered_map<std::string, JSClassJavaProperty> static_property_callbacks;

        /**
         * Map of function names to function callbacks.
         */
        std::unordered_map<std::string, JniGlobalRef<jobject>> static_function_callbacks;

        JniGlobalRef<jobject> initialize_callback;
        JniGlobalRef<jobject> finalize_callback;
        JniGlobalRef<jobject> has_property_callback;
        JniGlobalRef<jobject> get_property_callback;
        JniGlobalRef<jobject> set_property_callback;
        JniGlobalRef<jobject> delete_property_callback;
        JniGlobalRef<jobject> get_property_names_callback;
        JniGlobalRef<jobject> call_as_function_callback;
        JniGlobalRef<jobject> call_as_constructor_callback;
        JniGlobalRef<jobject> has_instance_callback;
        JniGlobalRef<jobject> convert_to_type_callback;
    };

    class JSJavaPrivateData {
    private:
        std::shared_ptr<const JSClassJavaSharedData> shared_data;
        JniGlobalRef<jobject> java_private_data;

    public:
        explicit JSJavaPrivateData(
            std::shared_ptr<const JSClassJavaSharedData> shared_data,
            const JniEnv &env,
            const JniStrongRef<jobject> &java_private_data
        );

        ~JSJavaPrivateData();

        /**
         * Retrieves the shared data for the class.
         *
         * @return the shared data
         */
        [[nodiscard]] const std::shared_ptr<const JSClassJavaSharedData> &get_shared_data() const;

        /**
         * Retrieves the java private data.
         *
         * @return the java private data
         */
        [[nodiscard]] const JniGlobalRef<jobject> &get_java_private_data() const;
    };
} // namespace ujr
