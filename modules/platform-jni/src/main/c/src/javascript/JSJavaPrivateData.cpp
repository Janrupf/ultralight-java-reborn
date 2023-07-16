#include "ujr/javascript/JSJavaPrivateData.hpp"

namespace ujr {
    JSJavaPrivateData::JSJavaPrivateData(
        std::shared_ptr<const JSClassJavaSharedData> shared_data,
        const JniEnv &env,
        const JniStrongRef<jobject> &java_private_data
    )
        : shared_data(std::move(shared_data))
        , java_private_data(java_private_data.clone_as_global(env)) {
    }

    JSJavaPrivateData::~JSJavaPrivateData() = default;

    const std::shared_ptr<const JSClassJavaSharedData> &JSJavaPrivateData::get_shared_data() const {
        return shared_data;
    }

    const JniGlobalRef<jobject> &JSJavaPrivateData::get_java_private_data() const { return java_private_data; }

    JSClassJavaSharedData::JSClassJavaSharedData()
        : static_property_callbacks()
        , static_function_callbacks()
        , initialize_callback(JniGlobalRef<jobject>::null())
        , finalize_callback(JniGlobalRef<jobject>::null())
        , has_property_callback(JniGlobalRef<jobject>::null())
        , get_property_callback(JniGlobalRef<jobject>::null())
        , set_property_callback(JniGlobalRef<jobject>::null())
        , delete_property_callback(JniGlobalRef<jobject>::null())
        , get_property_names_callback(JniGlobalRef<jobject>::null())
        , call_as_function_callback(JniGlobalRef<jobject>::null())
        , call_as_constructor_callback(JniGlobalRef<jobject>::null())
        , has_instance_callback(JniGlobalRef<jobject>::null())
        , convert_to_type_callback(JniGlobalRef<jobject>::null()) {}
} // namespace ujr