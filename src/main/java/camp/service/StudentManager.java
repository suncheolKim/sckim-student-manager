package camp.service;

import camp.common.StringHelper;
import camp.enumtype.StudentMenu;
import camp.enumtype.SubjectList;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StudentManager {
    private final List<Student> studentStore;
    private final List<Subject> subjectStore;
    private final StudentCreateManager scm;
    private final StudentInquiryManager sim;

    // 스캐너
    private final Scanner sc = new Scanner(System.in);

    public StudentManager() {
        studentStore = new ArrayList<>();
        subjectStore = new ArrayList<>();
        scm = new StudentCreateManager(studentStore, subjectStore);
        sim = new StudentInquiryManager(studentStore);
        initSubjects();
    }

    public void displayView() {
        boolean flag = true;
        while (flag) {
            printMenu();
            final String inputString = sc.next();
            if (!StringHelper.isDigit(inputString)) {
                System.out.println("숫자만 입력해주세요.\n");
                continue;
            }

            final StudentMenu studentMenu;
            try {
                studentMenu = StudentMenu.get(Integer.parseInt(inputString));
            } catch (IndexOutOfBoundsException e) {
                System.out.println("존재하지 않는 메뉴 입니다.\n");
                continue;
            }

            switch (studentMenu) {
                case CREATE -> scm.createStudent(); // 수강생 등록
                case INQUIRY -> sim.inquireStudent(); // 수강생 목록 조회
                case TO_MAIN -> flag = false; // 메인 화면 이동
            }
        }
    }

    private void printMenu() {
        System.out.println("==================================");
        System.out.println("수강생 관리 실행 중...");
        System.out.println("1. 수강생 등록");
        System.out.println("2. 수강생 목록 조회");
        System.out.println("3. 메인 화면 이동");
        System.out.print("관리 항목을 선택하세요...");
    }

    private void initSubjects() {
        Arrays.stream(SubjectList.values())
                .forEach(enumSubject -> subjectStore.add(new Subject(enumSubject)));
    }
}
