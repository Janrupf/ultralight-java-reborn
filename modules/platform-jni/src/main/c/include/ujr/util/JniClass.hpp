#pragma once

#include "JniException.hpp"

#include <jni.h>

#include <mutex>
#include <optional>

#include "ujr/util/JniName.hpp"
#include "ujr/util/JniRef.hpp"
#include "ujr/util/JniSignature.hpp"
#include "ujr/util/JniType.hpp"

namespace ujr {
    namespace _internal {
        jclass get_class_not_found_exception_class(const JniEnv &env);
    }

    /**
     * Helper class to deal with JNI class references.
     *
     * @tparam Name the name of the class, in java binary notation
     * @tparam ObjectType the C++ JNI type of the object this class represents
     */
    template<JniClassName Name, typename ObjectType = jobject>
        requires IsJniType<ObjectType>
    class JniClass {
    public:
        /**
         * The C++ JNI type of the object this class represents.
         */
        using AsJniType = JniType<ObjectType>::Type;

        /**
         * The JNI binary name of the class.
         */
        static constexpr JniClassName AsJniName = JniClassSignature<Name>::Signature;

    private:
        std::mutex clazz_mutex;
        std::optional<JniWeakRef<jclass>> clazz;

        /**
         * Performs the logic required to look up a class.
         *
         * The should_throw parameter controls whether a C++ exception should be thrown if the class
         * could not be found. For other errors, a C++ exception is always thrown.
         *
         * @param env the JNI environment to use
         * @param should_throw throw an exception if the class could not be found
         * @return
         */
        JniLocalRef<jclass> do_get(const JniEnv &env, bool should_throw) {
            std::unique_lock<std::mutex> clazz_lock(clazz_mutex);

            if (clazz.has_value()) {
                auto ref_clazz = clazz->clone_as_local(env);

                if (ref_clazz) {
                    // Class has not been garbage collected, we are good to go
                    return std::move(ref_clazz); // NOLINT(cppcoreguidelines-pro-type-static-cast-downcast)
                }
            }

            // Class has been garbage collected (or never been loaded before), we need to load it (again)
            auto ref_clazz = env->FindClass(Name);

            if (env->ExceptionCheck()) {
                if (!env->IsSameObject(ref_clazz, nullptr)) {
                    // In case we _somehow_ got a class reference, we need to delete it
                    env->DeleteLocalRef(ref_clazz);
                }

                auto thrown_exception = JniLocalRef<jthrowable>::wrap(env, env->ExceptionOccurred());
                env->ExceptionClear();

                if (!should_throw
                    && env->IsInstanceOf(thrown_exception, _internal::get_class_not_found_exception_class(env))) {
                    // ClassNotFoundException is not a fatal error when we should not throw, so we can just return
                    // nullptr
                    return JniLocalRef<jclass>::wrap(env, nullptr);
                } else {
                    // Some other exception occurred, we need to rethrow it
                    JniException::throw_java_as_cpp(std::move(thrown_exception));
                }
            }

            // Class found, cache it
            auto local_ref = JniLocalRef<jclass>::wrap(env, ref_clazz);
            clazz.emplace(local_ref.clone_as_weak());

            return std::move(local_ref);
        }

    public:
        /**
         * Creates a new JniClass instance.
         */
        constexpr explicit JniClass() noexcept = default;

        /**
         * Retrieves the JNI class reference.
         *
         * @param env the JNI environment to use
         * @return the JNI class reference
         * @throws JniException if the class could not be found or loaded
         */
        JniLocalRef<jclass> get(const JniEnv &env) { return std::move(do_get(env, true)); }

        /**
         * Retrieves the JNI class reference.
         *
         * This method is only allowed to be called if no exception is pending.
         *
         * @param env  the JNI environment to use
         * @return the JNI class reference, or nullptr if the class could not be found or loaded
         */
        JniLocalRef<jclass> maybe_get(const JniEnv &env) { return std::move(do_get(env, false)); }
    };

    /**
     * Concept to check whether a type is a JniClass.
     *
     * @tparam T the type to check
     */
    template<typename T>
    concept IsJniClass
        = requires(T t) { []<JniClassName Name, typename ObjectType>(const JniClass<Name, ObjectType> &clazz) {}(t); };

} // namespace ujr

#define DECLARE_JNI_CLASS(name, object_type, decl) static ::ujr::JniClass<name, object_type> decl
#define DECLARE_OBJECT_JNI_CLASS(name, decl) DECLARE_JNI_CLASS(name, jobject, decl)
