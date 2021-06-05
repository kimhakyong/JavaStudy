package refactoring.java.r08_rtcws;

public class ShapeRectangle extends Shape {
    public static ShapeRectangle createShapeRectangle(int startx, int starty, int endx, int endy) {
        return new ShapeRectangle(startx, starty, endx, endy);
    }

    public ShapeRectangle(int startx, int starty, int endx, int endy) {
        super(startx, starty, endx, endy);
    }

    @Override
    public ShapeEnum getTypecode() {
        return ShapeEnum.RECTANGLE;
    }

    @Override
    public String getName() {
        return ShapeEnum.RECTANGLE.getName();
    }

    @Override
    public void draw() {
        drawRectangle();
    }

    private void drawRectangle() {
        System.out.println("drawRectangle: " + this.toString());
    }
}
