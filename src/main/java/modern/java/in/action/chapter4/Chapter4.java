package modern.java.in.action.chapter4;

import modern.java.in.action.sample.Dish;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static modern.java.in.action.sample.Dish.menu;

/**
 * Stream API 특징
 * - 선언형 : 간결 / 가독성
 * - 조립할 수 있음 : 유연성
 * - 병렬화 : 성능
 */
public class Chapter4 {
    public static void main(String[] args) {
        useOldMethod();
        useNewMethod();

        // a stream can only be used once
        useOnlyOneStream();

        // external iteration / internal iteration
        useExternalInternalIteration();

        printStreamMethodResult();
    }

    private static void printStreamMethodResult() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        List<String> names = menu.stream()
                .filter(dish -> {
                    System.out.println("filtering : " + dish.getName());
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println("mapping : " + dish.getName());
                    return dish.getName();
                })
                .limit(3)
                .collect(toList());
        System.out.println(names);
    }

    private static void useExternalInternalIteration() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        List<String> names = new ArrayList<>();

        // iterator
        Iterator<Dish> iterator = menu.iterator();
        while (iterator.hasNext()) {
            Dish dish = iterator.next();
            names.add(dish.getName());
        }

        // for-each
        for (Dish dish : menu) {
            names.add(dish.getName());
        }

        // stream
        names = menu.stream()
                .map(dish -> dish.getName())
                .collect(toList());
        System.out.println(names);

        // before refactoring
        List<Dish> highCaloricDishs = new ArrayList<>();
        Iterator<Dish> iterator2 = menu.iterator();
        while (iterator2.hasNext()) {
            Dish dish = iterator2.next();
            if (dish.getCalories() > 300) {
                highCaloricDishs.add(dish);
            }
        }
        System.out.println(highCaloricDishs);

        // after refactoring
        highCaloricDishs = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .collect(toList());
        System.out.println(highCaloricDishs);
    }

    private static void useOnlyOneStream() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        List<String> title = Arrays.asList("Java8", "In", "Action");
        Stream<String> s = title.stream();
        s.forEach(System.out::println);
        try {
            s.forEach(System.out::println);
        } catch (IllegalStateException ex) {
            System.out.println("Exception 발생 : " + ex);
            System.out.println("a stream can only be used once.");
        }
    }

    private static void useNewMethod() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        List<String> lowCaloricDishesName = menu.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());

        lowCaloricDishesName = menu.parallelStream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());

        lowCaloricDishesName.forEach(System.out::println);

        List<String> threedHighCalroicDishNames = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(toList());

        System.out.println(threedHighCalroicDishNames);
    }

    public static void useOldMethod() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        List<Dish> lowCaloricDishes = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() < 400) {
                lowCaloricDishes.add(dish);
            }
        }

        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });

        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish dish : lowCaloricDishes) {
            lowCaloricDishesName.add(dish.getName());
        }

        lowCaloricDishesName.forEach(System.out::println);
    }
}



