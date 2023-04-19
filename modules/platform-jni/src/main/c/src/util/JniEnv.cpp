#include "ujr/util/JniEnv.hpp"

#include <stdexcept>
#include <utility>

namespace ujr {
    JavaVM *JniEnv::jvm = nullptr;

    JniEnv::JniEnv(JNIEnv *env)
        : env(env) {}

    JniEnv::JniEnv(const JniEnv &other)
        : env(other.env) {}

    JniEnv::JniEnv(JniEnv &&other) noexcept
        : env(other.env) {
        other.env = nullptr;
    }

    JniEnv &JniEnv::operator=(const JniEnv &other) {
        if (&other != this) {
            this->env = other.env;
        }

        return *this;
    }

    JniEnv &JniEnv::operator=(JniEnv &&other) noexcept {
        this->env = other.env;
        other.env = nullptr;

        return *this;
    }

    void JniEnv::init(JavaVM *jvm) { JniEnv::jvm = jvm; }

    void JniEnv::deinit() { JniEnv::jvm = nullptr; }

    JniEnv JniEnv::from_existing(JNIEnv *env) { return std::move(JniEnv(env)); }

    JniEnv JniEnv::from_thread(bool auto_attach) {
        JNIEnv *env;

        auto result = jvm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_8);
        if (result == JNI_EDETACHED) {
            if (auto_attach) {
                // Automatically attach the thread to the JVM
                result = jvm->AttachCurrentThread(reinterpret_cast<void **>(&env), nullptr);

                if (result != JNI_OK) {
                    // JVM is probably shutting down, return an invalid environment
                    return JniEnv(nullptr);
                }
            } else {
                // Not attached and not allowed to attach, return an invalid environment
                return JniEnv(nullptr);
            }
        }

        // The JNI environment should be valid at this point
        return JniEnv(env);
    }

    JniEnv JniEnv::require_existing_from_thread() {
        auto env = from_thread(false);

        if (!env.is_valid()) {
            throw std::runtime_error("Thread not attached to JVM");
        }

        return env;
    }

    bool JniEnv::is_valid() const { return env != nullptr; }

    JNIEnv *JniEnv::get() const { return env; }

    JNIEnv *JniEnv::operator->() const { return get(); }
} // namespace ujr