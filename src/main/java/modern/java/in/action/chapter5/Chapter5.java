package modern.java.in.action.chapter5;

import modern.java.in.action.sample.Transaction;
import modern.java.in.action.sample.Dish;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import static modern.java.in.action.sample.Dish.menu;
import static modern.java.in.action.sample.Dish.specialMenu;

public class Chapter5 {
    public static void main(String[] args) {
        useStreamFiltering();
        useStreamSlicing();
        useStreamLimitSkip();
        useStreamMap();
        useFindMatch();
        useReduce();

        actualPractice();

        usePrimitiveStream();

        pythagorasNumber();

        makeStream();
    }

    private static void makeStream() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        // of()
        Stream<String> stream = Stream.of("Modern", "Java", "In ", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        Stream<Integer> intStream = Stream.of(1, 2, 3, 4, 5);
        intStream.forEach(System.out::println);

        // empty()
        Stream<String> emptyStream = Stream.empty();

        // ofNullable()
        String homeValue = System.getProperty("home");
        Stream<String> homeValueStream = (homeValue == null ? Stream.empty() : Stream.of(homeValue));
        Stream<String> nullableValue = Stream.ofNullable(homeValue);

        Map<String, String> map = new HashMap<>();
        map.put("config", "config string");
//        map.put("home", null);
        map.put("user", "user name");

        Stream<String> values = Stream.of("config", "home", "user")
//                .flatMap(key -> Stream.ofNullable(map.get(key)));
                .map(key -> map.get(key));
        values.forEach(System.out::println);

        // Arrays.stream()
        int[] numbers = {2, 3, 5, 7, 11, 13};
//        int sum = Arrays.stream(numbers).sum();
        int sum = IntStream.of(numbers).sum();
        System.out.println(sum);

        String filePath = "C:\\Projects\\IdeaProjects\\JavaStudy\\src\\main\\java\\modern\\java\\in\\action\\chapter5\\data.txt";
        long uniqueWords = 0;
        try (Stream<String> lines = Files.lines(Paths.get(filePath), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(uniqueWords);

        // Infinite stream
        // iterate(), generate()
        Stream.iterate(0, n -> n + 2)
                .limit(5)
                .forEach(System.out::println);

        // Fibonacci numbers[sequence]
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(5)
                .forEach(t -> System.out.println("(" + + t[0] + ", " + t[1] + ")"));

        // since JDK 9
        IntStream.iterate(0, n -> n < 100, n -> n + 4)
                .forEach(System.out::println);

        // Infinite loop
        IntStream.iterate(0, n -> n + 4)
//                .filter(n -> n < 100)
                .takeWhile(n -> n < 100)
                .forEach(System.out::println);

        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

        IntStream.generate(new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            @Override
            public int getAsInt() {
                int oldPrevious = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;

                return oldPrevious;
            }
        }).limit(5).forEach(System.out::println);
    }

    private static void pythagorasNumber() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Stream<long[]> pythagoreanTriples = LongStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> LongStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b -> new long[]{a, b, (long) Math.sqrt(a * a + b * b)})
                );

        pythagoreanTriples
                .limit(5)
                .forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        System.out.println("============");

        Stream<double[]> pythagoreanTriples2 = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(t -> t[2] % 1 == 0)
                );

        pythagoreanTriples2
                .limit(5)
                .forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }

    // Primitive Stream
    // IntStream, DoubleStream, LongStream
    private static void usePrimitiveStream() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        int calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println(calories);

        // Primitive Stream
        // mapToInt -> IntStream
        // mapToDouble -> DoubleStream
        // mapToLong -> LongStream
        // max, min, average
        calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> stream = intStream.boxed();

        // Optional
        // OptionalInt, OptionalDouble, OptionalLong
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        System.out.println(maxCalories.orElse(-1));

        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count());
    }

    private static void actualPractice() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        // 1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오
        List<Integer> solution = transactions.stream()
                .filter(t -> t.getYear() == 2011)
//                .sorted(comparing(Transaction::getValue))
                .sorted(comparing(Transaction::getValue).reversed())
                .map(Transaction::getValue)
                .collect(toList());
        System.out.println(solution);

        // 2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
        List<String> cities = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .collect(toList());
        System.out.println(cities);

        // 3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
        List<String> persons = transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(t -> t.getTrader().getName())
                .distinct()
//                .sorted()
                .sorted(Comparator.reverseOrder())
                .collect(toList());
        System.out.println(persons);

        // 4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.
        List<String> persons2 = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .collect(toList());
        System.out.println(persons2);

        String persons3 = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
