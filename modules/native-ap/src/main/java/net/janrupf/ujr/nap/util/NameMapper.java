package net.janrupf.ujr.nap.util;

/**
 * Helper for remapping identifiers.
 */
public class NameMapper {
    /**
     * Converts the given input to screaming snake case.
     *
     * @param input the input to convert
     * @return the converted input
     */
    public static String toScreamingSnakeCase(String input) {
        StringBuilder builder = new StringBuilder();
        boolean lastWasLower = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (Character.isUpperCase(c)) {
                if (lastWasLower) {
                    builder.append('_');
                }

                builder.append(Character.toUpperCase(c));
                lastWasLower = false;
            } else {
                builder.append(Character.toUpperCase(c));
                lastWasLower = true;
            }
        }

        return builder.toString();
    }
}
