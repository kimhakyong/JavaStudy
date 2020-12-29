package pattern.java.p07_builder;

public class TextBuilder extends Builder {
    private StringBuffer buffer = new StringBuffer();

    @Override
    public void makeTitle(String title) {
        buffer.append("===============================");
        buffer.append("***(Text) ").append(title).append(" ***\n");
        buffer.append("\n");
    }

    @Override
    public void makeString(String str) {
        buffer.append("--- ").append(str).append("\n");
    }

    @Override
    public void makeItems(String[] items) {
        for (String item : items) {
            buffer.append("   . ").append(item).append("\n");
        }
    }

    @Override
    public void close() {
        buffer.append("===============================");
    }

    public String getResult() {
        return buffer.toString();
    }
}
