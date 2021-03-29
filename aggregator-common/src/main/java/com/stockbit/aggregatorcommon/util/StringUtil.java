package com.stockbit.aggregatorcommon.util;

public final class StringUtil {

    private StringUtil() {
    }

    public static boolean isEmpty(String token) {
        return null == token || token.trim().isEmpty();
    }
}
