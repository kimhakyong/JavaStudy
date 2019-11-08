package net.jackbauer.generic;

class NormalSample {
	private Object obj;
	
	public Object get() { return obj; }
	
	public void set(Object obj) { this.obj = obj; }
}

class GenericSample<T> {
	private T t;
	
	public T get() { return t; }
	
	public void set(T t) { this.t = t; }
	
	public <T2 extends Number> T2 methodOne(T2 t) {
		return t;
	}
	
	public GenericSample<String> methodTwo() {
		return new GenericSample<>();
	}
}

public class Sample {
	public static void main(String[] args) {
	    // 비제네릭
	    NormalSample normal = new NormalSample();
	    normal.set("콘"); 	// String -> Object (자동 타입 변환)
	    String normalMember = (String) normal.get();   // Object -> String (강제 타입 변환)
	    System.out.println(normalMember);
	    
	    // 제네릭
	    GenericSample<String> generic = new GenericSample<String>();
	    generic.set("콘");
	    String genericNember = generic.get();
	    System.out.println(genericNember);
	    
	    System.out.println(generic.methodOne(2));
	}
}