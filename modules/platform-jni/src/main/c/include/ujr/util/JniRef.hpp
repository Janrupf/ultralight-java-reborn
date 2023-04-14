#pragma once

#include <string>
#include <type_traits>
#include <utility>

#include "ujr/util/JniEnv.hpp"
#include "ujr/util/JniExceptionCheck.hpp"
#include "ujr/util/JniType.hpp"

namespace ujr {
    template<typename T>
        requires IsJniType<T>
    class JniRef;
    template<typename T> class JniWeakRef;
    template<typename T> class JniStrongRef;
    template<typename T> class JniLocalRef;
    template<typename T> class JniGlobalRef;

    /**
     * Base class for JNI reference types.
     *
     * @tparam T the type of the JNI reference, e.g. jobject, jclass, etc.
     */
    template<typename T>
        requires IsJniType<T>
    class JniRef {
    public:
        /**
         * The C++ JNI type of the object this reference represents.
         */
        using AsJniType = JniType<T>::Type;

        /**
         * The JNI binary name of the object class this reference presents.
         */
        static constexpr JniClassName AsJniName = JniType<T>::Name;

    protected:
        JniType<T>::Type ref;

        /**
         * Creates a new JNI reference.
         *
         * @param ref the JNI reference
         */
        explicit JniRef(JniType<T>::Type ref)
            : ref(ref) {}

    public:
        virtual ~JniRef() = default;

        JniRef(const JniRef &other) = delete;

        JniRef(JniRef &&other) noexcept
            : ref(other.ref) {
            other.ref = nullptr;
        }

        JniRef &operator=(const JniRef &other) = delete;

        JniRef &operator=(JniRef &&other) noexcept {
            this->ref = other.ref;
            other.ref = nullptr;

            return *this;
        }

        /**
         * Determines whether this reference is valid.
         *
         * @param env the JNI environment to use for checking
         * @return true if the reference is valid, false otherwise
         */
        [[nodiscard]] bool is_valid(const JniEnv &env) const { return !env->IsSameObject(this->ref, nullptr); }

        /**
         * Clones this reference as a weak reference.
         *
         * @param target_env the environment to clone the reference in
         * @return the cloned weak reference
         */
        JniWeakRef<T> clone_as_weak(JniEnv target_env) const {
            auto weak = reinterpret_cast<JniType<T>::Type>(target_env->NewWeakGlobalRef(this->ref));
            JniExceptionCheck::throw_if_pending(target_env);

            return std::move(JniWeakRef<T>::wrap(std::move(target_env), weak));
        }

        /**
         * Clones this reference as a strong local reference.
         *
         * @param target_env the environment to clone the reference in
         * @return the cloned strong local reference
         */
        JniLocalRef<T> clone_as_local(JniEnv target_env) const {
            auto local = reinterpret_cast<JniType<T>::Type>(target_env->NewLocalRef(this->ref));
            JniExceptionCheck::throw_if_pending(target_env);

            return std::move(JniLocalRef<T>::wrap(std::move(target_env), local));
        }

        /**
         * Clones this reference as a strong global reference.
         *
         * @param target_env the environment to clone the reference in
         * @return the cloned strong global reference
         */
        JniGlobalRef<T> clone_as_global(JniEnv target_env) const {
            auto global = reinterpret_cast<JniType<T>::Type>(target_env->NewGlobalRef(this->ref));
            JniExceptionCheck::throw_if_pending(target_env);

            return std::move(JniGlobalRef<T>::wrap(std::move(target_env), global));
        }
    };

