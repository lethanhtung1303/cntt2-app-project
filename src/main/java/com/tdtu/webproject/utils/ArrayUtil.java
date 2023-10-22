package com.tdtu.webproject.utils;

import java.util.List;
import java.util.Optional;

public class ArrayUtil {
    public static boolean isNotNullOrEmptyList(List<?> arrayList) {
        return Optional.ofNullable(arrayList).isPresent() && !arrayList.isEmpty();
    }
}
