package net.jackbauer.lambda;

public class AsyncHelloWorld {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("Hello World!");
        }).start();
    }
}
