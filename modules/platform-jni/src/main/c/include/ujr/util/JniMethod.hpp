#pragma once

#include <jni.h>

#include "ujr/util/JniClass.hpp"
#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniExceptionCheck.hpp"
#include "ujr/util/JniName.hpp"
#include "ujr/util/JniSignature.hpp"
#include "ujr/util/JniType.hpp"

namespace ujr {
    namespace _internal {
        template<typename T> struct JniInvoker {};

#define DEFINE_JNI_INVOKER(j_type, j_type_name)                                                                        \
    template<> struct JniInvoker<j_type> {                                                                             \
        template<typename... Args> static j_type invoke(const JniEnv &env, jmethodID id, jobject obj, Args... args) {  \
            return env->Call##j_type_name##Method(obj, id, args...);                                                   \
        }                                                                                                              \
                                                                                                                       \
        template<typename... Args>                                                                                     \
        static j_type invoke_non_virtual(const JniEnv &env, jclass clazz, jmethodID id, jobject obj, Args... args) {   \
            return env->CallNonvirtual##j_type_name##Method(obj, clazz, id, args...);                                  \
        }                                                                                                              \
                                                                                                                       \
        template<typename... Args>                                                                                     \
        static j_type invoke_static(const JniEnv &env, jclass clazz, jmethodID, Args... args) {                        \
            return env->CallStatic##j_type_name##Method(clazz, args...);                                               \
        }                                                                                                              \
    }

        DEFINE_JNI_INVOKER(void, Void);
        DEFINE_JNI_INVOKER(jboolean, Boolean);
        DEFINE_JNI_INVOKER(jbyte, Byte);
        DEFINE_JNI_INVOKER(jchar, Char);
        DEFINE_JNI_INVOKER(jshort, Short);
        DEFINE_JNI_INVOKER(jint, Int);
        DEFINE_JNI_INVOKER(jlong, Long);
        DEFINE_JNI_INVOKER(jfloat, Float);
        DEFINE_JNI_INVOKER(jdouble, Double);
        DEFINE_JNI_INVOKER(jobject, Object);

#undef DEFINE_JNI_INVOKER

#define DEFINE_JNI_ARRAY_INVOKER(j_type)                                                                               \
    template<> struct JniInvoker<j_type> {                                                                             \
        template<typename... Args> static j_type invoke(const JniEnv &env, jmethodID id, jobject obj, Args... args) {  \
            return reinterpret_cast<j_type>(env->CallObjectMethod(obj, id, args...));                                  \
        }                                                                                                              \
                                                                                                                       \
        template<typename... Args>                                                                                     \
        static j_type invoke_non_virtual(const JniEnv &env, jclass clazz, jmethodID id, jobject obj, Args... args) {   \
            return reinterpret_cast<j_type>(env->CallNonvirtualObjectMethod(obj, clazz, id, args...));                 \
        }                                                                                                              \
                                                                                                                       \
        template<typename... Args>                                                                                     \
        static j_type invoke_static(const JniEnv &env, jclass clazz, jmethodID, Args... args) {                        \
            return reinterpret_cast<j_type>(env->CallStaticObjectMethod(clazz, args...));                              \
        }                                                                                                              \
    }

        DEFINE_JNI_ARRAY_INVOKER(jbooleanArray);
        DEFINE_JNI_ARRAY_INVOKER(jbyteArray);
        DEFINE_JNI_ARRAY_INVOKER(jcharArray);
        DEFINE_JNI_ARRAY_INVOKER(jshortArray);
        DEFINE_JNI_ARRAY_INVOKER(jintArray);
        DEFINE_JNI_ARRAY_INVOKER(jlongArray);
        DEFINE_JNI_ARRAY_INVOKER(jfloatArray);
        DEFINE_JNI_ARRAY_INVOKER(jdoubleArray);
        DEFINE_JNI_ARRAY_INVOKER(jobjectArray);

#undef DEFINE_JNI_ARRAY_INVOKER

        template<> struct JniInvoker<jstring> {
            template<typename... Args>
            static jstring invoke(const JniEnv &env, jmethodID id, jobject obj, Args... args) {
                return reinterpret_cast<jstring>(env->CallObjectMethod(obj, id, args...));
            }

            template<typename... Args>
            static jstring
            invoke_non_virtual(const JniEnv &env, jclass clazz, jmethodID id, jobject obj, Args... args) {
                return reinterpret_cast<jstring>(env->CallNonvirtualObjectMethod(obj, clazz, id, args...));
            }

            template<typename... Args>
            static jstring invoke_static(const JniEnv &env, jclass clazz, jmethodID, Args... args) {
                return reinterpret_cast<jstring>(env->CallStaticObjectMethod(clazz, args...));
            }
        };

