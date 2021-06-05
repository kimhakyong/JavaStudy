package refactoring.java.r03_ia;

import java.util.Random;

public class Main {
    private static final Random random = new Random(1234);

    private static void execute(int length) {
        int[] data = new int[length];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(data.length);
        }

        SortSample sorter = new SortSample(data);
        System.out.println("Before : " + sorter);

        sorter.sort();
        System.out.println("After : " + sorter);

        System.out.println();
    }

    public static void main(String[] args) {
        execute(10);
        execute(10);
        execute(10);
        execute(10);
        execute(10);
    }
}
