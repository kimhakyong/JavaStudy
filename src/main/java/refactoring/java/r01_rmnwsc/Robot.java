package refactoring.java.r01_rmnwsc;

/**
 * rmnwsc : Replace Magic Number with Symbolic Constant
 * 매직 넘버를 기호 상수로 치환
 */
public class Robot {
    // after 1
//    public static final int COMMAND_WALK = 0;
//    public static final int COMMAND_STOP = 1;
//    public static final int COMMAND_JUMP = 2;

    // after 2
    public enum Command {
        WALK, STOP, JUMP
    }

    private final String _name;

    public Robot(String name) {
        _name = name;
    }

    public void order(Robot.Command command) {
        // Before
//        if (command == 0) {
//            System.out.println(_name + " walks.");
//        } else if (command == 1) {
//            System.out.println(_name + " stops.");
//        } else if (command == 2) {
//            System.out.println(_name + " jumps.");
//        } else {
//            System.out.println("Command error. command = " + command);
//        }

        // After 1
//        if (command == COMMAND_WALK) {
//            System.out.println(_name + " walks.");
//        } else if (command == COMMAND_STOP) {
//            System.out.println(_name + " stops.");
//        } else if (command == COMMAND_JUMP) {
//            System.out.println(_name + " jumps.");
//        } else {
//            System.out.println("Command error. command = " + command);
//        }

        // After 2
        if (command == Command.WALK) {
            System.out.println(_name + " walks.");
        } else if (command == Command.STOP) {
            System.out.println(_name + " stops.");
        } else if (command == Command.JUMP) {
            System.out.println(_name + " jumps.");
        } else {
            System.out.println("Command error. command = " + command);
        }
    }
}