        // ref type SPECIALIZATIONS

        template<typename T, typename... Args>
            requires IsJniRefType<T>
        typename JniType<T>::LocalRefType invoke(const JniEnv &env, jmethodID id, jobject obj, Args... args) {
            return JniType<T>::LocalRefType::wrap(
                env,
                JniInvoker<typename JniType<T>::Type>::invoke(env, id, obj, args...)
            );
        }

        template<typename T, typename... Args>
            requires IsJniRefType<T>
        typename JniType<T>::LocalRefType
        invoke_non_virtual(const JniEnv &env, jclass clazz, jmethodID id, jobject obj, Args... args) {
            return JniType<T>::LocalRefType::wrap(
                env,
                JniInvoker<typename JniType<T>::Type>::invoke_non_virtual(env, clazz, id, obj, args...)
            );
        }

        template<typename T, typename... Args>
            requires IsJniRefType<T>
        typename JniType<T>::LocalRefType invoke_static(const JniEnv &env, jclass clazz, jmethodID id, Args... args) {
            return JniType<T>::LocalRefType::wrap(
                env,
                JniInvoker<typename JniType<T>::Type>::invoke_static(env, clazz, id, args...)
            );
        }

        // value type SPECIALIZATIONS

        template<typename T, typename... Args>
            requires(IsJniValueType<T>)
        typename JniType<T>::Type invoke(const JniEnv &env, jmethodID id, jobject obj, Args... args) {
            return JniInvoker<typename JniType<T>::Type>::invoke(env, id, obj, args...);
        }

        template<typename T, typename... Args>
            requires(IsJniValueType<T>)
        typename JniType<T>::Type
        invoke_non_virtual(const JniEnv &env, jclass clazz, jmethodID id, jobject obj, Args... args) {
            return JniInvoker<typename JniType<T>::Type>::invoke_non_virtual(env, clazz, id, obj, args...);
        }

        template<typename T, typename... Args>
            requires(IsJniValueType<T>)
        typename JniType<T>::Type invoke_static(const JniEnv &env, jclass clazz, jmethodID id, Args... args) {
            return JniInvoker<typename JniType<T>::Type>::invoke_static(env, clazz, id, args...);
        }

        // void SPECIALIZATIONS

        template<typename T, typename... Args>
            requires std::is_void_v<T>
        void invoke(const JniEnv &env, jmethodID id, jobject obj, Args... args) {
            JniInvoker<typename JniType<T>::Type>::invoke(env, id, obj, args...);
        }

        template<typename T, typename... Args>
            requires std::is_void_v<T>
        void invoke_non_virtual(const JniEnv &env, jclass clazz, jmethodID id, jobject obj, Args... args) {
            JniInvoker<typename JniType<T>::Type>::invoke_non_virtual(env, clazz, id, obj, args...);
        }

        template<typename T, typename... Args>
            requires std::is_void_v<T>
        void invoke_static(const JniEnv &env, jclass clazz, jmethodID id, Args... args) {
            JniInvoker<typename JniType<T>::Type>::invoke_static(env, clazz, id, args...);
        }
    } // namespace _internal

    /**
     * Helper class to represent a JNI executable (method or constructor).
     *
     * Since the JVM represents constructors and methods the same way, this
     * class is used to represent both.
     *
     * @tparam Static whether this executable is static
     * @tparam Name the name of the executable
     * @tparam Class the class this executable belongs to
     * @tparam R the return type of the executable
     * @tparam Args the argument types of the executable
     */
    template<bool Static, JniMemberName Name, typename Class, typename R, typename... Args>
        requires(IsJniClass<Class> && IsJniType<R> && (IsJniType<Args> && ...))
    class JniExecutable {
    protected:
        // The class this method belongs to
        Class &clazz;

        jmethodID id;

        /**
         * Constructs a new JNI executable.
         *
         * @param clazz the class this method belongs to
         */
        constexpr explicit JniExecutable(Class &clazz) noexcept
            : clazz(clazz)
            , id(nullptr) {}

