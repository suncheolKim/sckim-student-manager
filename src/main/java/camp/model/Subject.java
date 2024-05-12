package camp.model;

import camp.enumtype.SubjectList;
import camp.enumtype.IndexGenerator;
import camp.enumtype.SubjectType;

public class Subject {
    // 인덱스는 외부에서 알 필요가 없다
    private static final IndexGenerator indexGenerator = IndexGenerator.SUBJECT;

    private final String id;
    private final String name;
    private final SubjectType type;

    public Subject(SubjectList subjectList) {
        this.id = indexGenerator.nextSeq();
        this.name = subjectList.getName();
        this.type = subjectList.getType();
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SubjectType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
