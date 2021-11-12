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
        List<Integer> integersResult = oddOrEven(integers);
        System.out.println(integersResult);
    }

    public static int minValue(int[] values) {
        return IntStream.of(values).distinct().sorted().reduce((s1, s2) -> s1 * 10 + s2).getAsInt();
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        if (integers.stream().mapToInt(num -> num).sum() % 2 != 0) {
            return (List<Integer>) integers.stream().mapToInt(num -> num).filter(o -> o % 2 == 0).boxed().collect(Collectors.toList());
        } else {
            return (List<Integer>) integers.stream().mapToInt(num -> num).filter(o -> o % 2 != 0).boxed().collect(Collectors.toList());
        }
    }
}
