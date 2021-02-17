package modern.java.in.action.chapter2;

import modern.java.in.action.sample.Apple;

import static modern.java.in.action.sample.Color.GREEN;

public class AppleGreenColorPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return GREEN.equals(apple.getColor());
    }
}
