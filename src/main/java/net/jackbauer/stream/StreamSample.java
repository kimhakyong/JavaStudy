package net.jackbauer.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.OptionalDouble;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
 * ex) orders.stream().map(n->n.price).sum()
 * 스트림 생성 . 중개 연산(스트림 변환). 최종 연산(스트림 사용)
 */
public class StreamSample {
	public static void main(String[] args) {
		int sum1 = 0;
		for (int number = 1; number <= 100; number++) {
			sum1 += number;
		}
		System.out.println(sum1);
		
		int sum2 = IntStream.rangeClosed(1, 100).reduce(0, (left, right) -> left + right);
		System.out.println(sum2);
		
		int sum3 = IntStream.rangeClosed(1, 100).parallel().reduce(0, (left, right) -> left + right);
		System.out.println(sum3);
		
		int sum4 = IntStream.rangeClosed(1, 100).reduce(0, Integer::sum);
		System.out.println(sum4);
	
		System.out.println("Sum : " + IntStream.rangeClosed(1, 1000).sum());
		System.out.println("Average : " + IntStream.rangeClosed(1, 1000).average().getAsDouble());
		System.out.println("Min : " + IntStream.rangeClosed(1, 1000).min().getAsInt());
		System.out.println("Max : " + IntStream.rangeClosed(1, 1000).max().getAsInt());
		System.out.println("Count : " + IntStream.rangeClosed(1, 1000).count());
		
		IntSummaryStatistics summary = IntStream.rangeClosed(1, 1000).summaryStatistics();
		System.out.println("Sum : " + summary.getSum());
		System.out.println("Average : " + summary.getAverage());
		System.out.println("Min : " + summary.getMin());
		System.out.println("Max : " + summary.getMax());
		System.out.println("Count : " + summary.getCount());
		
		Iterator<Integer> intIterator = IntStream.rangeClosed(0, 100).iterator();
		int[] intArray = IntStream.rangeClosed(0, 100).toArray();
		
		Stream<int[]> fibonacci = Stream.iterate(new int[] {0, 1}, n -> new int[] {n[1], n[0] + n[1]});
		fibonacci.limit(10).map(n -> n[0]).forEach(System.out::println);
		
		try(Stream<String> lines = Files.lines(Paths.get("test.csv"))) {
			long count = lines.map(line -> line.split(",")).filter(values -> values[6].contains("FL")).count();
			
			System.out.println("File Lines : " + count);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
