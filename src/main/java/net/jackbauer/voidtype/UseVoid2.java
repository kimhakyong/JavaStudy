package net.jackbauer.voidtype;

// 제네릭 클래스를 구현할 때 구현하는 메소드의 반환값이 파라미터화되어 있지만, 
// 현재 구현에서 반환할 것이 없을 때 이를 명확하게 나타내기 위해 Void 타입을 사용

interface Executable<T> {
	T execute();
}

public class UseVoid2 implements Executable<Void> {
	public Void execute() {
		return null;
	}
}