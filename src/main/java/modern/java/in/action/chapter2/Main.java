package modern.java.in.action.chapter2;

import org.apache.logging.log4j.core.util.JsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static modern.java.in.action.chapter2.Color.GREEN;
import static modern.java.in.action.chapter2.Color.RED;

public class Main {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, GREEN),
                new Apple(155, GREEN),
                new Apple(160, RED));

        List<Apple> apples = filterGreenApples(inventory);
        apples.forEach(System.out::println);

        System.out.println("==========");

        apples = filterApplesByColor(inventory, RED);
        apples.forEach(System.out::println);

        System.out.println("==========");

        apples = filterApples(inventory, new AppleHeavyWeightPredicate());
        apples.forEach(System.out::println);

        System.out.println("==========");

        apples = filterApples(inventory, new AppleGreenColorPredicate());
        apples.forEach(System.out::println);

        System.out.println("==========");

        apples = filterApples(inventory, new AppleRedAndHeavyPredicate());
        apples.forEach(System.out::println);

        System.out.println("==========");

        prettyPrintApple(inventory, new AppleFancyFormatter());
        prettyPrintApple(inventory, new AppleSimpleFormatter());

        System.out.println("==========");

        apples = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return RED.equals(apple.getColor());
            }
        });
        apples.forEach(System.out::println);

        System.out.println("==========");

        inventory.sort((Apple a1, Apple a2) -> Integer.valueOf(a2.getWeight()).compareTo(a1.getWeight()));
        inventory.forEach(System.out::println);
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }

        return result;
    }

    public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }

        return result;
    }

    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }

        return result;
    }

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }

    public static void prettyPrintApple(List<Apple> inventory, AppleFormatter f) {
        for (Apple apple : inventory) {
            String output = f.accept(apple);
            System.out.println(output);
        }
    }
}
