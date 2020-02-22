//package modern.java.in.action;
//
//import java.io.File;
//import java.io.FileFilter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class Chapter1 {
//	public static void main(String[] args) {
//		ancientIsHiddenFiles();
//		modernIsHiddenFiles();
//
//		List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));
//		List<Apple> apples;
//
//		apples = filterGreenApples(inventory);
//		for (Apple apple : apples) {
//			System.out.println("green : " + apple);
//		}
//
//		apples = filterHeavyApples(inventory);
//		for (Apple apple : apples) {
//			System.out.println("heavy : " + apple);
//		}
//
//		apples = filterApples(inventory, Chapter1::isGreenApple);
//		for (Apple apple : apples) {
//			System.out.println("function green : " + apple);
//		}
//
//		apples = filterApples(inventory, Chapter1::isHeavyApple);
//		for (Apple apple : apples) {
//			System.out.println("function heavy : " + apple);
//		}
//
//		apples = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
//		for (Apple apple : apples) {
//			System.out.println("lambda green : " + apple);
//		}
//
//		apples = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
//		for (Apple apple : apples) {
//			System.out.println("lambda heavy : " + apple);
//		}
//
//		apples = filterApples(inventory, (Apple a) -> a.getWeight() <= 80 || "red".equals(a.getColor()));
//		for (Apple apple : apples) {
//			System.out.println("lambda light or red : " + apple);
//		}
//
//		apples = inventory.stream().filter((Apple a) -> a.getWeight() > 150).collect(Collectors.toList());
//		for (Apple apple : apples) {
//			System.out.println("stream heavy : " + apple);
//		}
//
//		apples = inventory.parallelStream().filter((Apple a) -> a.getWeight() > 150).collect(Collectors.toList());
//		for (Apple apple : apples) {
//			System.out.println("parallel stream heavy : " + apple);
//		}
//
//		// 1.5
//	}
//
//	public static void ancientIsHiddenFiles() {
//		File[] hiddenFiles = new File(".").listFiles(new FileFilter() {
//			@Override
//			public boolean accept(File pathname) {
//				return pathname.isHidden();
//			}
//		});
//
//		for (File file : hiddenFiles) {
//			System.out.println(file);
//		}
//	}
//
//	public static void modernIsHiddenFiles() {
//		File[] hiddenFiles = new File(".").listFiles(File::isHidden);
//
//		for (File file : hiddenFiles) {
//			System.out.println(file);
//		}
//	}
//
//	public static List<Apple> filterGreenApples(List<Apple> inventory) {
//		List<Apple> result = new ArrayList<>();
//
//		for (Apple apple : inventory) {
//			if ("green".equals(apple.getColor())) {
//				result.add(apple);
//			}
//		}
//
//		return result;
//	}
//
//	public static List<Apple> filterHeavyApples(List<Apple> inventory) {
//		List<Apple> result = new ArrayList<>();
//
//		for (Apple apple : inventory) {
//			if (apple.getWeight() > 150) {
//				result.add(apple);
//			}
//		}
//
//		return result;
//	}
//
//	public static boolean isGreenApple(Apple apple) {
//		return "green".equals(apple.getColor());
//	}
//
//	public static boolean isHeavyApple(Apple apple) {
//		return apple.getWeight() > 150;
//	}
//
//	public interface Predicate<T> {
//		boolean test(T t);
//	}
//
//	public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
//		List<Apple> result = new ArrayList<>();
//
//		for (Apple apple : inventory) {
//			if (p.test(apple)) {
//				result.add(apple);
//			}
//		}
//
//		return result;
//	}
//}
