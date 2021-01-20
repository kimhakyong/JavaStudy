package modern.java.in.action.chapter6;

import modern.java.in.action.sample.Dish;
import net.jackbauer.enumtype.CalculatorType;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static modern.java.in.action.sample.Dish.menu;

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

        // Multi Grouping
        useCollectorsMultiGrouping();

        // Partitioning
        useCollectorsPartitioning();

        // Custom Collectors
        useCustomCollectors();
    }

    private static void useCustomCollectors() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        List<Dish> dishes = menu.stream().collect(toList());
        dishes = menu.stream().collect(new ToListCollector<>());
        System.out.println(dishes);

        dishes = menu.stream().collect(
                ArrayList::new,
                List::add,
                List::addAll
        );
        System.out.println(dishes);

        Map<Boolean, List<Integer>> primeMap = IntStream.rangeClosed(2, 100).boxed()
                .collect(new PrimeNumbersCollector());
        System.out.println(primeMap);
    }

    private static void useCollectorsPartitioning() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Map<Boolean, List<Dish>> partitionedMenu = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian));
        System.out.println(partitionedMenu);

        List<Dish> vegetarianDishes = partitionedMenu.get(true);
        System.out.println(vegetarianDishes);

        vegetarianDishes = menu.stream().filter(Dish::isVegetarian).collect(toList());
        System.out.println(vegetarianDishes);

        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        groupingBy(Dish::getType)));
        System.out.println(vegetarianDishesByType);

        Map<Boolean, Optional<Dish>> mostCaloricParitionedByVegetarian = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        maxBy(comparingInt(Dish::getCalories))));
        System.out.println(mostCaloricParitionedByVegetarian);

        Map<Boolean, Dish> mostCaloricParitionedByVegetarian2 = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        collectingAndThen(
                                maxBy(comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(mostCaloricParitionedByVegetarian2);

        Map<Boolean, Map<Boolean, List<Dish>>> partDish1 = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        partitioningBy(d -> d.getCalories() > 500)));
        System.out.println(partDish1);

//        Map<Boolean, Map<Boolean, List<Dish>>> partDish2 = menu.stream()
//                .collect(partitioningBy(Dish::isVegetarian,
//                        partitioningBy(Dish::getType))); -> predicate 아님. 오류

        Map<Boolean, Long> countDish = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, counting()));
        System.out.println(countDish);

        Map<Boolean, List<Integer>> primeMap = IntStream.rangeClosed(2, 100).boxed()
                .collect(partitioningBy(Chapter6::isPrimeType2));
        System.out.println(primeMap);

    }

    public static boolean isPrimeType1(int candidate) {
        return IntStream.range(2, candidate).noneMatch(i -> candidate % i == 0);
    }

    public static boolean isPrimeType2(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.range(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }

    public static boolean isPrimeType3(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return primes.stream()
                .takeWhile(i -> i <= candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    public static boolean isPrimeType4(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return takeWhile(primes, i -> i <= candidateRoot)
                .stream()
                .noneMatch(p -> candidate % p == 0);
    }

    /**
     * takeWhile JAVA 9부터 지원
     * JAVA 8에서는 아래와 같이 구현
     */
    public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
        int i = 0;

        for (A item : list) {
            if (!p.test(item)) {
                return list.subList(0, i);
            }
            i++;
        }

        return list;
    }

    private static void useCollectorsMultiGrouping() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream()
                .collect(
                        groupingBy(Dish::getType,
                                groupingBy(dish -> {
                                    if (dish.getCalories() <= 400)
                                        return CaloricLevel.DIET;
                                    else if (dish.getCalories() <= 700)
                                        return CaloricLevel.NORMAL;
                                    else
                                        return CaloricLevel.FAT;
                                }))
                );
        System.out.println(dishesByTypeCaloricLevel);

        Map<Dish.Type, Long> typesCount = menu.stream()
                .collect(groupingBy(Dish::getType, counting()));
        System.out.println(typesCount);

        Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        maxBy(comparingInt(Dish::getCalories))));
        System.out.println(mostCaloricByType);

        Map<Dish.Type, Dish> mostCaloricByType2 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(
                                maxBy(comparingInt(Dish::getCalories)), Optional::get)
                ));
        System.out.println(mostCaloricByType2);

        Map<Dish.Type, Integer> totalCaloriesByType = menu.stream()
                .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        System.out.println(totalCaloriesByType);

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        mapping(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }, toSet())
                ));
        System.out.println(caloricLevelsByType);

        Map<Dish.Type, List<CaloricLevel>> caloricLevelsByType2 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        mapping(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }, toList())
                ));
        System.out.println(caloricLevelsByType2);

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType3 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        mapping(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }, toCollection(HashSet::new))
                ));
        System.out.println(caloricLevelsByType3);
    }

    public enum CaloricLevel {DIET, NORMAL, FAT}

    private static void useCollectorsGrouping() {
        System.out.println("*** " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Map<Dish.Type, List<Dish>> dishesByType = menu.stream()
                .collect(groupingBy(Dish::getType));
        System.out.println(dishesByType);

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream()
                .collect(groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));
        System.out.println(dishesByCaloricLevel);

        // filter 조건에 없는 항목은 key에서 제외
        Map<Dish.Type, List<Dish>> caloricDishesByType = menu.stream()
                .filter(dish -> dish.getCalories() > 500)
                .collect(groupingBy(Dish::getType));
        System.out.println(caloricDishesByType);

        // filter 조건에 없는 항목도 key에 포함
        caloricDishesByType = menu.stream()
                .collect(groupingBy(Dish::getType,
                        filtering(dish -> dish.getCalories() > 500, toList())));
        System.out.println(caloricDishesByType);

        Map<Dish.Type, List<String>> dishNamesByType = menu.stream()
                .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
        System.out.println(dishNamesByType);

        Map<String, List<String>> dishTags = new HashMap<>();
        dishTags.put("port", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("frech fries", asList("greasy", "fried"));

        Map<Dish.Type, Set<String>> dishNamesByType2 = menu.stream()
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

        long howManyDishes = menu.stream().collect(counting());
        howManyDishes = menu.stream().count();
        howManyDishes = menu.size();

        System.out.println(howManyDishes);

        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCaloriesDish = menu.stream().collect(maxBy(dishCaloriesComparator));
        System.out.println(mostCaloriesDish.get());

        // summingInt(), summingLong(), summingDouble()
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println(totalCalories);

        // averagingInt(), averagingLong(), averagingDouble()
        double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));
        System.out.println(avgCalories);

        // summarizingInt() : IntSummaryStatistics
        // summarizingLong() : LongSummaryStatistics
        // summarizingDouble() : DoubleSummaryStatistics
        IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(menuStatistics);

        // joining() : 내부적으로 StringBuilder 사용
//        String shortMenu = Dish.menu.stream().map(Dish::getName).collect(joining());
        String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));
//        shortMenu = Dish.menu.stream().collect(joining()); -> Dish.toString() 호출로 안됨
        System.out.println(shortMenu);

        String testStr = Arrays.stream(new String[]{"aaa", "bbb"}).collect(joining());

        // reducing()
        int totalCalories2 = menu.stream()
                .collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
        totalCalories2 = menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
        totalCalories2 = menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
        totalCalories2 = menu.stream().mapToInt(Dish::getCalories).sum();

        System.out.println(totalCalories2);

        Optional<Dish> mostCaloriesDish2 = menu.stream()
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
}
