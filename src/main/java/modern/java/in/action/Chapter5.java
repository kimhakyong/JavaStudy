package modern.java.in.action;

import modern.java.in.action.chapter1.Transaction;
import modern.java.in.action.chapter4.Dish;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public class Chapter5 {
    public static void main(String[] args) {
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

        transactions.stream().filter(t -> t.getYear() == 2011)
                .sorted(comparing(Transaction::getValue).reversed())
                .forEach(System.out::println);

        transactions.stream().map(t -> t.getTrader().getCity()).distinct().forEach(System.out::println);

        System.out.println("====================");

        transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .sorted(comparing(t -> t.getTrader().getName()))
                .forEach(System.out::println);

        transactions.stream().sorted(comparing(t -> t.getTrader().getName()))
                .forEach(System.out::println);

        boolean bool = transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println("Milan : " + bool);

        System.out.println("====================");
        transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        Optional<Integer> max = transactions.stream().map(Transaction::getValue).reduce(Integer::max);
        System.out.println("Max : " + max.get());

        Optional<Integer> min = transactions.stream().map(Transaction::getValue).reduce(Integer::min);
        System.out.println("nin : " + min.get());

        int sumInt = Dish.menu.stream().mapToInt(Dish::getCalories).sum();
        OptionalInt sumInt2 = Dish.menu.stream().mapToInt(Dish::getCalories).max();

        System.out.println(sumInt);

        IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count());

        Stream<int[]> triples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100).filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                );

        Stream<double[]> dtriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100).mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(t -> t[2] % 1 == 0));

        triples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
        dtriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        Stream<String> stream = Stream.of("Modern", "Java", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        String homeValue = System.getProperty("home");
//        Stream<String> homeValueStream = Stream.of(homeValue);
//        Stream<String> homeValueStream = Stream.empty();
        Stream<String> homeValueStream = Stream.ofNullable(homeValue);

        homeValueStream.forEach(System.out::println);

        int[] numbers = {2, 3, 5, 7, 11, 13};
        int sum = Arrays.stream(numbers).sum();

        System.out.println(sum);

        long uniqueWords = 0;
        String FilePath = "D:\\09.Work\\Project\\IdeaProjects\\JavaStudy\\src\\main\\java\\modern\\java\\in\\action\\data.txt";
        try (Stream<String> lines = Files.lines(Paths.get(FilePath), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("word : " + uniqueWords);

        Stream.iterate(0, n -> n + 2).limit(100).forEach(System.out::println);

        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .map(t -> t[0])
                .forEach(System.out::println);

        IntStream.iterate(0, n -> n < 100, n -> n + 4).forEach(System.out::println);

        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

        IntSupplier fib = new IntSupplier() {
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
        };

        IntStream.generate(fib).limit(10).forEach(System.out::println);
    }
}
