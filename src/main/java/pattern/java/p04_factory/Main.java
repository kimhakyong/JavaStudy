package pattern.java.p04_factory;

import pattern.java.p04_factory.framework.Factory;
import pattern.java.p04_factory.framework.Product;
import pattern.java.p04_factory.idcard.IDCardFactory;

/**
 * Factory Method Pattern
 * 인스턴스를 생성하는 공장을 Template Method 패턴으로 구성한 것
 * 인스턴스를 만드는 방법을 상위 클래스(framework.Factory)에서 결정
 * 구체적인 내용은 하위 클래스(idcard.IDCardFactory) 측에서 수행
 * 인스턴스 생을 위한 골결(framework)과 인스턴스 생성의 구체적인 행위
 * 클래스를 분리
 *
 * Product : 인스턴스가 가져야 할 인터페이스 결정
 * Creator : 구체적인 내용은 하위 ConcreteCreator에 위임하고, 표준 기준 마련
 * ConcreteProduct : 구체적인 제품
 * ConcreteCreator : 구체적인 제품을 만드는 세부 내용 결정
 */
public class Main {
    public static void main(String[] args) {
        Factory factory = new IDCardFactory();

        Product card1 = factory.create("김학용");
        Product card2 = factory.create("정소연");
        Product card3 = factory.create("김세은");
        Product card4 = factory.create("김민서");

        card1.use();
        card2.use();
        card3.use();
        card4.use();
    }
}
