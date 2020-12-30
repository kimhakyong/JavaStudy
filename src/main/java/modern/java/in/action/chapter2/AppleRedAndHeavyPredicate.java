package modern.java.in.action.chapter2;

import modern.java.in.action.sample.Apple;

import static modern.java.in.action.sample.Color.RED;

public class AppleRedAndHeavyPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return RED.equals(apple.getColor()) && apple.getWeight() > 150;
    }
}
