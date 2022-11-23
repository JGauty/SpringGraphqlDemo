package com.example.springgraphqldemo.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UtilHelper {
    public <T extends Comparable<T>> boolean isBetween(T valToCompare, T start, T end) {
        return valToCompare.compareTo(start) >= 0 && valToCompare.compareTo(end) <= 0;
    }
}
