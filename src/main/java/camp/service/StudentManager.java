package camp.service;

import camp.common.StringHelper;
import camp.enumtype.StudentMenu;
import camp.view.StudentView;

import java.util.Scanner;

public class StudentManager {
    // 서비스
    private final StudentView studentView;

    // 스캐너
    private final Scanner sc = new Scanner(System.in);

    public StudentManager() {
        this.studentView = new StudentView();
    }

    public void start() {
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
                case INQUIRY -> studentView.inquireStudent(); // 수강생 목록 조회
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
}