    /**
     * A weak reference to a JNI object.
     *
     * @tparam T the type of the JNI reference, e.g. jobject, jclass, etc.
     */
    template<typename T> class JniWeakRef final : public JniRef<T> {
    private:
        explicit JniWeakRef(JniType<T>::Type ref)
            : JniRef<T>(ref) {}

    public:
        ~JniWeakRef() final {
            if (this->ref != nullptr) {
                JniEnv::from_thread()->DeleteWeakGlobalRef(this->ref);
            }
        }

        JniWeakRef(const JniWeakRef &other) = delete;

        JniWeakRef(JniWeakRef &&other) noexcept
            : JniRef<T>(std::move(other)) {}

        JniWeakRef &operator=(const JniWeakRef &other) = delete;

        JniWeakRef &operator=(JniWeakRef &&other) noexcept {
            if (this->ref != nullptr) {
                JniEnv::from_thread()->DeleteWeakGlobalRef(this->ref);
            }

            JniRef<T>::operator=(std::move(other));
            return *this;
        }

        /**
         * Wraps an existing JNI reference into a weak reference.
         *
         * The passed reference must be a weak reference.
         *
         * @param ref the JNI reference
         * @return the wrapped reference
         */
        static JniWeakRef wrap(JniType<T>::Type ref) { return JniWeakRef(ref); }
    };

    /**
     * Represents some kind of strong reference to a JNI object.
     *
     * @tparam T the type of the JNI reference, e.g. jobject, jclass, etc.
     */
    template<typename T> class JniStrongRef : public JniRef<T> {
    protected:
        explicit JniStrongRef(JniType<T>::Type ref)
            : JniRef<T>(ref) {}

    public:
        JniStrongRef(const JniStrongRef &other) = delete;

        JniStrongRef(JniStrongRef &&other) noexcept
            : JniRef<T>(std::move(other)) {}

        JniStrongRef &operator=(const JniStrongRef &other) = delete;

        JniStrongRef &operator=(JniStrongRef &&other) noexcept {
            JniRef<T>::operator=(std::move(other));
            return *this;
        }

        /**
         * Retrieves the underlying JNI reference.
         *
         * @return the JNI reference
         */
        T get() const { return this->ref; }

        /**
         * Retrieves the underlying JNI reference.
         *
         * @return the JNI reference
         */
        operator T() const { return get(); } // NOLINT(google-explicit-constructor)

        // SPECIALIZATIONS

        /**
         * Throws this reference as an exception.
         *
         * @param env the JNI environment to use for throwing
         */
        void do_throw(const JniEnv &env) const
            requires(std::is_same_v<jthrowable, T> || std::is_base_of_v<jthrowable, T>)
        {
            env->Throw(get());
        }
    };

