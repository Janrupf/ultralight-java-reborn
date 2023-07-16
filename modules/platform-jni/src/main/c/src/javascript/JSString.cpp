#include "ujr/javascript/JSString.hpp"

namespace ujr {
    JniLocalRef<jstring> JSString::to_java(const JniEnv &env, JSStringRef string) {
        auto j_string = JniLocalRef<jstring>::from_utf16(
            env,
            JSStringGetCharactersPtr(string),
            static_cast<jint>(JSStringGetLength(string))
        );
        return j_string;
    }

    std::string JSString::to_cpp(JSStringRef string) {
        auto utf8_length = JSStringGetMaximumUTF8CStringSize(string);

        std::string out;
        out.resize(utf8_length);

        auto actual_length = JSStringGetUTF8CString(string, out.data(), utf8_length);
        out.resize(actual_length - 1);

        return out;
    }

    JSStringRef JSString::from_java(const JniEnv &env, const JniStrongRef<jstring> &string) {
        auto *java_chars = env->GetStringCritical(string, nullptr);
        auto js_string = JSStringCreateWithCharacters(java_chars, static_cast<size_t>(env->GetStringLength(string)));
        env->ReleaseStringCritical(string, java_chars);

        return js_string;
    }
} // namespace ujr