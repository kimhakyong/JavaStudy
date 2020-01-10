package net.jackbauer.enumtype;

public class Shoes {
	public String name;
	public int size;
	public Type type;

	public static void main(String[] args) {
		Shoes shoes = new Shoes();
		
		shoes.name = "나이키";
		shoes.size = 230;
		shoes.type = Type.WALKING;
		
		System.out.println("신발 이름 : " + shoes.name);
		System.out.println("신발 사이즈 : " + shoes.size);
		System.out.println("신발 종류 : " + shoes.type + " " + shoes.type.ordinal());

		if (shoes.type.equals(Type.RUNNING))
			System.out.println("equal!");
		else
			System.out.println("not equal!");
		
		for (Type type : Type.values()) {
			System.out.println(type + " " + type.ordinal() + " " + type.getName());
		}
		
		Type tp1 = Type.WALKING;
		Type tp2 = Type.valueOf("WALKING");
		
		System.out.println(tp1);
		System.out.println(tp2);
	}
}
