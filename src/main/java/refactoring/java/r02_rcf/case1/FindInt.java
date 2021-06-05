package refactoring.java.r02_rcf.case1;

/**
 * rcf : Remove Control Flag
 * 제어 플래그를 삭제하고, break, continue, return 사용
 */
public class FindInt {
    public static boolean find(int[] data, int target) {
        // before
//        boolean flag = false;
//        for (int i = 0; i < data.length && !flag; i++) {
//            if (data[i] == target) {
//                flag = true;
//            }
//        }

        // after 1
//        boolean found = false;
//        for (int i = 0; i < data.length; i++) {
//            if (data[i] == target) {
//                found = true;
//                break;
//            }
//        }

        // after 2
        for (int i = 0; i < data.length; i++) {
            if (data[i] == target) {
                return false;
            }
        }

        return true;
    }
}
