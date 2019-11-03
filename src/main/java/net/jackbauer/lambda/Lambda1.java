package net.jackbauer.lambda;

@FunctionalInterface
interface Say {
    int something(int a, int b);
}

public class Lambda1 {
    public void hi(Say line) {
        int number = line.something(3, 4);
        System.out.println("Number is " + number);
    }

    public static void main(String[] args) {
        Lambda1 lambda = new Lambda1();

        lambda.hi((a, b) -> {
            System.out.println("My name is AAA");
            System.out.println("Nice");
            System.out.println(a + " " + b);

            return 7;
        });
    }
}
