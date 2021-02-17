package modern.java.in.action.chapter3;

import modern.java.in.action.sample.Apple;

import java.util.Comparator;

public class AppleComparator implements Comparator<Apple> {
    @Override
    public int compare(Apple a1, Apple a2) {
        return Integer.valueOf(a1.getWeight()).compareTo(a2.getWeight());
    }
}
