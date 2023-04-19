#pragma once

#include <jni.h>

#include <utility>

#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniName.hpp"

namespace ujr {
    template<typename T> class JniLocalRef;

    /**
     * A type trait to get the JNI type of a type.
     *
     * @tparam T the type to get the JNI type of
     */
    template<typename T> struct JniType {
        using Type = typename T::AsJniType;
        using LocalRefType = JniLocalRef<Type>;

        static constexpr JniClassName Name = T::AsJniName;
    };

    /**
     * Concept which requires that a type has a JniType type alias.
     *
     * @tparam T the type to check
     */
    template<typename T>
    concept IsJniType = std::is_void_v<T> || requires(T t) {
        typename JniType<T>::Type;

        // This probably requires some explanation. The idea is that we want to
        // make sure that the JniType<T>::Name is a valid JniClassName. We do that by
        // defining a function that takes a JniClassName<N> and then calling it with
        // the JniType<T>::Name. This will cause a compile error if the JniType<T>::Name
        // is not a valid JniClassName. Since we are in a concept, this will cause the
        // concept to fail.
        []<size_t N>([[maybe_unused]] JniClassName<N> name) {}(JniType<T>::Name);
    };

    /**
     * Concept which requires that a type is a JNI type and has a LocalRefType type alias.
     *
     * @tparam T the type to check
     */
    template<typename T>
    concept IsJniRefType = IsJniType<T> && requires(T t) { typename JniType<T>::LocalRefType; };

    /**
     * Concept which requires that a type is a JNI value type and is not a JNI reference type.
     *
     * @tparam T the type to check
     */
    template<typename T>
    concept IsJniValueType = IsJniType<T> && !IsJniRefType<T> && !std::is_void_v<T>;

    template<typename T>
        requires IsJniType<T>
    class JniRef;

    /**
     * Concept which requires that a type is a JNI reference type wrapper.
     *
     * @tparam T the type to check
     */
    template<typename T>
    concept IsJniRefWrapper = requires(T t) {
        []<typename X>([[maybe_unused]] const JniRef<X> &ref)
            requires IsJniType<X>
        {}
        (t);
    };

    // JNI type SPECIALIZATIONS
#define JNI_VALUE_TYPE_SPECIALIZATION(j_type, class_name)                                                              \
    template<> struct JniType<j_type> {                                                                                \
        using Type = j_type;                                                                                           \
                                                                                                                       \
        static constexpr JniClassName Name = class_name;                                                               \
    };                                                                                                                 \
                                                                                                                       \
    template<> struct JniType<j_type##Array> {                                                                         \
        using Type = j_type##Array;                                                                                    \
        using LocalRefType = JniLocalRef<Type>;                                                                        \
                                                                                                                       \
        static constexpr JniClassName Name = "[" class_name;                                                           \
    }

    JNI_VALUE_TYPE_SPECIALIZATION(jboolean, "Z");
    JNI_VALUE_TYPE_SPECIALIZATION(jbyte, "B");
    JNI_VALUE_TYPE_SPECIALIZATION(jchar, "C");
    JNI_VALUE_TYPE_SPECIALIZATION(jshort, "S");
    JNI_VALUE_TYPE_SPECIALIZATION(jint, "I");
    JNI_VALUE_TYPE_SPECIALIZATION(jlong, "J");
    JNI_VALUE_TYPE_SPECIALIZATION(jfloat, "F");
    JNI_VALUE_TYPE_SPECIALIZATION(jdouble, "D");

#undef JNI_VALUE_TYPE_SPECIALIZATION

#define JNI_REFERENCE_TYPE_SPECIALIZATION(j_type, class_name)                                                          \
    template<> struct JniType<j_type> {                                                                                \
        using Type = j_type;                                                                                           \
        using LocalRefType = JniLocalRef<Type>;                                                                        \
                                                                                                                       \
        static constexpr JniClassName Name = "L" class_name ";";                                                       \
    }

    JNI_REFERENCE_TYPE_SPECIALIZATION(jobject, "java/lang/Object");
    JNI_REFERENCE_TYPE_SPECIALIZATION(jclass, "java/lang/Class");
    JNI_REFERENCE_TYPE_SPECIALIZATION(jstring, "java/lang/String");
    JNI_REFERENCE_TYPE_SPECIALIZATION(jarray, "java/lang/Object");
    JNI_REFERENCE_TYPE_SPECIALIZATION(jthrowable, "java/lang/Throwable");

#undef JNI_REFERENCE_TYPE_SPECIALIZATION

    template<> struct JniType<void> {
        using Type = void;
        using LocalRefType = void;

        static constexpr JniClassName Name = "V";
    };

    template<> struct JniType<jobjectArray> {
        using Type = jobjectArray;
        using LocalRefType = JniLocalRef<jobjectArray>;

        static constexpr JniClassName Name = "[Ljava/lang/Object;";
    };

    /**
     * Helper struct to convert a type to its JNI type.
     *
     * @tparam T the type to convert_to_jni
     */
    template<typename T>
        requires IsJniType<T>
    struct JniTypeConverter {
        /**
         * Converts a JNI value type to its JNI type.
         *
         * This is a no-op.
         *
         * @param t the value to convert
         * @return t
         */
        static typename JniType<T>::Type convert_to_jni(T t)
            requires(!IsJniRefWrapper<T>)
        {
            return t;
        }

        /**
         * Converts a JNI reference to its JNI type.
         *
         * @param t the reference to convert
         * @return the converted reference
         */
        static auto convert_to_jni(T t)
            requires IsJniRefWrapper<T>
        {
            return t.get();
        }

        /**
         * Converts a JNI value type from its JNI type.
         *
         * This is a no-op.
         *
         * @param env the JNI environment to use
         * @param t the value to convert
         * @return t
         */
        static T convert_from_jni([[maybe_unused]] const JniEnv &env, typename JniType<T>::Type t)
            requires(IsJniValueType<T>)
        {
            return t;
        }

        /**
         * Converts a JNI reference from its JNI type.
         *
         * @param env the JNI environment to use
         * @param t the reference to convert
         * @return the converted reference
         */
        static auto convert_from_jni(const JniEnv &env, typename JniType<T>::Type t)
            requires IsJniRefType<T>
        {
            return JniType<T>::LocalRefType::wrap(env, t);
        }
    };

    /**
     * Concept which requires that a type is convertible to its JNI type.
     *
     * @tparam T the type to check
     */
    template<typename T>
    concept IsJniConvertible = requires(T t) { JniTypeConverter<T>::convert_to_jni(t); };

    /**
     * Concept which requires that a type is JNI convertible to another type.
     *
     * @tparam Source the source type
     * @tparam Target the target type
     */
    template<typename Source, typename Target>
    concept IsJniConvertibleTo = requires(Source s) {
        { JniTypeConverter<Target>::convert_to_jni(s) } -> std::convertible_to<typename JniType<Target>::Type>;
    };
}; // namespace ujr
