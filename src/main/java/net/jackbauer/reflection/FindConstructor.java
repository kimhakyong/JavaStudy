package net.jackbauer.reflection;

import java.lang.reflect.Constructor;

public class FindConstructor {
	public FindConstructor() {}
	
	protected FindConstructor(int i, double d) {
		
	}
	
	public static void main(String args[]) {
		try {
			Class<?> cls = Class.forName("net.jackbauer.reflection.FindConstructor");
			
			Constructor<?> consList[] = cls.getDeclaredConstructors();
			for (int i = 0; i < consList.length; i++) {
				Constructor<?> cons = consList[i];
				System.out.printf("name = %s%n", cons.getName());
				System.out.printf("class = %s%n", cons.getDeclaringClass());
				
				Class<?> pvec[] = cons.getParameterTypes();
				for (int j = 0; j < pvec.length; j++)
					System.out.printf("param #%d = %s%n", j, pvec[j]);
				
				Class<?> evec[] = cons.getExceptionTypes();
				for (int j = 0; j < evec.length; j++)
					System.out.printf("exc #%d = %s%n", j, evec[j]);
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}
