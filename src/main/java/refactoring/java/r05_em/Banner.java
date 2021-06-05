package refactoring.java.r05_em;

/**
 * em : Extreact Method
 * 메서드 추출
 */
public class Banner {
    private final String _content;

    public Banner(String content) {
        _content = content;
    }

    // before
//    public void print(int times) {
//        // 테두리 출력
//        System.out.print("+");
//        for (int i = 0; i < _content.length(); i++) {
//            System.out.print("-");
//        }
//        System.out.println("+");
//
//        // 내용 출력
//        for (int i = 0; i < times; i++) {
//            System.out.println("|" + _content + "|");
//        }
//
//        // 테두리 출력
//        System.out.print("+");
//        for (int i = 0; i < _content.length(); i++) {
//            System.out.print("-");
//        }
//        System.out.println("+");
//    }

    // after
    public void print(int times) {
        printBorder();
        printContent(times);
        printBorder();
    }

    private void printContent(int times) {
        for (int i = 0; i < times; i++) {
            System.out.println("|" + _content + "|");
        }
    }

    private void printBorder() {
        System.out.print("+");
        for (int i = 0; i < _content.length(); i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}
