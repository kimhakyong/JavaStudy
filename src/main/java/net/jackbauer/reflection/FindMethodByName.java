package net.jackbauer.reflection;

import java.lang.reflect.Method;

public class FindMethodByName {
	public int add(int a, int b) {
		return a + b;
	}
	
	public static void main(String args[]) {
		try {
			Class<?> cls = Class.forName("net.jackbauer.reflection.FindMethodByName");
			Class<?> parTypes[] = new Class[2];
			parTypes[0] = Integer.TYPE;
			parTypes[1] = Integer.TYPE;
			
			Method meth = cls.getMethod("add", parTypes);
			FindMethodByName methodObj = new FindMethodByName();
			Object argList[] = new Object[2];
			argList[0] = new Integer(37);
			argList[1] = new Integer(47);
			
			Object retObj = meth.invoke(methodObj, argList);
			Integer retVal = (Integer) retObj;
			System.out.println(retVal.intValue());
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}
