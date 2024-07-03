package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println("Min from {1,2,3,3,2,3} = " + minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println("Min from {9,8} = " + minValue(new int[]{9, 8}));
        System.out.println("Min from {9,6,8,1,9,1} = " + minValue(new int[]{9, 6, 8, 1, 9, 1}));
        System.out.println("Min from {} = " + minValue(new int[]{}));

        List<Integer> list = new ArrayList<>();
        System.out.println("oddOrEven from [] = " + oddOrEven(list));
        list.add(1);
        System.out.println("oddOrEven from [1] = " + oddOrEven(list));
        list.add(0);
        list.add(12);
        System.out.println("oddOrEven from [1,0,12] = " + oddOrEven(list));
        list.add(1);
        System.out.println("oddOrEven from [1,0,12,2,1] = " + oddOrEven(list));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> parts = integers.stream().collect(Collectors.partitioningBy(i -> i % 2 != 0));
        return parts.get(parts.get(true).size() % 2 == 0);
    }
}
