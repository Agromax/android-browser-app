package com.browser;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Dell on 09-06-2016.
 */
public class ListUtil {
    /**
     * Joins a list with the given delimiter
     *
     * @param list
     * @param delimiter
     * @param <T>
     * @return
     */
    public static <T> String join(@NonNull List<T> list, @NonNull CharSequence delimiter) {
        StringBuilder result = new StringBuilder();

        for (T listElement : list) {
            result.append(listElement).append(delimiter);
        }

        if (result.length() > delimiter.length()) {
            result.delete(result.length() - delimiter.length(), result.length());
        }
        return result.toString();
    }
}
