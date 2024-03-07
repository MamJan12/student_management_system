package studentmanagementsystem;

public class Gender {
    private String name;

    public Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
