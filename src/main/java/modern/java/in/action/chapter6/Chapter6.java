package modern.java.in.action.chapter6;

import modern.java.in.action.sample.Dish;
import net.jackbauer.enumtype.CalculatorType;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class Chapter6 {
    public static List<Transaction> transactions = asList(
            new Transaction(Currency.EUR, 1500.0),
            new Transaction(Currency.USD, 2300.0),
            new Transaction(Currency.GBP, 9900.0),
            new Transaction(Currency.EUR, 1100.0),
            new Transaction(Currency.JPY, 7800.0),
            new Transaction(Currency.CHF, 6700.0),
            new Transaction(Currency.EUR, 5600.0),
            new Transaction(Currency.USD, 4500.0),
            new Transaction(Currency.CHF, 3400.0),
            new Transaction(Currency.GBP, 3200.0),
            new Transaction(Currency.USD, 4600.0),
            new Transaction(Currency.JPY, 5700.0),
            new Transaction(Currency.EUR, 6800.0)
    );

    /**
     * Collectors 제공 메서드 기능
     * - 스트림 요소를 하나의 값으로 리듀스하고 요약
     * - 요소 그룹화
     * - 요소 분할
     */
    public static void main(String... args) {
        useCollectorsFirst();

        // Summary, Reduce
        useCollectorsSummary();

        // Grouping
        useCollectorsGrouping();

        System.exit(0);

    }

    public enum CaloricLevel {DIET, NORMAL, FAT}

    private static void useCollectorsGrouping() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Map<Dish.Type, List<Dish>> dishesByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType));
        System.out.println(dishesByType);

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = Dish.menu.stream()
                .collect(groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));
        System.out.println(dishesByCaloricLevel);

        // filter 조건에 없는 항목은 key에서 제외
        Map<Dish.Type, List<Dish>> caloricDishesByType = Dish.menu.stream()
                .filter(dish -> dish.getCalories() > 500)
                .collect(groupingBy(Dish::getType));
        System.out.println(caloricDishesByType);

        // filter 조건에 없는 항목도 key에 포함
        caloricDishesByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType,
                        filtering(dish -> dish.getCalories() > 500, toList())));
        System.out.println(caloricDishesByType);

        Map<Dish.Type, List<String>> dishNamesByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
        System.out.println(dishNamesByType);

        Map<String, List<String>> dishTags = new HashMap<>();
        dishTags.put("port", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("frech fries", asList("greasy", "fried"));

        Map<Dish.Type, Set<String>> dishNamesByType2 = Dish.menu.stream()
                .collect(groupingBy(Dish::getType,
//                        flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));
                        flatMapping(dish -> {
                            List<String> tagList = dishTags.get(dish.getName());
                            return tagList != null ? tagList.stream() : Stream.empty();
                        }, toSet())));
        System.out.println(dishNamesByType2);
    }

    private static void useCollectorsSummary() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        long howManyDishes = Dish.menu.stream().collect(counting());
        howManyDishes = Dish.menu.stream().count();
        howManyDishes = Dish.menu.size();

        System.out.println(howManyDishes);

        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCaloriesDish = Dish.menu.stream().collect(maxBy(dishCaloriesComparator));
        System.out.println(mostCaloriesDish.get());

        // summingInt(), summingLong(), summingDouble()
        int totalCalories = Dish.menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println(totalCalories);

        // averagingInt(), averagingLong(), averagingDouble()
        double avgCalories = Dish.menu.stream().collect(averagingInt(Dish::getCalories));
        System.out.println(avgCalories);

        // summarizingInt() : IntSummaryStatistics
        // summarizingLong() : LongSummaryStatistics
        // summarizingDouble() : DoubleSummaryStatistics
        IntSummaryStatistics menuStatistics = Dish.menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(menuStatistics);

        // joining() : 내부적으로 StringBuilder 사용
//        String shortMenu = Dish.menu.stream().map(Dish::getName).collect(joining());
        String shortMenu = Dish.menu.stream().map(Dish::getName).collect(joining(", "));
//        shortMenu = Dish.menu.stream().collect(joining()); -> Dish.toString() 호출로 안됨
        System.out.println(shortMenu);

        String testStr = Arrays.stream(new String[]{"aaa", "bbb"}).collect(joining());

        // reducing()
        int totalCalories2 = Dish.menu.stream()
                .collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
        totalCalories2 = Dish.menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
        totalCalories2 = Dish.menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
        totalCalories2 = Dish.menu.stream().mapToInt(Dish::getCalories).sum();

        System.out.println(totalCalories2);

        Optional<Dish> mostCaloriesDish2 = Dish.menu.stream()
                .collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
        System.out.println(mostCaloriesDish2);
    }

    public static void useCollectorsFirst() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        // old pattern
        Map<Currency, List<Transaction>> transactionsByCurrencies = new HashMap<>();

        for (Transaction transaction : transactions) {
            Currency currency = transaction.getCurrency();
            List<Transaction> transactionsForCurrency = transactionsByCurrencies.get(currency);
            if (transactionsForCurrency == null) {
                transactionsForCurrency = new ArrayList<>();
                transactionsByCurrencies.put(currency, transactionsForCurrency);
            }
            transactionsForCurrency.add(transaction);
        }

        for (Currency currency : Currency.values()) {
            List<Transaction> transactionList = transactionsByCurrencies.get(currency);

            System.out.println("currency : " + currency);
            for (Transaction transaction : transactionList) {
                System.out.println(transaction);
            }
        }

        // new pattern
        transactionsByCurrencies = transactions.stream().collect(groupingBy(Transaction::getCurrency));

        for (Currency currency : Currency.values()) {
            transactionsByCurrencies.get(currency).stream().forEach(System.out::println);
        }
    }

    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(partitioningBy(candidate -> isPrime(candidate)));
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(new PrimeNumbersCollector());
    }
}
