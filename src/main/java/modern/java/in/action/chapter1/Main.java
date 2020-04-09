package modern.java.in.action.chapter1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static modern.java.in.action.chapter1.Color.GREEN;
import static modern.java.in.action.chapter1.Color.RED;

public class Main {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, GREEN),
                new Apple(155, GREEN),
                new Apple(120, RED));

        List<Apple> resultList = filterApples(inventory, Apple::isGreenApple);
        resultList.forEach(System.out::println);

        System.out.println("==========");

        resultList = filterApples(inventory, Apple::isHeavyApple);
        resultList.forEach(System.out::println);

        System.out.println("==========");

        resultList = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
        resultList.forEach(System.out::println);

        System.out.println("==========");

        resultList = filterApples(inventory, (Apple a) -> GREEN.equals(a.getColor()));
        resultList.forEach(System.out::println);

        System.out.println("==========");

        inventory.stream()
                .filter((Apple a) -> GREEN.equals(a.getColor()))
                .collect(toList())
                .forEach(System.out::println);
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }
}
