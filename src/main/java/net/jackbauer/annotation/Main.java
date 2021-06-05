package net.jackbauer.annotation;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        MyContextContatiner demo = new MyContextContatiner();
        NesoyObject object = demo.get(NesoyObject.class);
        object.annotationTest();
    }
}