        /**
         * Resolves the method ID of this executable.
         *
         * @param env the JNI environment to use
         */
        void resolve(const JniEnv &env) {
            if (id) {
                return;
            }

            // N.B: This may be run multiple times in a multithreaded environment, but that is fine
            // since the JVM guarantees that the method ID will be the same for the same method
            // signature.

            auto java_clazz = clazz.get(env);
            if constexpr (Static) {
                id = env->GetStaticMethodID(java_clazz, Name, signature());
            } else {
                id = env->GetMethodID(java_clazz, Name, signature());
            }

            JniExceptionCheck::throw_if_pending(env);
        }

    public:
        /**
         * Retrieves the signature of this method.
         *
         * @return the signature of this method
         */
        [[nodiscard]] constexpr const char *signature() const noexcept {
            return JniMethodSignature<R, Args...>::Signature;
        }
    };

    /**
     * Helper class to represent a non static JNI method. This does not include constructors.
     *
     * @tparam Class the class this method belongs to
     * @tparam Name the name of the method
     * @tparam R the return type of the method
     * @tparam Args the argument types of the method
     */
    template<typename Class, JniMemberName Name, typename R, typename... Args>
    class JniInstanceMethod : public JniExecutable<false, Name, Class, R, Args...> {
    public:
        /**
         * Constructs a new JNI method.
         *
         * @param clazz the class this method belongs to
         */
        constexpr explicit JniInstanceMethod(Class &clazz) noexcept
            : JniExecutable<false, Name, Class, R, Args...>(clazz) {}

        /**
         * Invokes this method on the given object.
         *
         * @tparam Self the type of the object to invoke this method on
         * @tparam FnArgs the types of the arguments to pass to this method
         * @param env the JNI environment to use
         * @param self the object to invoke this method on
         * @param args the arguments to pass to this method
         * @return the return value of this method
         */
        template<typename Self, typename... FnArgs>
        auto invoke(const JniEnv &env, const Self &self, FnArgs... args)
            requires(
                !std::is_void_v<R> && IsJniConvertibleTo<Self, typename JniType<Class>::Type>
                && (IsJniConvertibleTo<FnArgs, Args> && ...)
            )
        {
            this->resolve(env);

            auto val = _internal::JniInvoker<typename JniType<R>::Type>::invoke(
                env,
                this->id,
                JniTypeConverter<Self>::convert_to_jni(self),
                JniTypeConverter<FnArgs>::convert_to_jni(std::forward<FnArgs>(args))...
            );

            JniExceptionCheck::throw_if_pending(env);

            return JniTypeConverter<R>::convert_from_jni(env, val);
        }

        /**
         * Invokes this method on the given object.
         *
         * @tparam Self the type of the object to invoke this method on
         * @tparam FnArgs the types of the arguments to pass to this method
         * @param env the JNI environment to use
         * @param self the object to invoke this method on
         * @param args the arguments to pass to this method
         */
        template<typename Self, typename... FnArgs>
        void invoke(const JniEnv &env, const Self &self, FnArgs... args)
            requires(
                std::is_void_v<R> && IsJniConvertibleTo<Self, typename JniType<Class>::Type>
                && (IsJniConvertibleTo<FnArgs, Args> && ...)
            )
        {
            this->resolve(env);

            _internal::JniInvoker<R>::invoke(
                env,
                this->id,
                JniTypeConverter<Self>::convert_to_jni(self),
                JniTypeConverter<FnArgs>::convert_to_jni(std::forward<FnArgs>(args))...
            );

            JniExceptionCheck::throw_if_pending(env);
        }

        /**
         * Invokes this non-virtual method on the given object.
         *
         * @tparam Self the type of the object to invoke this method on
         * @tparam FnArgs the types of the arguments to pass to this method
         * @param env the JNI environment to use
         * @param self the object to invoke this method on
         * @param args the arguments to pass to this method
         * @return the return value of this method
         */
        template<typename Self, typename... FnArgs>
        auto invoke_non_virtual(const JniEnv &env, const Self &self, FnArgs... args)
            requires(
                !std::is_void_v<R> && IsJniConvertibleTo<Self, typename JniType<Class>::Type>
                && (IsJniConvertibleTo<FnArgs, Args> && ...)
            )
        {
            this->resolve(env);

            auto val = _internal::JniInvoker<R>::invoke_non_virtual(
                env,
                this->clazz.get(env),
                this->id,
                JniTypeConverter<Self>::convert_to_jni(self),
                JniTypeConverter<FnArgs>::convert_to_jni(std::forward<FnArgs>(args))...
            );

            JniExceptionCheck::throw_if_pending(env);

            return JniTypeConverter<R>::convert_from_jni(env, val);
        }

