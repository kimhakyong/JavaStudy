package net.jackbauer.reflection;

import java.lang.reflect.Constructor;

public class AddConstructor {
	int a;
	int b;
	
	public AddConstructor() {
		
	}
	
	public AddConstructor(int a, int b) {
		System.out.printf("a = %d, b = %d%n", a, b);
		this.a = a;
		this.b = b;
	}
	
	public int add() {		
		return this.a + this.b;
	}
	
	public static void main(String args[]) {
		try {
			Class<?> cls = Class.forName("net.jackbauer.reflection.AddConstructor");
			Class<?> parTypes[] = new Class[2];
			parTypes[0] = Integer.TYPE;
			parTypes[1] = Integer.TYPE;
			
			Constructor<?> con = cls.getConstructor(parTypes);
			Object argList[] = new Object[2];
			argList[0] = new Integer(37);
			argList[1] = new Integer(47);
			AddConstructor retObj = (AddConstructor) con.newInstance(argList);
			int retVal = retObj.add();
			
			System.out.printf("Add() Return Value : %d%n", retVal);
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}
