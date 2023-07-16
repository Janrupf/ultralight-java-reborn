#pragma once

#include <string>

#include "ujr/util/JniException.hpp"

namespace ujr {
    /**
     * Exception thrown when an illegal argument is passed to a JNI method.
     */
    class JniIllegalArgumentException : public JniException {
    private:
        std::string argument;
        std::string message;

    public:
        /**
         * Constructs a new JniIllegalArgumentException.
         *
         * @param argument the name of the argument that is illegal
         * @param message a message describing why the argument is illegal
         */
        explicit JniIllegalArgumentException(std::string argument, std::string message);

        [[nodiscard]] JniGlobalRef<jthrowable> translate_to_java() const override;
    };
} // namespace ujr
