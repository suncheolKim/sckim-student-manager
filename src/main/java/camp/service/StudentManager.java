package camp.service;

import camp.common.StringHelper;
import camp.enumtype.StudentMenu;
import camp.enumtype.SubjectList;
import camp.model.Student;
import camp.model.Subject;
import camp.view.StudentView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StudentManager {
    // 멤버 변수
    private final List<Student> studentStore;
    private final List<Subject> subjectStore;

    // 서비스
    private final StudentView studentView;
    private final StudentCreateService scm;
    private final StudentInquiryService sim;

    // 스캐너
    private final Scanner sc = new Scanner(System.in);

    public StudentManager() {
        this.studentStore = new ArrayList<>();
        this.subjectStore = new ArrayList<>();

        this.studentView = new StudentView(studentStore, subjectStore);
        this.scm = new StudentCreateService(studentStore);
        this.sim = new StudentInquiryService(studentStore);
        initSubjects();
    }

    public void displayView() {
        boolean flag = true;
        while (flag) {
            printMenu();
            final String inputString = sc.next();
            if (StringHelper.isNotDigit(inputString)) {
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
                case CREATE -> studentView.createStudent(); // 수강생 등록
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
