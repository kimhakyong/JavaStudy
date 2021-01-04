package pattern.java.p13_visitor;

import java.util.Iterator;

/**
 * Visitor Pattern
 * 데이터 구조와 처리를 분리
 * 데이터 구조 안을 돌아다니는 주체인 '방문자'를 나타내는 클래스를 준비해서
 * 그 클래스에게 처리를 위임. 새로운 처리를 추가하고 싶을 때에는 새로운 방문자
 * 생성. 데이터 구조는 문을 두드리고 있는 '방문자'를 받아들임
 *
 * Visitor : visit(xxx) xxx를 처리하기 위한 메소드
 * ConcreteVisitor : visit(xxx)의 메소드 구현
 * Element : Visitor의 방문할 곳 설정. accept 메소드로 Visitor 수용
 * ConcreteElement : Element의 실제 Visitor 호출하는 역할
 * ObjectStructure : Element이 역할의 집합을 취급하는 역할. Directory
 *
 * double dispatch(이중 분리)
 * ConcreteElement와 ConcreteVisitor 쌍에 의해 실제 처리 결정.
 * element.accept(visitor) <-> visitor.visit(element)
 *
 * The Open-Closed Principle - 확장에 대해서는 열고, 수정에 대해서는 닫는다.
 * - Robert C. Martin
 * 기존의 클래스를 수정하지 않고 확장할 수 있도록 하는 것
 *
 * ConcreteVisitor 추가는 간단
 * ConcreteElement 추가는 곤란
 * Directory.iterator() 처럼 방문자가 데이터 구조에서 필요한 정보 취득할 수
 * 있도록 제공해야 함
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Making root entries ...");

        Directory rootdir = new Directory("root");
        Directory bindir = new Directory("bin");
        Directory tmpdir = new Directory("tmp");
        Directory usrdir = new Directory("usr");

        rootdir.add(bindir);
        rootdir.add(tmpdir);
        rootdir.add(usrdir);

        bindir.add(new File("vi", 10000));
        bindir.add(new File("latex", 20000));

        rootdir.accept(new ListVisitor());

        System.out.println("");
        System.out.println("Making user entries ...");

        Directory Kim = new Directory("Kim");
        Directory Lee = new Directory("Lee");
        Directory Park = new Directory("Park");

        usrdir.add(Kim);
        usrdir.add(Lee);
        usrdir.add(Park);

        Kim.add(new File("diary.html", 100));
        Kim.add(new File("Composite.java", 200));
        Lee.add(new File("memo.tex", 300));
        Park.add(new File("game.doc", 400));
        Park.add(new File("junk.html", 500));

        rootdir.accept(new ListVisitor());

        System.out.println("==========");

        FileFindVisitor ffv = new FileFindVisitor(".html");
        rootdir.accept(ffv);

        System.out.println("HTML files are:");
        Iterator<File> it = ffv.getFoundFiles();
        while (it.hasNext()) {
            File file = it.next();
            System.out.println(file.toString());
        }
    }
}
