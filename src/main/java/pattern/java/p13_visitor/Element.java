package pattern.java.p13_visitor;

public interface Element {
    void accept(Visitor visitor);
}