    template<typename T> class JniLocalRef final : public JniStrongRef<T> {
    private:
        JniEnv env;

        explicit JniLocalRef(JniEnv env, JniType<T>::Type ref)
            : JniStrongRef<T>(ref)
            , env(std::move(env)) {}

    public:
        ~JniLocalRef() final {
            if (this->ref != nullptr) {
                this->env->DeleteLocalRef(this->ref);
            }
        }

        JniLocalRef(const JniLocalRef &other)
            : JniStrongRef<T>(other.ref)
            , env(other.env) {
            this->ref = reinterpret_cast<JniType<T>::Type>(this->env->NewLocalRef(other.ref));
        }

        JniLocalRef(JniLocalRef &&other) noexcept
            : JniStrongRef<T>(std::move(other))
            , env(std::move(other.env)) {}

        JniLocalRef &operator=(const JniLocalRef &other) {
            if (this->ref != nullptr) {
                this->env->DeleteLocalRef(this->ref);
            }

            this->env = other.env;
            this->ref = reinterpret_cast<JniType<T>::Type>(this->env->NewLocalRef(other.ref));

            return *this;
        }

        JniLocalRef &operator=(JniLocalRef &&other) noexcept {
            if (this->ref != nullptr) {
                this->env->DeleteLocalRef(this->ref);
            }

            JniStrongRef<T>::operator=(std::move(other));
            this->env = std::move(other.env);

            return *this;
        }

        /**
         * Retrieves the associated JNI environment.
         *
         * @return the associated JNI environment
         */
        [[nodiscard]] const JniEnv &associated_env() const { return env; }

        /**
         * Determines whether this reference is valid.
         *
         * @return true if the reference is valid, false otherwise
         */
        [[nodiscard]] bool is_valid() const { return !this->env->IsSameObject(this->ref, nullptr); }

        /**
         * Determines whether this reference is valid.
         *
         * @return true if the reference is valid, false otherwise
         */
        operator bool() const { return is_valid(); } // NOLINT(google-explicit-constructor)

        /**
         * Clones this reference as a weak reference.
         *
         * @return the cloned weak reference
         */
        JniWeakRef<T> clone_as_weak() const {
            auto weak = reinterpret_cast<JniType<T>::Type>(env->NewWeakGlobalRef(this->ref));
            JniExceptionCheck::throw_if_pending(env);

            return std::move(JniWeakRef<T>::wrap(weak));
        }

        /**
         * Clones this reference as a strong local reference.
         *
         * @return the cloned strong local reference
         */
        JniLocalRef<T> clone_as_local() const {
            auto local = reinterpret_cast<JniType<T>::Type>(env->NewLocalRef(this->ref));
            JniExceptionCheck::throw_if_pending(env);

            return std::move(JniLocalRef<T>::wrap(env, local));
        }

        /**
         * Clones this reference as a strong global reference.
         *
         * @return the cloned strong global reference
         */
        JniGlobalRef<T> clone_as_global() const {
            auto global = reinterpret_cast<JniType<T>::Type>(env->NewGlobalRef(this->ref));
            JniExceptionCheck::throw_if_pending(env);

            return std::move(JniGlobalRef<T>::wrap(global));
        }

        /**
         * Wraps a JNI reference into a local reference.
         *
         * The passed reference must be a local reference.
         *
         * @param env the environment to the reference belongs to
         * @param ref the JNI reference
         * @return the wrapped reference
         */
        static JniLocalRef wrap(JniEnv env, JniType<T>::Type ref) { return JniLocalRef(env, ref); }

        // SPECIALIZATIONS

        /**
         * Throws this reference as an exception.
         */
        void do_throw() const
            requires(std::is_same_v<jthrowable, T> || std::is_base_of_v<jthrowable, T>)
        {
            env->Throw(this->ref);
        }

        /**
         * Retrieves the UTF-8 representation of this string.
         *
         * @return the UTF-8 representation
         */
        [[nodiscard]] std::string to_utf8() const
            requires std::is_same_v<typename JniType<T>::Type, jstring>
        {
            auto utf8_length = static_cast<size_t>(env->GetStringUTFLength(this->ref));

            std::string result;
            result.resize(utf8_length);

            env->GetStringUTFRegion(this->ref, 0, utf8_length, result.data());
            JniExceptionCheck::throw_if_pending(env);

            return result;
        }

        /**
         * Creates a new local reference from a UTF-8 string.
         *
         * @param s the UTF-8 string
         * @return the local reference
         */
        static JniLocalRef from_utf8(JniEnv env, const char *s)
            requires std::is_same_v<typename JniType<T>::Type, jstring>
        {
            auto java_s = env->NewStringUTF(s);
            return JniLocalRef::wrap(std::move(env), java_s);
        }
    };

    /**
     * A global reference to a JNI object.
     *
     * @tparam T the type of the JNI reference, e.g. jobject, jclass, etc.
     */
    template<typename T> class JniGlobalRef final : public JniStrongRef<T> {
    private:
        explicit JniGlobalRef(JniType<T>::Type ref)
            : JniStrongRef<T>(ref) {}

    public:
        ~JniGlobalRef() final {
            if (this->ref != nullptr) {
                JniEnv::from_thread()->DeleteGlobalRef(this->ref);
            }
        }

        JniGlobalRef(const JniGlobalRef &other) = delete;

        JniGlobalRef(JniGlobalRef &&other) noexcept
            : JniStrongRef<T>(std::move(other)) {}

        JniGlobalRef &operator=(const JniGlobalRef &other) = delete;

        JniGlobalRef &operator=(JniGlobalRef &&other) noexcept {
            if (this->ref != nullptr) {
                JniEnv::from_thread()->DeleteGlobalRef(this->ref);
            }

            JniStrongRef<T>::operator=(std::move(other));
            return *this;
        }

        /**
         * Wraps an existing JNI reference into a global reference.
         *
         * The passed reference must be a global reference.
         *
         * @param ref the JNI reference
         * @return the wrapped reference
         */
        static JniGlobalRef wrap(JniType<T>::Type ref) { return JniGlobalRef(ref); }
    };
} // namespace ujr
