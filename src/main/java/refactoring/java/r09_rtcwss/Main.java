package refactoring.java.r09_rtcwss;

/**
 * rtcwss : Replace Type Code with State/Strategy
 * 분류 코드를 상태/전략 패턴으로 치환
 */
public class Main {
    public static void main(String[] args) {
        Logger logger = new Logger();
        logger.log("information #1");

        logger.start();
        logger.log("information #2");

        logger.start();
        logger.log("information #3");

        logger.stop();
        logger.log("information #4");

        logger.stop();
        logger.log("information #5");
    }
}
