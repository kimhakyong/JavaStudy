package refactoring.java.r10_recwe;

/**
 * recwe : Replace Error Code with Exception
 * 에러 코드를 예외를 치환
 */
public class Main {
    public static void main(String[] args) {
        Robot robot = new Robot("Andrew");
        System.out.println(robot.toString());

        robot.execute("forward right forward");
        System.out.println(robot.toString());

        robot.execute("left backward left forward");
        System.out.println(robot.toString());

        robot.execute("right forward forward farvard");
        System.out.println(robot.toString());
    }
}
