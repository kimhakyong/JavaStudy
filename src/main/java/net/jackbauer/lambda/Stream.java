package net.jackbauer.lambda;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Stream {
    public static void main(String[] args) {
        System.out.println(Arrays.asList(1, 2,3));

        Arrays.asList(1, 2, 3, 4, 5).stream().forEach(System.out::println);
        Arrays.asList(1, 2, 3, 4, 5).stream().map(i -> i * i).forEach(System.out::println);
        Arrays.asList(1, 2, 3, 4, 5).stream().limit(1).forEach(System.out::println);
        Arrays.asList(1, 2, 3, 4, 5).stream().skip(1).forEach(System.out::println);
        Arrays.asList(1, 2, 3, 4, 5).stream().filter(i -> 2 >= i).forEach(System.out::println);

        Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4, 5), Arrays.asList(6, 7, 8, 9)).stream()
                .flatMap(i -> i.stream())
                .forEach(System.out::println);

        int val = Arrays.asList(1, 2, 3).stream().reduce((a, b) -> a - b).get();
        System.out.println(val);

        System.out.println(Arrays.asList(1, 2, 3).stream().collect(Collectors.toList()));
        System.out.println(Arrays.asList(1, 2, 3).stream().iterator());
    }
}
