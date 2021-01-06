package modern.java.in.action.chapter3;

import modern.java.in.action.sample.Apple;
import modern.java.in.action.sample.Color;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

import static modern.java.in.action.sample.Color.GREEN;
import static modern.java.in.action.sample.Color.RED;

/*
    람다는 함수형 인터페이스다.
    함수형 인터페이스는 하나의 추상 메서드를 지정하는 인터페이스다.
    (디폴트 메서드를 포함할 수 있다.)
 */

public class Chapter3 {
    public static void main(String[] args) throws IOException {
        // lambda 다양한 표현
        useLambda();

        // 실행 어라운드 패터 (Execute around pattern)
        // 설정 -> 실행 -> 정리
        executeAroundPattern();

        usePredicate();
        useConsumer();
        useFunction();

        // 기본형에 대한 함수형 인터페이스
        usePrimitiveFunction();

        useMethodReference();

        useConstructorReference();

        // lambda summary
        useAllLambda();

        useUsefulMethod();
    }

    private static void useUsefulMethod() {
        System.out.println("*** " + new Object() {}.getClass().getEnclosingMethod().getName());

        List<Apple> inventory = Arrays.asList(
                new Apple(80, GREEN),
                new Apple(155, GREEN),
                new Apple(155, RED),
                new Apple(160, RED));

        // Comparator 활용
        System.out.println("** Comparator");

        Comparator<Apple> c = Comparator.comparing(Apple::getWeight);
        inventory.sort(c);
        inventory.forEach(System.out::println);

        inventory.sort(c.reversed());
        inventory.forEach(System.out::println);

        inventory.sort(c.reversed().thenComparing(Apple::getColor));
        inventory.forEach(System.out::println);

        // Predicate 조합
        // negate, and, or
        System.out.println("** Predicate");

        System.out.println("** negate");
        Predicate<Apple> redApplesPre = apple -> apple.getColor() == RED;
        List<Apple> notRedApples = filter(inventory, redApplesPre.negate());
        notRedApples.forEach(System.out::println);

        System.out.println("** and");
        Predicate<Apple> redAndHeavyPre
                = redApplesPre.and(apple->apple.getWeight() > 150).or(apple -> GREEN.equals(apple.getColor()));
        List<Apple> redAndHeavyApples = filter(inventory, redAndHeavyPre);
        redAndHeavyApples.forEach(System.out::println);

        // Function 조합
        // andThen, compose
        System.out.println("** function");
        System.out.println("** andThen, compose");

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);
        int result = h.apply(10);
        System.out.println("result : " + result);

        f = x -> x + 1;
        g = x -> x * 2;
        h = f.compose(g);
        result = h.apply(10);
        System.out.println("result : " + result);

        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformationPipeline1 =
                addHeader.andThen(Letter::addBody)
                        .andThen(Letter::checkSpelling)
                        .andThen(Letter::addFooter);
        Function<String, String> transformationPipeline2 =
                addHeader.andThen(Letter::addBody)
                        .andThen(Letter::addFooter);

