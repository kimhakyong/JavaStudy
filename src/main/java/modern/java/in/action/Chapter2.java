package modern.java.in.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Chapter2 {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED));

        List<Apple> apples = filterGreenApples(inventory);
        for (Apple apple : apples) {
            System.out.println(apple.toString());
        }

        apples = filterApplesByColor(inventory, Color.RED);
        for (Apple apple : apples) {
            System.out.println(apple.toString());
        }

        apples = filterApples(inventory, new AppleHeavyWeightPredicate());
        for (Apple apple : apples) {
            System.out.println(apple.toString());
        }

        apples = filterApples(inventory, new AppleRedAndHeavyPredicate());
        for (Apple apple : apples) {
            System.out.println(apple.toString());
        }

        prettyPrintApple(inventory, new AppleFancyFormatter());

        apples = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return Color.RED.equals(apple.getColor());
            }
        });

        for (Apple apple : apples) {
            System.out.println(apple.toString());
        }

        System.out.println("==================================");

        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return Integer.valueOf(o2.getWeight()).compareTo(o1.getWeight());
            }
        });

        for (Apple apple : inventory) {
            System.out.println(apple.toString());
        }
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (Color.GREEN.equals(apple.getColor())) {
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
