package refactoring.java.r08_rtcws;

public enum ShapeEnum {
    LINE("LINE"), RECTANGLE("RECTANGLE"), OVAL("OVAL");

    final private String name;

    ShapeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
