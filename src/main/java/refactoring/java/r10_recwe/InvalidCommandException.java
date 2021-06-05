package refactoring.java.r10_recwe;

public class InvalidCommandException extends Exception {
    public InvalidCommandException(String name) {
        super(name);
    }

    public InvalidCommandException() {
    }
}
