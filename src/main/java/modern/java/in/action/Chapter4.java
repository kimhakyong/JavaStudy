package modern.java.in.action;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static java.util.stream.Collectors.toList;

public class Chapter4 {
    public static void main(String[] args) {
        List<String> threeHighCaloricDishNames = Dish.menu.stream()
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
        System.out.println(threeHighCaloricDishNames);

        Dish.menu.stream().forEach(System.out::println);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 4, 5, 1, 2, 8);
        numbers.stream().filter(i -> i % 2 == 0).distinct().forEach(System.out::println);

        List<Dish> specialMenu = Arrays.asList(
                new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 320, Dish.Type.OTHER),
                new Dish("chicken", false, 320, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER)
        );

        List<Dish> filteredMenu = specialMenu.stream().filter(dish -> dish.getCalories() < 320).collect(toList());
        System.out.println(filteredMenu);

        filteredMenu = specialMenu.stream().dropWhile(dish -> dish.getCalories() <= 320).collect(toList());
        System.out.println(filteredMenu);

        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
        List<Integer> wordLengths = words.stream().map(String::length).collect(toList());
        System.out.println(wordLengths);

        List<Integer> sqrts = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(sqrts.stream().map(i -> i * i).collect(toList()));

        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
//        List<int[]> pairs = numbers1.stream().flatMap(i -> numbers2.stream().map(j -> new int[]{i, j}))
//                .collect(toList());

        List<int[]> pairs = numbers1.stream().flatMap(i -> {
               return numbers2.stream().filter(j -> (i + j) % 3 == 0).map(j -> new int[]{1, j});
        }).collect(toList());

        Optional<Dish> dish = Dish.menu.stream().filter(Dish::isVegetarian).findAny();
        System.out.println(dish);

        Dish.menu.stream().filter(Dish::isVegetarian).findAny().ifPresent(d -> System.out.println(d.getName()));

        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> num3 = someNumbers.stream().map(n -> n * n).filter(n -> n % 3 == 0).findFirst();
        num3.ifPresent(System.out::println);

        List<Integer> sNums = Arrays.asList(1, 2, 3, 4, 5, 6);
        int sum = sNums.stream().reduce(0, Integer::max);
        System.out.println(sum);

        long count = Dish.menu.stream().map(d -> 1).count();
        System.out.println(count);
    }
}