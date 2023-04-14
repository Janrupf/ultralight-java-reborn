#pragma once

#include <jni.h>

namespace ujr {
    /**
     * Reference to a JNI environment.
     */
    class JniEnv {
    private:
        static JavaVM *jvm;

        JNIEnv *env;

        explicit JniEnv(JNIEnv *env);

    public:
        JniEnv(const JniEnv &other);
        JniEnv(JniEnv &&other) noexcept;

        JniEnv &operator=(const JniEnv &other);
        JniEnv &operator=(JniEnv &&other) noexcept;

        /**
         * Stores the reference to the JVM instance.
         *
         * @param jvm the jvm instance
         */
        static void init(JavaVM *jvm);

        /**
         * Clears the reference to the JVM instance.
         */
        static void deinit();

        /**
         * Creates a JniEnv from an existing native JNI env.
         *
         * @param env the underlying JNI environment
         * @return the created JniEnv
         */
        static JniEnv from_existing(JNIEnv *env);

        /**
         * Creates a JniEnv from the current thread.
         *
         * The created environment may not be valid if {@code auto_attach} is false and
         * the current thread is not attached to the JVM.
         *
         * The thread is not automatically detached from the JVM, even if it was attached.
         *
         * @param auto_attach whether to automatically attach the current thread to the JVM
         *                    if it is not already attached
         * @return the created JniEnv
         */
        static JniEnv from_thread(bool auto_attach = true);

        /**
         * Determines whether this JniEnv is valid.
         *
         * @return true if this JniEnv is valid, false otherwise
         */
        [[nodiscard]] bool is_valid() const;

        /**
         * Retrieves the underlying JNI environment.
         *
         * @return the underlying JNI environment
         */
        [[nodiscard]] JNIEnv *get() const;

        /**
         * Retrieves the underlying JNI environment.
         *
         * @return the underlying JNI environment
         */
        [[nodiscard]] JNIEnv *operator->() const;
    };
} // namespace ujr