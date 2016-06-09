package com.browser;

import java.util.Random;

/**
 * Created by Dell on 09-06-2016.
 */
public class RandomShit {
    private final Random r;
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public RandomShit(long seed) {
        this.r = new Random(seed);
    }

    public String getHexString(int length) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = r.nextInt(HEX_CHARS.length);
            buffer.append(HEX_CHARS[index]);
        }
        return buffer.toString();
    }
}
