package modern.java.in.action;

import java.util.*;

import static java.util.stream.Collectors.*;

public class Chapter6 {
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
        System.out.println("===============");

        Map<Currency, List<Transaction>> transByCurrencies =
                transactions.stream().collect(groupingBy(Transaction::getCurrency));

        long cnt1 = Dish.menu.stream().collect(counting());
        long cnt2 = Dish.menu.stream().count();

        System.out.println(cnt1 + " : " + cnt2);

        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = Dish.menu.stream().collect(maxBy(dishCaloriesComparator));
        System.out.println(mostCalorieDish.get());

        int totalCalories = Dish.menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println("sum : " + totalCalories);

        System.out.println(Dish.menu.stream().mapToInt(Dish::getCalories).max());

        IntSummaryStatistics intSummaryStatistics = Dish.menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(intSummaryStatistics);

        String shortMenu = Dish.menu.stream().map(Dish::getName).collect(joining(","));
        System.out.println(shortMenu);

        int totCal = Dish.menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
        System.out.println(totCal);

        int totCal2 = Dish.menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
    }

    public static class Transaction {
        private final Currency currency;
        private final double value;

        public Transaction(Currency currency, double value) {
            this.currency = currency;
            this.value = value;
        }

        public Currency getCurrency() {
            return currency;
        }

        public double getValue() {
            return value;
        }

        @Override
        public String toString() {
            return currency + " " + value;
        }

    }

    public enum Currency {
        EUR, USD, JPY, GBP, CHF
    }
}
