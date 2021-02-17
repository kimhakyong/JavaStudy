package modern.java.in.action.chapter2;

import modern.java.in.action.sample.Apple;
import modern.java.in.action.sample.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

import static modern.java.in.action.sample.Color.GREEN;
import static modern.java.in.action.sample.Color.RED;

public class Chapter2 {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, GREEN),
                new Apple(155, GREEN),
                new Apple(160, RED));

        // 단계별 필터 적용
        stepByStepFilter(inventory);

        // 단계별 정렬 적용
        stepByStepSort(inventory);

        // 단계별 Thread 실행
        stepByStepThread();

        // 단계별 Future 실행
        stepByStepFuture();
    }

    private static void stepByStepFuture() {
        // 1단계 : 익명 클래스 적용
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> threadName = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Thread.currentThread().getName();
            }
        });
        System.out.println(threadName);

        // 2단계 : Lambda 적용
        threadName = executorService.submit(() -> Thread.currentThread().getName());
        System.out.println(threadName);
    }

    private static void stepByStepThread() {
        // 1단계 : 익명 클래스 적용
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread Start1");
            }
        }).start();

        // 2단계 : Lambda 적용
        new Thread(() -> System.out.println("Thread Start2")).start();

    }

    public static void stepByStepSort(List<Apple> inventory) {
        // 1단계 : 익명 클래스 사용
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return Integer.compare(o1.getWeight(), o2.getWeight());
            }
        });
        inventory.forEach(System.out::println);

        // 2단계 : Lambda
        inventory.sort((Apple a1, Apple a2) -> Integer.compare(a1.getWeight(), a2.getWeight()));

        // 3단계 : Comparator
        inventory.sort(Comparator.comparingInt(Apple::getWeight));
        inventory.forEach(System.out::println);
    }

    public static void stepByStepFilter(List<Apple> inventory) {
        // 1단계
        List<Apple> apples = filterGreenApples(inventory);
        apples.forEach(System.out::println);

        System.out.println("==========");

        // 2단계
        apples = filterApplesByColor(inventory, RED);
        apples.forEach(System.out::println);

        System.out.println("==========");

        // 3단계 : strategy design pattern
        apples = filterApples(inventory, new AppleHeavyWeightPredicate());
        apples.forEach(System.out::println);

        System.out.println("==========");

        apples = filterApples(inventory, new AppleGreenColorPredicate());
        apples.forEach(System.out::println);

        System.out.println("==========");

        apples = filterApples(inventory, new AppleRedAndHeavyPredicate());
        apples.forEach(System.out::println);

        System.out.println("==========");

        // Quiz 2-1
        prettyPrintApple(inventory, new AppleFancyFormatter());
        prettyPrintApple(inventory, new AppleSimpleFormatter());

        System.out.println("==========");

        // 4단계 : 익명 클래스
        apples = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return RED.equals(apple.getColor());
            }
        });
        apples.forEach(System.out::println);

        System.out.println("==========");

        // 5단계 : Lambda
        apples = filterApples(inventory, (Apple apple) -> RED.equals(apple.getColor()));
        apples.forEach(System.out::println);

        // 6단계 : List 형식으로 추상화
        apples = filter(inventory, (Apple apple) -> RED.equals(apple.getColor()));
        apples.forEach(System.out::println);

        List<Integer> nums = filter(Arrays.asList(1, 2, 3, 4, 5), (Integer num) -> num > 3);
        nums.forEach(System.out::println);

        nums = filter(Arrays.asList(1, 2, 3, 4, 5), (Integer num) -> num % 2 == 0);
        nums.forEach(System.out::println);
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }

        return result;
    }

    public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }

        return result;
    }

    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }

        return result;
    }

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }

    public static void prettyPrintApple(List<Apple> inventory, AppleFormatter f) {
        for (Apple apple : inventory) {
            String output = f.accept(apple);
            System.out.println(output);
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<T>();
        for (T e : list) {
            if (p.test(e)) {
                result.add(e);
            }
        }

        return result;
    }
}
