package net.jackbauer.annotation;

public class NesoyObject {
    @NesoyAnnotation(value = "I'm Annotation")
    public void annotationTest() {
        System.out.println("Hello! Nesoy");
    }
}
