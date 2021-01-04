package pattern.java.p11_composite;

/**
 * Composite Pattern
 * 그릇과 내용물을 동일시해서 재귀적인 구조를 만들기 위한 디자인 패턴
 *
 * Leaf : File, 내용물
 * Composite : Directory, 그롯
 * Component : Leaf, Composite를 동일 시 하는 역할. Entry
 * Client : Composite 사용자
 *
 * 일반적으로 트리 구조로 된 데이터 구조는 Composite 패턴에 해당
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

        rootdir.printList();

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
        Park.add(new File("junk.mail", 500));

        rootdir.printList();
    }
}
