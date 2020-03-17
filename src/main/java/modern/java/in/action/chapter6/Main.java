package modern.java.in.action.chapter6;

import java.sql.SQLOutput;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class Main {
    public static List<Transaction> transactions = Arrays.asList(
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

    public static void main(String... args) {
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

        transactionsByCurrencies = transactions.stream().collect(groupingBy(Transaction::getCurrency));

        for (Currency currency : Currency.values()) {
            List<Transaction> transactionList = transactionsByCurrencies.get(currency);

            System.out.println("currency : " + currency);
            for (Transaction transaction : transactionList) {
                System.out.println(transaction);
            }
        }

        long howManyDishes = Dish.menu.stream().collect(Collectors.counting());
        System.out.println("count : " + howManyDishes);
        System.out.println("count : " + Dish.menu.stream().count());

        Comparator<Dish> dishComparator = comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = Dish.menu.stream().collect(maxBy(dishComparator));
        System.out.println(mostCalorieDish.orElse(null));

        int totalCalories = Dish.menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println("totalCalories : " + totalCalories);

        IntSummaryStatistics menuStatistics = Dish.menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(menuStatistics);

        String shortMenu = Dish.menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println(shortMenu);

//        String stringMenu = Dish.menu.stream().collect(joining());
//        System.out.println(stringMenu);

        totalCalories = Dish.menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
        System.out.println(totalCalories);

        mostCalorieDish = Dish.menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
        System.out.println(mostCalorieDish);

        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
        List<Integer> numbers = stream.reduce(new ArrayList<Integer>(),
                (List<Integer> l, Integer e) -> {
                    l.add(e);
                    return l; },
                (List<Integer> l1, List<Integer> l2) -> {
                    l1.addAll(l2);
                    return l1;
                }
                );

        totalCalories = Dish.menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
        totalCalories = Dish.menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
        totalCalories = Dish.menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println(totalCalories);

        Map<Dish.Type, List<Dish>> dishesByType = Dish.menu.stream().collect(groupingBy(Dish::getType));
        System.out.println(dishesByType);

        Map<CaloricLevel, List<Dish>> dishesByCalroicLevel = Dish.menu.stream().collect(groupingBy(
            dish -> {
                if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                else return CaloricLevel.FAT;
            }
        ));
        System.out.println(dishesByCalroicLevel);

        Map<Dish.Type, List<Dish>> caloricDishesByType = Dish.menu.stream().filter(dish -> dish.getCalories() > 500)
                .collect(groupingBy(Dish::getType));
        System.out.println(caloricDishesByType);

        Map<Dish.Type, List<Dish>> caloricDishesByType2 = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
        System.out.println(caloricDishesByType2);

        Map<Dish.Type, List<String>> dishNamesByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
        System.out.println(dishNamesByType);

        Map<Dish.Type, Set<String>> dishNamesByType2 = Dish.menu.stream()
                .collect(groupingBy(Dish::getType,
                        flatMapping(dish -> Dish.dishTags.get(dish.getName()).stream(),
                                toSet())));
        System.out.println(dishNamesByType2);

        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel
                = Dish.menu.stream().collect(groupingBy(Dish::getType, groupingBy(dish -> {
                    if (dish.getCalories() <= 400)
                        return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700)
                        return CaloricLevel.NORMAL;
                    else
                        return CaloricLevel.FAT;
        })));
        System.out.println(dishesByTypeCaloricLevel);

        Map<Dish.Type, Long> typesCount = Dish.menu.stream().collect(groupingBy(Dish::getType, counting()));
        System.out.println(typesCount);

        Map<Dish.Type, Optional<Dish>> mostCaloricByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
        System.out.println(mostCaloricByType);

        Map<Dish.Type, Dish> mostCaloricByType2 = Dish.menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(mostCaloricByType2);

        System.out.println("============");

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, mapping(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }, toCollection(HashSet::new))));
        System.out.println(caloricLevelByType);

        Map<Boolean, List<Dish>> partitionedMenu = Dish.menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println(partitionedMenu);

        List<Dish> vegetarianDishes = Dish.menu.stream().filter(Dish::isVegetarian).collect(toList());
        System.out.println(vegetarianDishes);

        for (int i = 0; i < 10; i++) {
            System.out.println(i + " : " + isPrime(i));
        }

        System.out.println(partitionPrimes(10));

        List<Dish> dishes = Dish.menu.stream().collect(new ToListCollector<Dish>());
        System.out.println(dishes);

        dishes = Dish.menu.stream().collect(toList());
        System.out.println(dishes);

        dishes = Dish.menu.stream().collect(ArrayList::new, List::add, List::addAll);
        System.out.println(dishes);

        System.out.println(partitionPrimesWithCustomCollector(10));
    }

    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double)candidate);
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

    public enum CaloricLevel {DIET, NORMAL, FAT}
}
