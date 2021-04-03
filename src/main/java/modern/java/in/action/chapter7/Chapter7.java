package modern.java.in.action.chapter7;

import java.util.Spliterator;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Chapter7 {
//    final static String SENTENCE = "Nel meszzo del cammin di nostra vita " +
//            "mi ritrovai in una selva oscura" +
//            "ch la dritta via era smarrita ";

    final static String SENTENCE =
            "    I hope to live with a conscience clear " +
                    "    until my dying day " +
                    "    And yet like the windblown leaf " +
                    "    I have suffered " +
                    "    I must love all those close to death " +
                    "    with a heart that sings of the stars. " +
                    "    And take the path " +
                    "    I have been called to walk " +
                    "    Even tonight, the stars are being ruffled by the wind.";

    public static void main(String[] args) {
        System.out.println(sequentialSum(100));
        System.out.println(iterativeSum(100));
        System.out.println(parallelSum(100));

        System.out.println(Runtime.getRuntime().availableProcessors());

        System.out.println("SideEffect parallel sum done in: " +
                measurePerf(Chapter7::sideEffectParallelSum, 100_000_000L) + " msecs");

        System.out.println("ForkJoin sum done in: " +
                measurePerf(ForkJoinSumCalculator::forkJoinSum, 100_000_000L) + " msecs");

        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");
        System.out.println("Found " + countWords(SENTENCE) + " words");
        System.out.println("Found " + parallelCountWords(SENTENCE) + " words");
        System.out.println("Found " + spliteratorParallelCountWords(SENTENCE) + " words");
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1l, i -> i + 1)
                .limit(n)
                .reduce(0l, Long::sum);
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1l; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1l, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0l, Long::sum);
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public static long sideEffectSum(long n) {
        Accumulator accumlator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumlator::add);

        System.out.println("total: " + accumlator.total);

        return accumlator.total;
    }

    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);

        return accumulator.total;
    }


    public static <T, R> long measurePerf(Function<T, R> f, T input) {
        long fastest = Long.MAX_VALUE;

        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            R result = f.apply(input);
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest) {
                fastest = duration;
            }
        }

        return fastest;
    }

    public static int countWordsIteratively(String s) {
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

    public static int countWords(String sentence) {
        Stream<Character> stream = IntStream.range(0, sentence.length())
                .mapToObj(SENTENCE::charAt);
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate, WordCounter::combine);

        return wordCounter.getCounter();
    }

    public static int parallelCountWords(String sentence) {
        Stream<Character> stream = IntStream.range(0, sentence.length())
                .mapToObj(SENTENCE::charAt).parallel();
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate, WordCounter::combine);

        return wordCounter.getCounter();
    }

    public static int spliteratorParallelCountWords(String sentence) {
        Spliterator<Character> spliterator = new WordCounterSpliterator(sentence);

        Stream<Character> stream = StreamSupport.stream(spliterator, true);
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate, WordCounter::combine);

        return wordCounter.getCounter();
    }
}

class Accumulator {
    public long total = 0;
    public void add(long value) {
        total += value;
    }
}
