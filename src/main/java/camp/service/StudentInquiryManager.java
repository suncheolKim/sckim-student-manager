package camp.service;

import camp.model.Student;
import camp.model.Subject;

import java.util.List;

public class StudentInquiryManager {
    private final List<Student> studentStore;
    private final List<Subject> subjectStore;

    public StudentInquiryManager(List<Student> studentStore, List<Subject> subjectStore) {
        this.studentStore = studentStore;
        this.subjectStore = subjectStore;
    }

    // 수강생 목록 조회
    public void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 기능 구현
        for (int i=0; i<studentStore.size(); i++) {
            printStudent(i, studentStore);
        }

        System.out.println("\n수강생 목록 조회 성공!");
    }

    private void printStudent(int idx, List<?> store) {
        System.out.println((idx+1) + " : " + store.get(idx));
    }
}
