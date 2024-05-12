package camp.service;

import camp.common.StringHelper;
import camp.model.Student;
import camp.model.StudentCreateRequest;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> studentList;

    public StudentService() {
        this.studentList = new ArrayList<>();
    }

    // 수강생 조회
    public List<Student> getStudentList() {
        return studentList;
    }

    // 수강생 등록
    public Student createStudent(StudentCreateRequest request) throws IllegalArgumentException {
        validate(request);

        final Student student = new Student(request.getName()
                                            , request.getMandatorySubjects()
                                            , request.getOptionalSubjects()
        );
        studentList.add(student);

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
