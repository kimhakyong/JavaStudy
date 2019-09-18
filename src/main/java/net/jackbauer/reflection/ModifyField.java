package net.jackbauer.reflection;

import java.lang.reflect.Field;

public class ModifyField {
	public double d;
	
	public static void main(String args[]) {
		try {
			Class<?> cls = Class.forName("net.jackbauer.reflection.ModifyField");
			Field fld = cls.getField("d");
			
			ModifyField obj = new ModifyField();
			System.out.printf("d = %f%n", obj.d);
			
			fld.setDouble(obj, 1234);
			System.out.printf("d = %f%n", obj.d);
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}
