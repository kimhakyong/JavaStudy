package refactoring.java.r07_rtcwc;

public class ItemType {
    public static final ItemType BOOK = new ItemType(0);
    public static final ItemType DVD = new ItemType(1);
    public static final ItemType SOFTWARE = new ItemType(2);

    private final int typecode;

    private ItemType(int typecode) {
        this.typecode = typecode;
    }

    public int getTypecode() {
        return this.typecode;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemType))
            return false;

        return ((ItemType)obj).getTypecode() == this.typecode;
    }
}