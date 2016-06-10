package com.browser;

/**
 * @author Anurag Gautam
 */
public class XMLUtil {
    public static String asXMLTag(String s) {
        StringBuilder tag = new StringBuilder();
        String afterSpecialChars = s.replaceAll("[{}()+\\-/*;.,\"':\\[\\]|\\\\=_~!@#$%\\^&]+", "");
        String trimmed = afterSpecialChars.replaceAll("[\n\r\t ]+", " ").trim();
        for (String t : trimmed.split(" +")) {
            if (t.length() > 0) {
                tag.append(Character.toUpperCase(t.charAt(0))).append(t.substring(1));
            }
        }
        return tag.toString();
    }
}