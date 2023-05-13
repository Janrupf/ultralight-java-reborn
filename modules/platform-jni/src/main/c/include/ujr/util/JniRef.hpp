#pragma once

#include <Ultralight/String16.h>

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
        template<typename O> friend class JniLocalRef;

    public:
        /**
         * The C++ JNI type of the object this reference represents.
         */
        using AsJniType = typename JniType<T>::Type;

        /**
         * The JNI binary name of the object class this reference presents.
         */
        static constexpr JniClassName AsJniName = JniType<T>::Name;

    protected:
        typename JniType<T>::Type ref;

        /**
         * Creates a new JNI reference.
         *
         * @param ref the JNI reference
         */
        explicit JniRef(typename JniType<T>::Type ref)
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
         * Determines whether this reference is null.
         *
         * @param env the JNI environment to use for checking
         * @return true if the reference is valid, false otherwise
         */
        [[nodiscard]] bool is_null(const JniEnv &env) const { return env->IsSameObject(this->ref, nullptr); }

        /**
         * Throws an IllegalArgumentException if this reference is null.
         *
         * @param env the JNI environment to use for checking
         * @param name the name of the argument
         * @return this
         */
        virtual const JniRef &require_non_null_argument(const JniEnv &env, std::string name) {
            if (this->is_null(env)) {
                JniExceptionCheck::throw_illegal_argument(std::move(name), "must not be null");
            }

            return *this;
        }

        /**
         * Compares this reference to another reference.
         *
         * @tparam O the type of the other reference
         * @param env the JNI environment to use for checking
         * @param other the other reference
         * @return true if the references are equal (refer to the same object!), false otherwise
         */
        template<typename O = T> bool ref_equals(const JniEnv &env, const JniRef<O> &other) const {
            // Avoid calling IsSameObject if the references are the same.
            return this->ref == other.ref || env->IsSameObject(this->ref, other.ref);
        }

        /**
         * Compares this reference to another reference.
         *
         * @tparam O the type of the other reference
         * @param other the other reference
         * @return true if the references are equal (refer to the same object!), false otherwise
         */
        template<typename O = T> bool ref_equals(const JniLocalRef<O> &other) const {
            // Avoid calling IsSameObject if the references are the same.
            return this->ref == other.ref || other.associated_env()->IsSameObject(this->ref, other.ref);
        }

        /**
         * Clones this reference as a weak reference.
         *
         * @param target_env the environment to clone the reference in
         * @return the cloned weak reference
         */
        JniWeakRef<T> clone_as_weak(JniEnv target_env) const {
            auto weak = reinterpret_cast<typename JniType<T>::Type>(target_env->NewWeakGlobalRef(this->ref));
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
            auto local = reinterpret_cast<typename JniType<T>::Type>(target_env->NewLocalRef(this->ref));
            JniExceptionCheck::throw_if_pending(target_env);

            return JniLocalRef<T>::wrap(target_env, local);
        }

        /**
         * Clones this reference as a strong global reference.
         *
         * @param target_env the environment to clone the reference in
         * @return the cloned strong global reference
         */
        JniGlobalRef<T> clone_as_global(const JniEnv &target_env) const {
            auto global = reinterpret_cast<typename JniType<T>::Type>(target_env->NewGlobalRef(this->ref));
            JniExceptionCheck::throw_if_pending(target_env);

            return JniGlobalRef<T>::wrap(global);
        }
    };

    /**
     * A weak reference to a JNI object.
     *
     * @tparam T the type of the JNI reference, e.g. jobject, jclass, etc.
     */
    template<typename T> class JniWeakRef final : public JniRef<T> {
    private:
        explicit JniWeakRef(typename JniType<T>::Type ref)
            : JniRef<T>(ref) {}

    public:
        ~JniWeakRef() final {
            if (this->ref != nullptr) {
                auto env = JniEnv::from_thread();

                // For weak reference we allow the environment to not exist,
                // because the JVM may be shutting down.
                if (env.is_valid()) {
                    env->DeleteWeakGlobalRef(this->ref);
                }
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
         * Throws an IllegalArgumentException if this reference is null.
         *
         * @param env the JNI environment to use for checking
         * @param name the name of the argument
         * @return this
         */
        const JniWeakRef &require_non_null_argument(const JniEnv &env, std::string name) final {
            JniRef<T>::require_non_null_argument(env, std::move(name));
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
        static JniWeakRef wrap(typename JniType<T>::Type ref) { return JniWeakRef(ref); }
    };

    /**
     * Represents some kind of strong reference to a JNI object.
     *
     * @tparam T the type of the JNI reference, e.g. jobject, jclass, etc.
     */
    template<typename T> class JniStrongRef : public JniRef<T> {
    protected:
        explicit JniStrongRef(typename JniType<T>::Type ref)
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
         * Retrieves the underlying JNI reference and resets this reference to null.
         *
         * @return the JNI reference
         */
        T leak() {
            auto ref = this->ref;
            this->ref = nullptr;
            return ref;
        }

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

        /**
         * Throws an IllegalArgumentException if this reference is null.
         *
         * @param env the JNI environment to use for checking
         * @param name the name of the argument
         * @return this
         */
        const JniStrongRef &require_non_null_argument(const JniEnv &env, std::string name) override {
            JniRef<T>::require_non_null_argument(env, std::move(name));
            return *this;
        }
    };

    template<typename T> class JniLocalRef final : public JniStrongRef<T> {
    private:
        JniEnv env;
        bool needs_delete;

        explicit JniLocalRef(JniEnv env, typename JniType<T>::Type ref, bool needs_delete)
            : JniStrongRef<T>(ref)
            , env(std::move(env))
            , needs_delete(needs_delete) {}

    public:
        ~JniLocalRef() final {
            if (this->needs_delete && this->ref != nullptr) {
                this->env->DeleteLocalRef(this->ref);
            }
        }

        JniLocalRef(const JniLocalRef &other)
            : JniStrongRef<T>(other.ref)
            , env(other.env)
            , needs_delete(true) {
            this->ref = reinterpret_cast<typename JniType<T>::Type>(this->env->NewLocalRef(other.ref));
        }

        JniLocalRef(JniLocalRef &&other) noexcept
            : JniStrongRef<T>(std::move(other))
            , env(std::move(other.env))
            , needs_delete(other.needs_delete) {}

        JniLocalRef &operator=(const JniLocalRef &other) {
            if (this->needs_delete && this->ref != nullptr) {
                this->env->DeleteLocalRef(this->ref);
            }

            this->env = other.env;
            this->needs_delete = true;
            this->ref = reinterpret_cast<typename JniType<T>::Type>(this->env->NewLocalRef(other.ref));

            return *this;
        }

        JniLocalRef &operator=(JniLocalRef &&other) noexcept {
            if (this->needs_delete && this->ref != nullptr) {
                this->env->DeleteLocalRef(this->ref);
            }

            JniStrongRef<T>::operator=(std::move(other));
            this->env = std::move(other.env);
            this->needs_delete = other.needs_delete;

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
         * Throws an IllegalArgumentException if this reference is null.
         *
         * @param o_env the JNI environment to use for checking
         * @param name the name of the argument
         * @return this
         */
        const JniLocalRef &require_non_null_argument(const JniEnv &o_env, std::string name) override {
            JniRef<T>::require_non_null_argument(o_env, std::move(name));
            return *this;
        }

        /**
         * Throws an IllegalArgumentException if this reference is null.
         *
         * @param name the name of the argument
         * @return this
         */
        const JniLocalRef &require_non_null_argument(std::string name) {
            JniRef<T>::require_non_null_argument(env, std::move(name));
            return *this;
        }

        /**
         * Clones this reference as a weak reference.
         *
         * @return the cloned weak reference
         */
        JniWeakRef<T> clone_as_weak() const {
            auto weak = reinterpret_cast<typename JniType<T>::Type>(env->NewWeakGlobalRef(this->ref));
            JniExceptionCheck::throw_if_pending(env);

            return JniWeakRef<T>::wrap(weak);
        }

        /**
         * Clones this reference as a strong local reference.
         *
         * @return the cloned strong local reference
         */
        JniLocalRef<T> clone_as_local() const {
            auto local = reinterpret_cast<typename JniType<T>::Type>(env->NewLocalRef(this->ref));
            JniExceptionCheck::throw_if_pending(env);

            return std::move(JniLocalRef<T>::wrap(env, local));
        }

        /**
         * Clones this reference as a strong global reference.
         *
         * @return the cloned strong global reference
         */
        JniGlobalRef<T> clone_as_global() const {
            auto global = reinterpret_cast<typename JniType<T>::Type>(env->NewGlobalRef(this->ref));
            JniExceptionCheck::throw_if_pending(env);

            return JniGlobalRef<T>::wrap(global);
        }

        /**
         * Wraps a JNI reference into a local reference which can be deleted.
         *
         * The passed reference must be a local reference.
         *
         * @param env the environment to the reference belongs to
         * @param ref the JNI reference
         * @return the wrapped reference
         */
        static JniLocalRef wrap(JniEnv env, typename JniType<T>::Type ref) { return JniLocalRef(env, ref, true); }

        /**
         * Wraps a JNI reference into a local reference which cannot be deleted.
         *
         * The passed reference must be a local reference.
         *
         * @param env the environment to the reference belongs to
         * @param ref the JNI reference
         * @return the wrapped reference
         */
        static JniLocalRef wrap_nodelete(JniEnv env, typename JniType<T>::Type ref) {
            return JniLocalRef(env, ref, false);
        }

        /**
         * Creates a null reference.
         *
         * @param env the environment to the reference belongs to
         * @return the created reference
         */
        static JniLocalRef null(JniEnv env) { return JniLocalRef(env, nullptr, false); }

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
         * Retrieves the UTF-16 representation of this string.
         *
         * @return the UTF-16 representation
         */
        [[nodiscard]] ultralight::String16 to_utf16() const
            requires std::is_same_v<typename JniType<T>::Type, jstring>
        {
            auto utf16_length = static_cast<size_t>(env->GetStringLength(this->ref));

            // We can safely use a critical method here
            auto *utf16_chars = env->GetStringCritical(this->ref, nullptr);
            ultralight::String16 result(utf16_chars, utf16_length);
            env->ReleaseStringCritical(this->ref, utf16_chars);

            JniExceptionCheck::throw_if_pending(env);

            return result;
        }

        /**
         * Creates a new local reference from a UTF-8 string.
         *
         * @param env the JNI environment
         * @param s the UTF-8 string
         * @return the local reference
         */
        static JniLocalRef from_utf8(JniEnv env, const char *s)
            requires std::is_same_v<typename JniType<T>::Type, jstring>
        {
            auto java_s = env->NewStringUTF(s);
            return JniLocalRef::wrap(std::move(env), java_s);
        }

        /**
         * Creates a new local reference from a UTF-16 string.
         *
         * @param env the JNI environment
         * @param s the UTF-16 string
         * @return the local reference
         */
        static JniLocalRef from_utf16(JniEnv env, const ultralight::String16 &s)
            requires std::is_same_v<typename JniType<T>::Type, jstring>
        {
            auto java_s = env->NewString(reinterpret_cast<const jchar *>(s.data()), static_cast<jint>(s.length()));
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
        explicit JniGlobalRef(typename JniType<T>::Type ref)
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
         * Throws an IllegalArgumentException if this reference is null.
         *
         * @param env the JNI environment to use for checking
         * @param name the name of the argument
         * @return this
         */
        const JniGlobalRef &require_non_null_argument(const JniEnv &env, std::string name) override {
            JniRef<T>::require_non_null_argument(env, std::move(name));
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
        static JniGlobalRef wrap(typename JniType<T>::Type ref) { return JniGlobalRef(ref); }

        /**
         * Creates a null reference.
         *
         * @return the created reference
         */
        static JniGlobalRef null() { return JniGlobalRef(nullptr); }
    };

    // Comparison operators
    // C++20 forces us to declare them outside the class because otherwise it considers
    // them to be ambiguous

    /**
     * Compares this reference to another reference.
     *
     * @tparam O the type of the other reference
     * @param other the other reference
     * @return true if the references are equal (refer to the same object!), false otherwise
     */
    template<typename A, typename B> bool operator==(const JniLocalRef<A> &a, const JniLocalRef<B> &b) {
        return a.ref_equals(b);
    }
} // namespace ujr
