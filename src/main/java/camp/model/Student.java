package camp.model;

import camp.enumtype.IndexGenerator;
import camp.enumtype.SubjectType;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private static final IndexGenerator indexGenerator = IndexGenerator.STUDENT;

    private final String id;
    private final String name;
    private final List<Subject> mandatorySubjects;
    private final List<Subject> optionalSubjects;
    private final List<Score> scores;

    public Student(String name) {
        this.id = indexGenerator.nextSeq();
        this.name = name;
        this.mandatorySubjects = new ArrayList<>();
        this.optionalSubjects = new ArrayList<>();
        this.scores = new ArrayList<>();
    }

    public void addMandatorySubject(Subject subject) {
        mandatorySubjects.add(subject);
    }

    public void addOptionalSubject(Subject subject) {
        optionalSubjects.add(subject);
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getMandatorySubjects() {
        return mandatorySubjects;
    }

    public List<Subject> getOptionalSubjects() {
        return optionalSubjects;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void printMandatorySubjects() {
        printSubjects(SubjectType.MANDATORY);
    }

    public void printOptionalSubjects() {
        printSubjects(SubjectType.OPTIONAL);
    }

    private void printSubjects(SubjectType subjectType) {
        final List<Subject> subjects;
        if (SubjectType.MANDATORY.equals(subjectType)) {
            subjects = mandatorySubjects;
        }
        else {
            subjects = optionalSubjects;
        }

        System.out.print("## 수강한 " + subjectType.getDesc() + " 과목: [");
        for (int i = 0; i< subjects.size(); i++) {
            if (i >= 1) {
                System.out.print(",\t");
            }
            System.out.print((i+1) + ": " + subjects.get(i).getName());
        }
        System.out.println("]");
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mandatorySubjects=" + mandatorySubjects +
                ", optionalSubjects=" + optionalSubjects +
                ", scores=" + scores +
                '}';
    }
}
