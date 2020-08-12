package com.learning.utils;

public class StringUtils {

    public static String toCapitalize(String name) {
        if (!name.contains(" ")) {
            return String.format("%s%s", name.substring(0, 1).toUpperCase(), name.substring(1));
        }

        String[] words = name.split("\\s");
        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            builder.append(String.format("%s%s ", word.substring(0, 1).toUpperCase(), word.substring(1)));
        }

        return builder.toString().trim();
    }

}
