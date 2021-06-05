package refactoring.java.r08_rtcws;

/**
 * rtcws : Replace Type Code with Subclass
 * 분류 코드를 하위 클래스로 치환
 */
public class Main {
    public static void main(String[] args) {
        // case 1
        Shape line = Shape.createShape(ShapeEnum.LINE, 0, 0, 100, 200);

        // case 2
        Shape rectangle = Shape.createShape(ShapeFactory.RectangleFactory.getInstance(), 10, 20, 30, 40);

        // case 3
        Shape oval = ShapeOval.createShapeOval(100, 200, 300, 400);

        Shape[] shape = {line, rectangle, oval};

        for (Shape s : shape) {
            s.draw();
        }
    }
}
