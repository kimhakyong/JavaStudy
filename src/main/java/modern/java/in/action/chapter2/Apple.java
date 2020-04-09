package modern.java.in.action.chapter2;


import static modern.java.in.action.chapter2.Color.GREEN;

enum Color {RED, GREEN}

public class Apple {
    private int weight = 0;
    private Color color = GREEN;

    public Apple(int weight, Color color) {
        this.weight = weight;
        this.color = color;
    }

    public Apple() {
    }

    public Apple(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static boolean isGreenApple(Apple apple) {
        return GREEN.equals(apple.getColor());
    }

    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

    @SuppressWarnings("boxing")
    @Override
    public String toString() {
        return String.format("Apple{color='%d', weight=%d}", color.ordinal(), weight);
    }
}