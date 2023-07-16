#pragma once

#include "ujr/util/JniException.hpp"

#include <JavaScriptCore/JSContextRef.h>
#include <JavaScriptCore/JSValueRef.h>

namespace ujr {
    class JniJavaScriptValueException : public JniException {
    private:
        JSGlobalContextRef context;
        JSValueRef value;

    public:
        static void throw_if_valid(JSContextRef context, JSValueRef exception /* owned! */);

        explicit JniJavaScriptValueException(JSGlobalContextRef context, JSValueRef value);
        ~JniJavaScriptValueException() override;

        [[nodiscard]] JniLocalRef<jthrowable> translate_to_java() const override;

        [[nodiscard]] JSGlobalContextRef get_context() const;
        [[nodiscard]] JSValueRef get_value() const;
    };
} // namespace ujr
