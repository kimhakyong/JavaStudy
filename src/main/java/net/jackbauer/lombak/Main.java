package net.jackbauer.lombak;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("길동", "홍",
                new Date(System.currentTimeMillis()));
        System.out.println(person.toString());
    }
}
