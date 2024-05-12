package camp.service;

import camp.model.Student;

import java.util.List;

public class StudentInquiryManager {
    private final List<Student> studentStore;

    public StudentInquiryManager(List<Student> studentStore) {
        this.studentStore = studentStore;
    }

    // 수강생 목록 조회
    public void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 기능 구현
        for (int i=0; i<studentStore.size(); i++) {
            System.out.println("<<" + (i+1) + ">>\n" + studentStore.get(i));
        }

        System.out.println("\n수강생 목록 조회 성공!");
    }
}
