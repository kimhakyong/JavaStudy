package modern.java.in.action.chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static modern.java.in.action.chapter3.Color.GREEN;
import static modern.java.in.action.chapter3.Color.RED;

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
        process(() -> { System.out.println("Hello World 4"); });

        System.out.println(processFile(BufferedReader::readLine));
        System.out.println(processFile((BufferedReader br) -> br.readLine() + br.readLine()));

        List<String> listOfString = Arrays.asList("ABC", "DEF", "", "EFG");
        List<String> resultList = filter(listOfString, (String s) -> !s.isEmpty());
        resultList.forEach(System.out::println);

        forEach(Arrays.asList(1, 2, 3, 4, 5), System.out::println);

        List<Integer> li = map(Arrays.asList("ABC", "DEF", "   ", "FG"), String::length);
        li.forEach(System.out::println);

        listOfString.sort(Comparator.comparingInt(String::length));
        listOfString.sort(Comparator.comparing(String::length));

        List<String> str = Arrays.asList("a", "b", "A", "B");
        str.sort(String::compareToIgnoreCase);
        str.forEach(System.out::println);

        List<Apple> inventory = Arrays.asList(
                new Apple(80, GREEN),
                new Apple(155, GREEN),
                new Apple(155, RED),
                new Apple(160, RED));

        inventory.sort(new AppleComparator());
        inventory.forEach(System.out::println);

        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return Integer.compare(o1.getWeight(), o2.getWeight());
            }
        });
        inventory.forEach(System.out::println);

        System.out.println("==========");

        inventory.sort(Comparator.comparingInt(Apple::getWeight));
        inventory.forEach(System.out::println);

        System.out.println("==========");

        inventory.sort(Comparator.comparingInt(Apple::getWeight).reversed().thenComparing(Apple::getColor));
        inventory.forEach(System.out::println);

        System.out.println("==========");

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);
        int result = h.apply(7);
        System.out.println(result);

        Function<Integer, Integer> j = f.compose(g);
        result = j.apply(7);
        System.out.println(result);

        System.out.println("==========");

        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformationPipeline = addHeader
                .andThen(Letter::checkSpelling)
                .andThen(Letter::addFooter);
        String curLetter = transformationPipeline.apply("labda kim hakyong");
        System.out.println(curLetter);
    }

    public static void process(Runnable r) {
        r.run();
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new FileReader("/Users/jackbauer/Projects/IdeaProjects/JavaStudy/src/main/java/modern/java/in/action/chapter3/data.txt"))) {
            return p.process(br);
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

    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for (T t : list) {
            c.accept(t);
        }
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(f.apply(t));
        }
        return result;
    }
}