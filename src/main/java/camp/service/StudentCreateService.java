package camp.service;

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
    public Student createStudent(StudentCreateRequest request) {
        final Student student = new Student(request.getName()
                                            , request.getMandatorySubjects()
                                            , request.getOptionalSubjects()
        );
        studentStore.add(student);

        return student;
    }
}