        /**
         * Invokes this non-virtual method on the given object.
         *
         * @tparam Self the type of the object to invoke this method on
         * @tparam FnArgs the types of the arguments to pass to this method
         * @param env the JNI environment to use
         * @param self the object to invoke this method on
         * @param args the arguments to pass to this method
         */
        template<typename Self, typename... FnArgs>
        void invoke_non_virtual(const JniEnv &env, const Self &self, FnArgs... args)
            requires(
                std::is_void_v<R> && IsJniConvertibleTo<Self, typename JniType<Class>::Type>
                && (IsJniConvertibleTo<FnArgs, Args> && ...)
            )
        {
            this->resolve(env);

            _internal::JniInvoker<R>::invoke_non_virtual(
                env,
                this->clazz.get(env),
                this->id,
                JniTypeConverter<Self>::convert_to_jni(self),
                JniTypeConverter<FnArgs>::convert_to_jni(std::forward<FnArgs>(args))...
            );

            JniExceptionCheck::throw_if_pending(env);
        }
    };

    /**
     * Helper class to represent a static JNI method.
     *
     * @tparam Class the class this method belongs to
     * @tparam Name the name of this method
     * @tparam R the return type of this method
     * @tparam Args the argument types of this method
     */
    template<typename Class, JniMemberName Name, typename R, typename... Args>
    class JniStaticMethod : public JniExecutable<true, Name, Class, R, Args...> {
    public:
        /**
         * Constructs a new JNI method.
         *
         * @param clazz the class this method belongs to
         */
        constexpr explicit JniStaticMethod(Class &clazz) noexcept
            : JniExecutable<true, Name, Class, R, Args...>(clazz) {}

        /**
         * Invokes this method on the given object.
         *
         * @tparam FnArgs the types of the arguments to pass to this method
         * @param env the JNI environment to use
         * @param args the arguments to pass to this method
         * @return the return value of this method
         */
        template<typename... FnArgs>
        auto invoke(const JniEnv &env, FnArgs... args)
            requires(!std::is_void_v<R> && (IsJniConvertibleTo<FnArgs, Args> && ...))
        {
            this->resolve(env);

            auto val = _internal::JniInvoker<R>::invoke_static(
                env,
                this->id,
                JniTypeConverter<FnArgs>::convert_to_jni(std::forward<FnArgs>(args))...
            );

            JniExceptionCheck::throw_if_pending(env);

            return JniTypeConverter<R>::convert_from_jni(env, val);
        }

        /**
         * Invokes this method on the given object.
         *
         * @tparam FnArgs the types of the arguments to pass to this method
         * @param env the JNI environment to use
         * @param args the arguments to pass to this method
         */
        template<typename... FnArgs>
        void invoke(const JniEnv &env, FnArgs... args)
            requires(std::is_void_v<R> && (IsJniConvertibleTo<FnArgs, Args> && ...))
        {
            this->resolve(env);

            _internal::JniInvoker<R>::invoke_static(
                env,
                this->id,
                JniTypeConverter<FnArgs>::convert_to_jni(std::forward<FnArgs>(args))...
            );

            JniExceptionCheck::throw_if_pending(env);
        }
    };

    template<typename Class, typename... Args>
    class JniConstructor : public JniExecutable<false, "<init>", Class, void, Args...> {
    public:
        /**
         * Constructs a new JNI constructor.
         *
         * @param clazz the class this constructor belongs to
         */
        constexpr explicit JniConstructor(Class &clazz) noexcept
            : JniExecutable<false, "<init>", Class, void, Args...>(clazz) {}

        /**
         * Invokes this constructor.
         *
         * @tparam FnArgs the types of the arguments to pass to this constructor
         * @param env the JNI environment to use
         * @param args the arguments to pass to this constructor
         * @return the constructed object
         */
        template<typename... FnArgs>
        auto invoke(const JniEnv &env, FnArgs... args)
            requires(IsJniConvertibleTo<FnArgs, Args> && ...)
        {
            this->resolve(env);

            auto obj = reinterpret_cast<typename JniType<Class>::Type>(env->NewObject(
                this->clazz.get(env),
                this->id,
                JniTypeConverter<FnArgs>::convert_to_jni(std::forward<FnArgs>(args))...
            ));

            JniExceptionCheck::throw_if_pending(env);
            return JniType<Class>::LocalRefType::wrap(env, obj);
        }
    };
} // namespace ujr