        System.out.println(transformationPipeline1.apply("letter1"));
        System.out.println(transformationPipeline2.apply("letter2"));
    }

    private static void useAllLambda() {
        System.out.println("*** method useAllLambda");

        List<Apple> inventory = Arrays.asList(
                new Apple(80, GREEN),
                new Apple(155, GREEN),
                new Apple(155, RED),
                new Apple(160, RED));

        System.out.println("** step 1");

        inventory.sort(new AppleComparator());
        inventory.forEach(System.out::println);

        System.out.println("** step 2 : anonymous class");
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return Integer.valueOf(o1.getWeight()).compareTo(o2.getWeight());
            }
        });
        inventory.forEach(System.out::println);

        System.out.println("** step 3 : lambda");
        inventory.sort((Apple a1, Apple a2) -> Integer.valueOf(a1.getWeight()).compareTo(a2.getWeight()));
        inventory.forEach(System.out::println);

        System.out.println("** step 4 : 형식 추론");
        inventory.sort((a1, a2) -> Integer.valueOf(a1.getWeight()).compareTo(a2.getWeight()));
        inventory.forEach(System.out::println);

        System.out.println("** step 5 : static method");
        inventory.sort(Comparator.comparing(apple -> apple.getWeight()));
        inventory.forEach(System.out::println);

        System.out.println("** step 6 : method reference");
        inventory.sort(Comparator.comparing(Apple::getWeight));
        inventory.forEach(System.out::println);
    }

    private static void useConstructorReference() {
        // lambda
        Supplier<Apple> s1 = () -> new Apple();
        Apple a1 = s1.get();

        // Constructor Reference
        Supplier<Apple> s2 = Apple::new;
        Apple a2 = s2.get();

        // lambda
        Function<Integer, Apple> f1 = (weight) -> new Apple(weight);
        Apple a3 = f1.apply(100);

        // Constructor Reference
        Function<Integer, Apple> f2 = Apple::new;
        Apple a4 = f2.apply(200);

        List<Integer> weight = Arrays.asList(7, 3, 4, 10);
        List<Apple> apples = map(weight, Apple::new);

        forEach(apples, System.out::println);

        BiFunction<Integer, Color, Apple> b1 = Apple::new;
        Apple a5 = b1.apply(100, GREEN);

    }

    /**
     * Method Reference
     *
     * [Case1]
     * (args) -> ClassName.staticMethod(args)
     * ClassName::staticMethod
     *
     * [Case2]
     * (arg0, rest) -> arg0.instanceMethod(res)
     * ClassName::instanceMethod
     * ClassName은 arg0의 class
     *
     * [Case3]
     * (args) -> expr.instanceMethod(args)
     * expr::instanceMethod
     */
    private static void useMethodReference() {
        List<String> str = Arrays.asList("a", "b", "A", "B");

        str.sort((String s1, String s2) -> s1.compareToIgnoreCase(s2));
        str.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        str.sort(String::compareToIgnoreCase);

        str.forEach(System.out::println);
    }

    private static void usePrimitiveFunction() {
        // 박싱(boxing) : 기본형을 참조형으로 변환하는 기능
        // 언박싱(unboxing) : 참조형을 기본형으로 변환하는 기능

        // 오토박싱(autoboxing)
        // 비용 소모 : 힙 저장으로 메모리 낭비, 조회 시 메모리 탐색 비용
        List<Integer> list = new ArrayList<>();
        for (int i = 300; i < 400; i++) {
            list.add(i);
        }

        IntPredicate evenNumbers = (int i) -> i % 2 == 0;
        System.out.println(evenNumbers.test(1000));
    }

    public static void useFunction() {
        // lambda
        List<Integer> li = map(Arrays.asList("ABC", "DEF", "   ", "FG"), (String s) -> s.length());

        // method reference
        li = map(Arrays.asList("ABC", "DEF", "   ", "FG"), String::length);

        li.forEach(System.out::println);
    }

    private static void useConsumer() {
        // lambda
        forEach(Arrays.asList(1, 2, 3, 4, 5), (Integer i) -> System.out.println(i));

        // method reference
        forEach(Arrays.asList(1, 2, 3, 4, 5), System.out::println);
    }

    private static void usePredicate() {
        List<String> listOfString = Arrays.asList("ABC", "DEF", "", "EFG");
        List<String> resultList = filter(listOfString, (String s) -> !s.isEmpty());
        resultList.forEach(System.out::println);
    }

    public static void executeAroundPattern() throws IOException {
        System.out.println(processFile(BufferedReader::readLine));
        System.out.println(processFile((BufferedReader br) -> br.readLine() + br.readLine()));
    }

    public static void useLambda() {
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
        process(() -> {
            System.out.println("Hello World 4");
        });
    }

    public static void process(Runnable r) {
        r.run();
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(
                "C:\\Projects\\IdeaProjects\\JavaStudy\\src\\main\\java\\modern\\java\\in\\action\\chapter3\\data.txt"
        ))) {
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