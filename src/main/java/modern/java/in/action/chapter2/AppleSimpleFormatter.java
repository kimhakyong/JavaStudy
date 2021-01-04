package modern.java.in.action.chapter2;

import modern.java.in.action.sample.Apple;

public class AppleSimpleFormatter implements AppleFormatter {
    @Override
    public String accept(Apple a) {
        return "An apple of " + a.getWeight() + "g";
    }
}
