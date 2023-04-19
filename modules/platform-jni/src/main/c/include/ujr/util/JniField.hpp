#pragma once

#include <jni.h>

#include "ujr/util/JniClass.hpp"
#include "ujr/util/JniExceptionCheck.hpp"
#include "ujr/util/JniName.hpp"
#include "ujr/util/JniType.hpp"

namespace ujr {
    namespace _internal {
        template<typename T> struct JniFieldAccessor {};

#define DEFINE_JNI_FIELD_ACCESSOR(j_type, j_type_name)                                                                 \
    template<> struct JniFieldAccessor<j_type> {                                                                       \
        static j_type get(const JniEnv &env, jobject obj, jfieldID field) {                                            \
            return env->Get##j_type_name##Field(obj, field);                                                           \
        }                                                                                                              \
                                                                                                                       \
        static void set(const JniEnv &env, jobject obj, jfieldID field, j_type value) {                                \
            env->Set##j_type_name##Field(obj, field, value);                                                           \
        }                                                                                                              \
                                                                                                                       \
        static j_type get_static(const JniEnv &env, jclass clazz, jfieldID field) {                                    \
            return env->GetStatic##j_type_name##Field(clazz, field);                                                   \
        }                                                                                                              \
                                                                                                                       \
        static void set_static(const JniEnv &env, jclass clazz, jfieldID field, j_type value) {                        \
            env->SetStatic##j_type_name##Field(clazz, field, value);                                                   \
        }                                                                                                              \
    }

        DEFINE_JNI_FIELD_ACCESSOR(jboolean, Boolean);
        DEFINE_JNI_FIELD_ACCESSOR(jbyte, Byte);
        DEFINE_JNI_FIELD_ACCESSOR(jchar, Char);
        DEFINE_JNI_FIELD_ACCESSOR(jshort, Short);
        DEFINE_JNI_FIELD_ACCESSOR(jint, Int);
        DEFINE_JNI_FIELD_ACCESSOR(jlong, Long);
        DEFINE_JNI_FIELD_ACCESSOR(jfloat, Float);
        DEFINE_JNI_FIELD_ACCESSOR(jdouble, Double);
        DEFINE_JNI_FIELD_ACCESSOR(jobject, Object);

#undef DEFINE_JNI_FIELD_ACCESSOR

#define DEFINE_JNI_OBJECT_FIELD_ACCESSOR(j_type)                                                                       \
    template<> struct JniFieldAccessor<j_type> {                                                                       \
        static j_type get(const JniEnv &env, jobject obj, jfieldID field) {                                            \
            return reinterpret_cast<j_type>(env->GetObjectField(obj, field));                                          \
        }                                                                                                              \
                                                                                                                       \
        static void set(const JniEnv &env, jobject obj, jfieldID field, j_type value) {                                \
            env->SetObjectField(obj, field, value);                                                                    \
        }                                                                                                              \
                                                                                                                       \
        static j_type get_static(const JniEnv &env, jclass clazz, jfieldID field) {                                    \
            return reinterpret_cast<j_type>(env->GetStaticObjectField(clazz, field));                                  \
        }                                                                                                              \
                                                                                                                       \
        static void set_static(const JniEnv &env, jclass clazz, jfieldID field, j_type value) {                        \
            env->SetStaticObjectField(clazz, field, value);                                                            \
        }                                                                                                              \
    }

        DEFINE_JNI_OBJECT_FIELD_ACCESSOR(jstring);
        DEFINE_JNI_OBJECT_FIELD_ACCESSOR(jclass);
        DEFINE_JNI_OBJECT_FIELD_ACCESSOR(jthrowable);

#undef DEFINE_JNI_OBJECT_FIELD_ACCESSOR
    } // namespace _internal

    /**
     * Helper class to represent a JNI field.
     *
     * @tparam Static whether the field is static
     * @tparam Name the name of the field
     * @tparam Class the class the field is defined in
     * @tparam T the type of the field
     */
    template<bool Static, JniMemberName Name, typename Class, typename T>
        requires(IsJniClass<Class> && IsJniType<T>)
    class JniField {
    protected:
        Class &clazz;

        jfieldID id;

        /**
         * Constructs a new JNI field.
         *
         * @param clazz the class the field is defined in
         */
        constexpr explicit JniField(Class &clazz) noexcept
            : clazz(clazz)
            , id(nullptr) {}

