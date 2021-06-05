package refactoring.java.r07_rtcwc;

/**
 * rtcwc : Replace Type Code with Class
 * 분류 코드를 클래스로 치환
 */
public class Main {
    public static void main(String[] args) {
        Item book = new Item(ItemType.BOOK, "세계 역사", 4800);
        Item dvd = new Item(ItemType.DVD, "뉴욕의 꿈 특별판", 3000);
        Item soft = new Item(ItemType.SOFTWARE, "튜링 머신 에뮬레이터", 3200);

        System.out.println("book = " + book);
        System.out.println("dvd = " + dvd);
        System.out.println("soft = " + soft);

        System.out.println(ItemType.BOOK == ItemType.BOOK);
        System.out.println(ItemType.BOOK == ItemType.DVD);

        System.out.println(ItemEnum.BOOK == ItemEnum.BOOK);
        System.out.println(ItemEnum.BOOK.equals(ItemEnum.BOOK));

        System.out.println(EnumType.Drink.COFFEE);
    }
}
