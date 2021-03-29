package com.stockbit.aggregatorcommon.util;

import java.util.List;

public final class ListUtil {

    private ListUtil() {
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return null != list && !list.isEmpty();
    }
}
