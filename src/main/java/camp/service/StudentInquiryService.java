package camp.service;

import camp.model.Student;

import java.util.List;

public class StudentInquiryService {
    private final List<Student> studentList;

    public StudentInquiryService(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }
}