//                .reduce("", (n1, n2) -> n1 + n2);
                .collect(joining());
        System.out.println(persons3);

        // 5. 밀라노에 거래자가 있는가?
        Boolean isOk = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println(isOk);

        // 6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        // 7. 전체 트랜잭션 중 최댓값은 얼마인가?
        Optional<Integer> maxValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        System.out.println(maxValue.get());

        // 8. 전체 트랜잭션 중 최솟값은 얼마인가?
        Optional<Integer> minValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);
        System.out.println(minValue.get());

        Optional<Transaction> minTransaction = transactions.stream()
                .min(comparing(Transaction::getValue));
        System.out.println(minTransaction.get());

    }

    public static void useReduce() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        int sum = 0;
        int[] numbers = {1, 2, 3, 4, 5};
        for (int x : numbers) {
            sum += x;
        }

        Integer[] numbers2 = Arrays.stream(numbers).boxed().toArray(Integer[]::new);
        List<Integer> listNums = Arrays.asList(numbers2);

        sum = listNums.stream().reduce(0, (a, b) -> a + b);
        sum = listNums.stream().reduce(0, Integer::sum);
        int product = listNums.stream().reduce(1, (a, b) -> a * b);
        System.out.println(sum + " : " + product);

        Optional<Integer> oSum = listNums.stream().reduce(Integer::sum);
        System.out.println(oSum.orElse(0));

        int max = listNums.stream().reduce(0, (a, b) -> a > b ? a : b);
        int min = listNums.stream().reduce(3, Integer::min);
        System.out.println(max + " : " + min);

        int count = menu.stream()
                .map(d -> 1)
                .reduce(0, Integer::sum);
        System.out.println("count : " + count);
    }

    public static void useFindMatch() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        System.out.println(menu.stream().anyMatch(Dish::isVegetarian));
        System.out.println(menu.stream().allMatch(dish -> dish.getCalories() < 1000));
        System.out.println(menu.stream().noneMatch(dish -> dish.getCalories() >= 1000));

        // Optional : isPresent(), ifPresent(Consumer), get(), orElse(other)
        Optional<Dish> dish = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();
        System.out.println(dish);

        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(d -> System.out.println(d.getName()));

        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        someNumbers.stream()
                .map(n -> n * n)
                .filter(n -> n % 3 == 0)
//                .findFirst()
                .findAny()
                .ifPresent(System.out::println);
    }

    public static void useStreamMap() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        List<String> dishNames = menu.stream()
                .map(Dish::getName)
//                .map(dish -> "echo")
                .collect(toList());
        System.out.println(dishNames);

        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
//                .map(word -> word.length())
                .collect(toList());
        System.out.println(wordLengths);

        List<Integer> dishNameLengths = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(toList());
        System.out.println(dishNameLengths);

        // ["Hello", "World"] -> ["H", "e", "l", "o", "W", "r", "d"]
        String[] arrayOfWords = {"Hello", "World"};
        List<String> listOfWords = Arrays.asList(arrayOfWords);
        List<String[]> test1 = listOfWords.stream()
                .map(word -> word.split(""))
                .distinct()     // meaningless
                .collect(toList());
        System.out.println(test1);

        List<Stream<String>> test2 = listOfWords.stream()
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(toList());
        System.out.println(test2);

        List<String> test3 = listOfWords.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());
        System.out.println(test3);

        // [1, 2, 3, 4, 5] -> [1, 4, 9, 16, 25]
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5).stream()
                .map(num -> num * num)
                .collect(toList());
        System.out.println(numbers);

        // [1, 2, 3], [3, 4] -> [(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3,4)]
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j}))
                .collect(toList());
        pairs.forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));
        System.out.println();

        numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j}))
                .forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));
        System.out.println();

        pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(toList());
        pairs.forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));
        System.out.println();

    }

    public static void useStreamLimitSkip() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        List<Dish> dishes = specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .collect(toList());
        System.out.println(dishes);

        dishes = specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .collect(toList());
        System.out.println(dishes);

        dishes = menu.stream()
                .filter(dish -> dish.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(toList());
        System.out.println(dishes);
    }

    public static void useStreamSlicing() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(toList());
        System.out.println(filteredMenu);

        List<Dish> slicedMenu1 = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(toList());
        System.out.println(slicedMenu1);

        List<Dish> slicedMenu2 = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(toList());
        System.out.println(slicedMenu2);
    }

    public static void useStreamFiltering() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        // predicate
        List<Dish> vegetarianMenu = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(toList());
        System.out.println(vegetarianMenu);

        // distinct
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
    }
}
