package net.jackbauer.reflection;

import java.lang.reflect.Array;

public class UseArray {
	public static void main(String args[]) {
		try {
			Class<?> cls = Class.forName("java.lang.String");
			Object arr = Array.newInstance(cls, 10);
			
			Array.set(arr, 5, "this is a test");
			for (int i = 0; i < Array.getLength(arr); i++) {
				System.out.printf("Class Type [%d] : %s%n", i, Array.get(arr, i));
			}
			
			String s = (String) Array.get(arr, 5);
			System.out.println(s);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
