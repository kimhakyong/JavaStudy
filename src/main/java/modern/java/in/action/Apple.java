package modern.java.in.action;

enum Color {RED, GREEN}

public class Apple {
	private int weight = 0;
	private Color color = Color.GREEN;

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

	@SuppressWarnings("boxing")
	@Override
	public String toString() {
		return String.format("Apple{color='%d', weight=%d}", color.ordinal(), weight);
	}
}