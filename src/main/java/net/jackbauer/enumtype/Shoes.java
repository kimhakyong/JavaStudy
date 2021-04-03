package net.jackbauer.enumtype;

public class Shoes {
	public String name;
	public int size;
	public ShoesType type;

	public static void main(String[] args) {
		Shoes shoes = new Shoes();
		
		shoes.name = "나이키";
		shoes.size = 230;
		shoes.type = ShoesType.WALKING;
		
		System.out.println("신발 이름 : " + shoes.name);
		System.out.println("신발 사이즈 : " + shoes.size);
		System.out.println("신발 종류 : " + shoes.type + " " + shoes.type.ordinal());

		if (shoes.type.equals(ShoesType.RUNNING))
			System.out.println("equal!");
		else
			System.out.println("not equal!");
		
		for (ShoesType type : ShoesType.values()) {
			System.out.println(type + " " + type.ordinal() + " " + type.getName());
		}
		
		ShoesType tp1 = ShoesType.WALKING;
		ShoesType tp2 = ShoesType.valueOf("WALKING");
		
		System.out.println(tp1);
		System.out.println(tp2);
	}
}
