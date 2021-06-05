package refactoring.java.r07_rtcwc;

public enum ItemEnum {
    BOOK(0), DVD(1), SOFTWARE(2);

    private final int typecode;

    private ItemEnum(int typecode) {
        this.typecode = typecode;
    }
}
