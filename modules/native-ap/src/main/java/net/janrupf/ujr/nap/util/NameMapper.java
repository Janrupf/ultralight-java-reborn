package net.janrupf.ujr.nap.util;

import java.util.Collections;
import java.util.Set;

/**
 * Helper for remapping identifiers.
 */
public class NameMapper {
    private static final Set<String> FORBIDDEN_KEYWORDS = Collections.singleton("NULL");

    /**
     * Converts the given input to screaming snake case.
     *
     * @param input the input to convert
     * @return the converted input
     */
    public static String toScreamingSnakeCase(String input) {
        StringBuilder builder = new StringBuilder();
        boolean lastWasLower = false;
        boolean lastWasUpperCase = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (Character.isUpperCase(c)) {
                if (lastWasLower) {
                    builder.append('_');
                }

                // Lookahead to detect acronyms
                if (lastWasUpperCase && input.length() > i + 1) {
                    char next = input.charAt(i + 1);
                    if (Character.isLowerCase(next)) {
                        builder.append('_');
                    }
                }

                builder.append(Character.toUpperCase(c));
            } else {
                builder.append(Character.toUpperCase(c));
            }

            lastWasUpperCase = Character.isUpperCase(c);
            lastWasLower = Character.isLowerCase(c);
        }

        if (FORBIDDEN_KEYWORDS.contains(builder.toString())) {
            builder.append('_');
        }

        return builder.toString();
    }
}
