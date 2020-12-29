package modern.java.in.action.chapter1;

import net.jackbauer.study.App;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static modern.java.in.action.chapter1.Color.GREEN;
import static modern.java.in.action.chapter1.Color.RED;

/*
    - 스트림 API
    - 동작 파라미터화(behavior parameterization)로 메서드에 코드를 전달하는 기법
    - 병렬성과 공유 가변 데이터(shared mutalbe data)
    - 인터페이스의 디폴트 메서드
 */

public class Main {

    public static void main(String[] args) {
        fileFilter();

        List<Apple> inventory = Arrays.asList(
                new Apple(80, GREEN),
                new Apple(155, GREEN),
                new Apple(120, RED));

        appleFilter(inventory);

//        inventory.stream()
//                .filter((Apple a) -> GREEN.equals(a.getColor()))
//                .collect(toList())
//                .forEach(System.out::println);
    }

    public static void appleFilter(List<Apple> inventory) {
        // Filter 1단계
        System.out.println("Filter 1단계");

        List<Apple> resultList = filterGreenApples(inventory);
        resultList.forEach(System.out::println);

        resultList = filterHeavyApples(inventory);
        resultList.forEach(System.out::println);

        // Filter 2단계 : predicate
        System.out.println("Filter 2단계");

        resultList = filterApples(inventory, Apple::isGreenApple);
        resultList.forEach(System.out::println);

        resultList = filterApples(inventory, Apple::isHeavyApple);
        resultList.forEach(System.out::println);

        // Filter 3단계 : lambda
        System.out.println("Filter 3단계");

        resultList = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
        resultList.forEach(System.out::println);

        resultList = filterApples(inventory, (Apple a) -> GREEN.equals(a.getColor()));
        resultList.forEach(System.out::println);
    }

    public static void fileFilter() {
        // Java 8 이전
        File[] hiddenFiles = new File(".").listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isHidden();
            }
        });

        Arrays.asList(hiddenFiles).forEach(System.out::println);

        // Java 8 이후
        hiddenFiles = new File(".").listFiles(File::isHidden);

        Arrays.asList(hiddenFiles).forEach(System.out::println);
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

    public static List<Apple> filterHeavyApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }

        return result;
    }

    public static boolean isGreenApple(Apple apple) {
        return GREEN.equals(apple.getColor());
    }

    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

    public interface Predicate<T> {
        boolean test(T t);
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }
}

