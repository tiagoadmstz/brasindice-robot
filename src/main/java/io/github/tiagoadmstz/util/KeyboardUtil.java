package io.github.tiagoadmstz.util;

public abstract class KeyboardUtil {

    /**
     * The text is parsed returning a command array
     *
     * @param text to be parse
     * @return Integer[][]
     */
    public static Integer[][] parseStringToKeyEventArray(String text) {
        char[] chars = text.toCharArray();
        Integer[][] keyEvents = new Integer[chars.length][3];
        for (int c = 0; c < chars.length; c++) keyEvents[c] = parseCharToKeyEvent(chars[c]);
        return keyEvents;
    }

    /**
     * The char variable is parsed returning a KeyEvent array
     *
     * @param textChar char character
     * @return Integer[]
     */
    private static Integer[] parseCharToKeyEvent(char textChar) {
        if (textChar == 95) return new Integer[]{45, 1, 16};
            //numeros
        else if (textChar >= 48 && textChar <= 57) return new Integer[]{(int) textChar, 1};
            //maiuscula
        else if (textChar >= 65 && textChar <= 90) return new Integer[]{(int) textChar, 1, 16};
            //minuscula
        else return new Integer[]{textChar - 32, 1};
    }

}
