package net.jackbauer.autocloseable;

public class A implements AutoCloseable {
	@Override
	public void close() throws Exception {
		System.out.println("close A");
	}
}
