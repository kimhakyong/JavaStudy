package net.jackbauer.reflection;

import java.lang.reflect.Array;

public class UseArray2 {
	public static void main(String args[]) {
		int dims[] = new int[] {5, 10, 15};
		Object arr = Array.newInstance(Integer.TYPE, dims);
		
		Object arrObj = Array.get(arr, 3);
		Class<?> cls = arrObj.getClass().getComponentType();
		System.out.println(cls);
		arrObj = Array.get(arrObj, 5);
		Array.setInt(arrObj, 10, 37);
		
		int arrCast[][][] = (int[][][]) arr;
		System.out.println(arrCast[3][5][10]);
	}
}
