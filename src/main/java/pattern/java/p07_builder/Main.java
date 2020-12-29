package pattern.java.p07_builder;

/**
 * Builder Pattern
 * 전체를 구성하고 있는 각 부분을 만들고, 단계를 밟아 만들어 감
 *
 * Builder : 인스턴스 각 부분을 만들기 위한 메소드 구성
 * ConcreteBuilder : 인스턴스 각 부분 메소드의 구체적인 정의
 * Director : Builder의 인터페이스를 사용해서 메소드 호출
 * Client : 구체적인 ConcreteBuilder 결정 후 Director에서 요청
 */
public class Main {
    public static void main(String[] args) {
        TextBuilder textBuilder = new TextBuilder();
        Director director = new Director(textBuilder);

        director.construct();
        System.out.println(textBuilder.getResult());

        HtmlBuilder htmlBuilder = new HtmlBuilder();
        director = new Director(htmlBuilder);

        director.construct();
        System.out.println(htmlBuilder.getResult());
    }
}
