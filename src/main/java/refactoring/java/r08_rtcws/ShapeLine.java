package refactoring.java.r08_rtcws;

public class ShapeLine extends Shape {
    public static ShapeLine createShapeLine(int startx, int starty, int endx, int endy) {
        return new ShapeLine(startx, starty, endx, endy);
    }

    public ShapeLine(int startx, int starty, int endx, int endy) {
        super(startx, starty, endx, endy);
    }

    @Override
    public ShapeEnum getTypecode() {
        return ShapeEnum.LINE;
    }

    @Override
    public String getName() {
        return ShapeEnum.LINE.getName();
    }

    @Override
    public void draw() {
        drawLine();
    }

    private void drawLine() {
        System.out.println("drawLine: " + this.toString());
    }
}
