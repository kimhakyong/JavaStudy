package refactoring.java.r07_rtcwc;

public class Item {
    private final ItemType itemType;
    private final String title;
    private final int price;

    public Item(ItemType itemType, String title, int price) {
        this.itemType = itemType;
        this.title = title;
        this.price = price;
    }

    public int getTypecode() {
        return itemType.getTypecode();
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "typecode=" + itemType.getTypecode() +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
