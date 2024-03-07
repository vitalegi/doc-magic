package it.vitalegi.docsmagic.util;

public class StringUtil {

    public static String leftPad(String str, int len, char c) {
        while (str.length() < len) {
            str = c + str;
        }
        return str;
    }
}
