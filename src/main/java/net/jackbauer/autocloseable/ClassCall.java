package net.jackbauer.autocloseable;

public class ClassCall {
	public static void main(String[] args) {
		// 변수를 만들지 않은 상태로 사용할 경우 close() 메서드가 호출되지 않음
		// try (B b = new B(new A())) { 
		try (A a = new A(); B b = new B(a)) {  
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
