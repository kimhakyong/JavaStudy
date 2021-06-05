package refactoring.java.r08_rtcws;

public class ShapeOval extends Shape {
    // case 3
    public static ShapeOval createShapeOval(int startx, int starty, int endx, int endy) {
        return new ShapeOval(startx, starty, endx, endy);
    }

    public ShapeOval(int startx, int starty, int endx, int endy) {
        super(startx, starty, endx, endy);
    }

    @Override
    public ShapeEnum getTypecode() {
        return ShapeEnum.OVAL;
    }

    @Override
    public String getName() {
        return ShapeEnum.OVAL.getName();
    }

    @Override
    public void draw() {
        drawOval();
    }

    private void drawOval() {
        System.out.println("drawOval: " + this.toString());
    }
}
