package modern.java.in.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

import static java.util.Comparator.comparing;

public class Chapter3 {
    public static void main(String[] args) throws IOException {
        Runnable r1 = () -> System.out.println("Hello World 1");
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World 2");
            }
        };

        process(r1);
        process(r2);
        process(() -> System.out.println("Hello World 3"));

        String oneLine = processFile((BufferedReader br) -> br.readLine());
        String twoLine = processFile((BufferedReader br) -> br.readLine() + br.readLine());

        System.out.println(oneLine);
        System.out.println(twoLine);

        Predicate<String> nonEmptyStringPredicate = (s -> !s.isEmpty());
        List<String> nonEmpty = filter(Arrays.asList("a", "b", "c", ""), nonEmptyStringPredicate);

        for (String str : nonEmpty) {
            System.out.println(str);
        }

        forEach(Arrays.asList("aa", "bb", "cc", ""), s -> System.out.println(s));

        List<String> startWith = map(Arrays.asList("abc", "bcd", "attt"), (String str) -> str.startsWith("a") ? str : "");

        for (String str : startWith) {
            System.out.println(str);
        }

        List<String> str = Arrays.asList("a", "b", "A", "B");
        str.sort(String::compareToIgnoreCase);

        for (String s : str) System.out.println(s);

        Supplier<Apple> c1 = Apple::new;
        Apple a1 = c1.get();

        Supplier<Apple> c2 = () -> new Apple();
        Apple a2 = c2.get();

        Function<Integer, Apple> c3 = Apple::new;
        Apple a3 = c3.apply(100);

        List<Integer> weights = Arrays.asList(7, 3, 4, 10);
        List<Apple> apples = map(weights, Apple::new);

        for (Apple a : apples) {
            System.out.println(a);
        }

        BiFunction<Integer, Color, Apple> c4 = Apple::new;
        Apple a4 = c4.apply(115, Color.RED);

        System.out.println("================================");

        List<Apple> inventory = Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(155, Color.RED),
                new Apple(120, Color.RED));

//        inventory.sort((aa1, aa2) -> Integer.valueOf(aa1.getWeight()).compareTo(aa2.getWeight()));
        inventory.sort(comparing(Apple::getWeight));
        for (Apple a : inventory) {
            System.out.println(a);
        }

        System.out.println("================================");

        inventory.sort(comparing(Apple::getWeight).reversed());
        for (Apple a : inventory) {
            System.out.println(a);
        }

        System.out.println("================================");

        inventory.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
        for (Apple a : inventory) {
            System.out.println(a);
        }

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);
        int result1 = h.apply(10);
        System.out.println("result1 : " + result1);

        Function<Integer, Integer> z = f.compose(g);
        int result2 = z.apply(10);
        System.out.println("result2 : " + result2);
    }


    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(f.apply(t));
        }

        return result;
    }

    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for (T t: list) {
            c.accept(t);
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for (T t : list) {
            if (p.test(t)) {
                results.add(t);
            }
        }

        return results;
    }

    public static void process(Runnable r) {
        r.run();
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\09.Work\\Project\\IdeaProjects\\JavaStudy\\src\\main\\java\\modern\\java\\in\\action\\data.txt"))) {
            return p.process(br);
        }
    }
}
