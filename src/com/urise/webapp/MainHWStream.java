package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainHWStream {
    public static void main(String[] args) {
        int[] values = {1, 2, 3, 3, 2, 3};
        int result = minValue(values);
        System.out.println(result);

        List<Integer> integers = Arrays.asList(0, 1, 2, 3, 4, 5, 1);
        System.out.println(oddOrEven(integers));
    }

    public static int minValue(int[] values) {
        return IntStream.of(values)
                .distinct()
                .sorted()
                .reduce(0, (s1, s2) -> s1 * 10 + s2);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int evenOrOdd = integers.stream()
                .mapToInt(num -> num)
                .sum() % 2;

        return integers.stream()
                .mapToInt(num -> num)
                .filter(o -> o % 2 != evenOrOdd)
                .boxed()
                .collect(Collectors.toList());
    }
}
