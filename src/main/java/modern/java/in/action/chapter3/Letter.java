package modern.java.in.action.chapter3;

public class Letter {
    public static String addHeader(String text) {
        return "From Raoul, Mario and Alan : " + text;
    }

    public static String addBody(String text) {
        return text + "\n\ni like labda.\n\n";
    }

    public static String addFooter(String text) {
        return text + "Kind regards";
    }

    public static String checkSpelling(String text) {
        return text.replaceAll("labda", "lambda");
    }
}
