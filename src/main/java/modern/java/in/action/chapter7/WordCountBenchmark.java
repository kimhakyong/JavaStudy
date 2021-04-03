package modern.java.in.action.chapter7;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
public class WordCountBenchmark {
    private static final String KOR_SENTENCE =
            "    죽는 날까지 하늘을 우러러 " +
                    "    한 점 부끄럼이 없기를, " +
                    "    잎새에 이는 바람에도 " +
                    "    나는 괴로워했다. " +
                    "    별을 노래하는 마음으로 " +
                    "    모든 죽어 가는 것을 사랑해야지 " +
                    "    그리고 나한테 주어진 길을 " +
                    "    걸어가야겠다. " +
                    "    오늘 밤에도 별이 바람에 스치운다. ";

    public static String SENTENCE =
            "    I hope to live with a conscience clear " +
//                    "    until my dying day " +
//                    "    And yet like the windblown leaf " +
//                    "    I have suffered " +
//                    "    I must love all those close to death " +
//                    "    with a heart that sings of the stars. " +
//                    "    And take the path " +
//                    "    I have been called to walk " +
                      "    Even tonight, the stars are being ruffled by the wind.";

    @Benchmark
    public long countWordsIteratively() {
        return Chapter7.countWordsIteratively(SENTENCE);
    }

    @Benchmark
    public long countWords() {
        return Chapter7.countWords(SENTENCE);
    }

    @Benchmark
    public long parallelCountWords() {
        return Chapter7.parallelCountWords(SENTENCE);
    }

    @Benchmark
    public long spliteratorParallelCountWords() {
        return Chapter7.spliteratorParallelCountWords(SENTENCE);
    }

    @TearDown(Level.Invocation)
    public void tearDown() {
        System.gc();
    }
}
