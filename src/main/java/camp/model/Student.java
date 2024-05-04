package camp.model;

import camp.enumtype.IndexGenerator;

public class Student {
    private static final IndexGenerator indexGenerator = IndexGenerator.STUDENT;

    private final String id;
    private final String name;

    public Student(String name) {
        this.id = indexGenerator.nextSeq();
        this.name = name;
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
