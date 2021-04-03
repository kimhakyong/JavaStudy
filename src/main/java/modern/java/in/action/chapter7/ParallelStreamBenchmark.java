package modern.java.in.action.chapter7;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs={"-Xms4G", "-Xmx4G"})
public class ParallelStreamBenchmark {
    private static final long N = 10_000_000L;

    @Benchmark
    public long parallelSum() {
        return Chapter7.parallelSum(N);
    }

    @Benchmark
    public long sequentialSum() {
        return Chapter7.sequentialSum(N);
    }

    @Benchmark
    public long iterativeSum() {
        return Chapter7.iterativeSum(N);
    }

    @Benchmark
    public long rangedSum() {
        return Chapter7.rangedSum(N);
    }

    @Benchmark
    public long parallelRangedSum() {
        return Chapter7.parallelRangedSum(N);
    }

    @Benchmark
    public long forkJoinSum() {
        return ForkJoinSumCalculator.forkJoinSum(N);
    }

    @TearDown(Level.Invocation)
    public void tearDown() {
        System.gc();
    }
}
