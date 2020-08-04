package io.github.tiagoadmstz.util;

import java.text.Normalizer;

public abstract class WordUtils {

    /**
     * Removes all accentuations from the text
     *
     * @param text
     * @return String normalized
     */
    public static String removeAccentuation(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

}
