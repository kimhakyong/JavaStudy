package net.jackbauer.reflection;

import java.lang.reflect.Method;

// 리플렉션이란 객체를 통해 클래스의 정보를 분석해 내는 프로그램 기법
// 투영, 반사 라는 사전적인 의미
// 실행중인 자바프로그램 내부를 검사하고 내부의 속성을 수정있음

public class DumpMethods {
	public static void main(String args[]) {
		try {
			System.out.println("Class : " + args[0]);
			Class<?> c = Class.forName(args[0]);
			Method m[] = c.getDeclaredMethods();

			for (int i = 0; i < m.length; i++)
				System.out.println(m[i].toString());
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}
