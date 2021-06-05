package refactoring.java.r08_rtcws;

public abstract class Shape {
    private final int startx;
    private final int starty;
    private final int endx;
    private final int endy;

    protected Shape(int startx, int starty, int endx, int endy) {
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
    }

    // case 1
    public static Shape createShape(ShapeEnum shapeEnum, int startx, int starty, int endx, int endy) {
        switch(shapeEnum) {
            case LINE:
                return ShapeLine.createShapeLine(startx, starty, endx, endy);
            case RECTANGLE:
                return ShapeRectangle.createShapeRectangle(startx, starty, endx, endy);
            case OVAL:
                return ShapeOval.createShapeOval(startx, starty, endx, endy);
            default:
                throw new IllegalArgumentException();
        }
    }

    // case 2
    public static Shape createShape(ShapeFactory factory, int startx, int starty, int endx, int endy) {
        return factory.create(startx, starty, endx, endy);
    }

    public abstract ShapeEnum getTypecode();

    public abstract String getName();

    public abstract void draw();

    @Override
    public String toString() {
        return "Shape{" +
                "name=" + getName() +
                ", startx=" + startx +
                ", starty=" + starty +
                ", endx=" + endx +
                ", endy=" + endy +
                '}';
    }
}
