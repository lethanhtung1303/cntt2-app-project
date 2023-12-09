package com.tdtu.webproject.utils;


import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.Optional;

public class NumberUtil {
    public static Optional<BigDecimal> toBigDeimal(String str) {
        return Optional.ofNullable(str)
                .filter(NumberUtils::isCreatable)
                .map(v -> new BigDecimal(str));
    }
}
