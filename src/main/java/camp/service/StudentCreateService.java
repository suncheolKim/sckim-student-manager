package camp.service;

import camp.common.StringHelper;
import camp.model.Student;
import camp.model.StudentCreateRequest;

import java.util.List;
import java.util.Scanner;

public class StudentCreateService {
    private final List<Student> studentStore;

    // 스캐너
    private final Scanner sc;

    public StudentCreateService(List<Student> studentStore) {
        this.studentStore = studentStore;
        this.sc = new Scanner(System.in);
    }

    // 수강생 등록
    public Student createStudent(StudentCreateRequest request) throws IllegalArgumentException {
        validate(request);

        final Student student = new Student(request.getName()
                                            , request.getMandatorySubjects()
                                            , request.getOptionalSubjects()
        );
        studentStore.add(student);

        return student;
    }

    private void validate(StudentCreateRequest request) throws IllegalArgumentException{
        if (StringHelper.isEmpty(request.getName())) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (null == request.getMandatorySubjects() || request.getMandatorySubjects().isEmpty()) {
            throw new IllegalArgumentException("Mandatory subjects cannot be empty");
        }

        if (null == request.getOptionalSubjects() || request.getOptionalSubjects().isEmpty()) {
            throw new IllegalArgumentException("Optional subjects cannot be empty");
        }
    }
}