        /**
         * Resolves the id of this field.
         *
         * @param env the JNI environment to use
         */
        void resolve(const JniEnv &env) {
            if (id) {
                return;
            }

            auto java_clazz = clazz.get(env);
            if constexpr (Static) {
                id = env->GetStaticFieldID(java_clazz, Name, JniType<T>::Name);
            } else {
                id = env->GetFieldID(java_clazz, Name, JniType<T>::Name);
            }

            JniExceptionCheck::throw_if_pending(env);
        }
    };

    /**
     * Helper class to represent a static JNI field.
     *
     * @tparam Class the class the field is defined in
     * @tparam Name the name of the field
     * @tparam T the type of the field
     */
    template<typename Class, JniMemberName Name, typename T>
        requires(IsJniClass<Class> && IsJniType<T>)
    class JniStaticField : public JniField<true, Name, Class, T> {
    public:
        /**
         * Constructs a new JNI static field.
         *
         * @param clazz the class the field is defined in
         */
        constexpr explicit JniStaticField(Class &clazz) noexcept
            : JniField<true, Name, Class, T>(clazz) {}

        /**
         * Retrieves the value of this field.
         *
         * @param env the JNI environment to use
         * @return the value of this field
         */
        auto get(const JniEnv &env) {
            this->resolve(env);

            auto val = static_cast<typename JniType<T>::Type>(
                _internal::JniFieldAccessor<typename JniType<T>::Type>::get_static(
                    env,
                    this->clazz.get(env),
                    this->id
                )
            );

            JniExceptionCheck::throw_if_pending(env);
            return JniTypeConverter<T>::convert_from_jni(env, val);
        }

        /**
         * Sets the value of this field.
         *
         * @tparam V the type of the value to set
         * @param env the JNI environment to use
         * @param value the value to set
         */
        template<typename V>
        void set(const JniEnv &env, V value)
            requires IsJniConvertibleTo<V, T>
        {
            this->resolve(env);

            auto val = JniTypeConverter<T>::convert_to_jni(value);
            _internal::JniFieldAccessor<typename JniType<T>::Type>::set_static(
                env,
                this->clazz.get(env),
                this->id,
                val
            );

            JniExceptionCheck::throw_if_pending(env);
        }
    };

    /**
     * Helper class to represent an instance JNI field.
     *
     * @tparam Class the class the field is defined in
     * @tparam Name the name of the field
     * @tparam T the type of the field
     */
    template<typename Class, JniMemberName Name, typename T>
        requires(IsJniClass<Class> && IsJniType<T>)
    class JniInstanceField : public JniField<false, Name, Class, T> {
    public:
        /**
         * Constructs a new JNI instance field.
         *
         * @param clazz the class the field is defined in
         */
        constexpr explicit JniInstanceField(Class &clazz) noexcept
            : JniField<false, Name, Class, T>(clazz) {}

        /**
         * Retrieves the value of this field.
         *
         * @param env the JNI environment to use
         * @param self the object to retrieve the field from
         * @return the value of this field
         */
        template<typename Self>
        auto get(const JniEnv &env, Self self)
            requires IsJniConvertibleTo<Self, typename JniType<Class>::Type>
        {
            this->resolve(env);

            auto obj = JniTypeConverter<typename JniType<Class>::Type>::convert_to_jni(self);
            auto val = static_cast<typename JniType<T>::Type>(
                _internal::JniFieldAccessor<typename JniType<T>::Type>::get(env, obj, this->id)
            );

            JniExceptionCheck::throw_if_pending(env);
            return JniTypeConverter<T>::convert_from_jni(env, val);
        }

        /**
         * Sets the value of this field.
         *
         * @tparam V the type of the value to set
         * @param env the JNI environment to use
         * @param obj the object to set the field on
         * @param value the value to set
         */
        template<typename V, typename Self>
        void set(const JniEnv &env, Self self, V value)
            requires(IsJniConvertibleTo<Self, typename JniType<Class>::Type> && IsJniConvertibleTo<V, T>)
        {
            this->resolve(env);

            auto obj = JniTypeConverter<typename JniType<Class>::Type>::convert_to_jni(self);
            auto val = JniTypeConverter<T>::convert_to_jni(value);
            _internal::JniFieldAccessor<typename JniType<T>::Type>::set(env, obj, this->id, val);

            JniExceptionCheck::throw_if_pending(env);
        }
    };
} // namespace ujr

#define DECLARE_JNI_INSTANCE_FIELD(clazz, member_name, type, decl)                                                     \
    static ::ujr::JniInstanceField<decltype(clazz), member_name, type> decl(clazz)
