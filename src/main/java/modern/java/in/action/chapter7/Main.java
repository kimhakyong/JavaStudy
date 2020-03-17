package modern.java.in.action.chapter7;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    final static String SENTENCE = "Nel meszzo del cammin di nostra vita " +
            "mi ritrovai in una selva oscura" +
            "ch la dritta via era smarrita ";

    public static void main(String[] args) {
        Main main = new Main();

        System.out.println(main.sequentialSum(100));
        System.out.println(main.iterativeSum(100));
        System.out.println(main.parallelSum(100));

        System.out.println("ForkJoin sum done in: "
                + ParallelStreamHarness.measurePerf(ForkJoinSumCalculator::forkJoinSum, 10_000_000L) + " msecs");

        System.out.println("Found " + main.countWordsIteratively(SENTENCE) + " words");

        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
                .mapToObj(SENTENCE::charAt);
        System.out.println("Found " + main.countWords(stream) + " words");

        stream = IntStream.range(0, SENTENCE.length())
                .mapToObj(SENTENCE::charAt);
        System.out.println("Found " + main.countWords(stream.parallel()) + " words");
    }

    public long sequentialSum(long n) {
        return Stream.iterate(1l, i -> i + 1)
                .limit(n)
                .reduce(0l, Long::sum);
    }

    public long iterativeSum(long n) {
        long result = 0;
        for (long i = 1l; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public long parallelSum(long n) {
        return Stream.iterate(1l, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0l, Long::sum);
    }

    public int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;

        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) counter++;
                lastSpace = false;
            }
        }

        return counter;
    }

    private int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumlate, WordCounter::combine);
        return wordCounter.getCounter();
    }
}
