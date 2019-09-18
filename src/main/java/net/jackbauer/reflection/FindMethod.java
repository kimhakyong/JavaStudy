package net.jackbauer.reflection;

import java.lang.reflect.Method;

public class FindMethod {
	@SuppressWarnings("unused")
	private int f1(Object p, int x) throws NullPointerException {
		if (p == null)
			throw new NullPointerException();
		
		return x;
	}
	
	public static void main(String args[]) {
		try {
			Class<?> cls = Class.forName("net.jackbauer.reflection.FindMethod");
			
			Method methodList[] = cls.getDeclaredMethods();
			for (int i = 0; i < methodList.length; i++) {
				Method m = methodList[i];
				System.out.println("name = " + m.getName());
				System.out.println("class = " + m.getDeclaringClass());
				
				Class<?> pvec[] = m.getParameterTypes();
				for (int j = 0; j < pvec.length; j++)
					System.out.printf("param #%d = %s%n", j, pvec[j]);
				
				Class<?> evec[] = m.getExceptionTypes();
				for (int j = 0; j < evec.length; j++)
					System.out.printf("exc #%d = %s%n", j, evec[j]);
				
				System.out.printf("return type = %s%n", m.getReturnType());
				System.out.println("-----");
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}
