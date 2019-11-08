package net.jackbauer.autocloseable;

public class B implements AutoCloseable {
	public B(A a) {
		
	}
	
	@Override
	public void close() throws Exception {
		System.out.println("close B");
	}
}
