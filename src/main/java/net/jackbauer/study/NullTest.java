package net.jackbauer.study;

import java.util.HashMap;

public class NullTest {
	public static void main(String[] args) {
		try {
			HashMap<Object, String> map = new HashMap<>();
			map.put("people", "사람");
			map.put("baseball", "야구");

			Object obj = map.get(null);
			System.out.println(obj.toString());
		} catch (NullPointerException ex) {
			System.out.println(ex.getStackTrace());
			System.out.println(ex.getMessage());
			throw ex;
		}
	}
}
