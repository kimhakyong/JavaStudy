package net.jackbauer.voidtype;

import java.lang.reflect.Method;

// 리플렉션에서 반환값을 판별
public class UseVoid1 {
	public int foo() { return 0; }
	public void bar() {}
	
	public static void main(String[] args) throws NoSuchMethodException {
		examinteMethod("foo");
		examinteMethod("bar");
	}
	
	private static void examinteMethod(String name) throws NoSuchMethodException {
		Method m = UseVoid1.class.getMethod(name);
		Class<?> returnType = m.getReturnType();
		if (returnType.equals(Void.TYPE))
			System.out.println(name + " does not return anything");
		else
			System.out.println(name + " return a " + returnType);
	}
}
