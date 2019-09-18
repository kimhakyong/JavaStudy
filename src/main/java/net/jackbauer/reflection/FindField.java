package net.jackbauer.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FindField {
	@SuppressWarnings("unused")
	private double d;
	public static final int i = 37;
	String s = "testing";
	
	public static void main(String args[]) {
		try {
			Class<?> cls = Class.forName("net.jackbauer.reflection.FindField");
			Field fieldList[] = cls.getDeclaredFields();
			
			for (int i = 0; i < fieldList.length; i++) {
				Field fld = fieldList[i];
				System.out.printf("name = %s%n", fld.getName());
				System.out.printf("decl class = %s%n", fld.getDeclaringClass());
				System.out.printf("type = %s%n", fld.getType());
				
				int mod = fld.getModifiers();
				
				System.out.printf("modifiers = %s%n", Modifier.toString(mod));
				System.out.println("-----");
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}