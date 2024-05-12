package camp.model;

import java.util.List;

public class StudentCreateRequest {
    private final String name;
    private final List<Subject> mandatorySubjects;
    private final List<Subject> optionalSubjects;

    public StudentCreateRequest(String name, List<Subject> mandatorySubjects, List<Subject> optionalSubjects) {
        this.name = name;
        this.mandatorySubjects = mandatorySubjects;
        this.optionalSubjects = optionalSubjects;
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
}
