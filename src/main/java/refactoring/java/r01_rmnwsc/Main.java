package refactoring.java.r01_rmnwsc;

public class Main {
    public static void main(String[] args) {
        Robot robot = new Robot("Andrew");

        // Before
//        robot.order(0);
//        robot.order(1);
//        robot.order(2);

        // After 1
//        robot.order(Robot.COMMAND_WALK);
//        robot.order(Robot.COMMAND_STOP);
//        robot.order(Robot.COMMAND_JUMP);

        // After 2
        robot.order(Robot.Command.WALK);
        robot.order(Robot.Command.STOP);
        robot.order(Robot.Command.JUMP);
    }
}
