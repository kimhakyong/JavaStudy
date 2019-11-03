package net.jackbauer.lambda;

public class Lambda4 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("전통 방식");
            }
        }).start();

        Runnable runnable = () -> {
            for (int i = 0; i < 30; i++) {
                System.out.println(i);
            }
        };

        Thread thread1 = new Thread(runnable);
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
        });
        thread2.start();
    }
}
