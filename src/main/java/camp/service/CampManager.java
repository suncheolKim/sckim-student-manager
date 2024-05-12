package camp.service;

import camp.common.Menu;
import camp.model.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CampManager {
    // 데이터 저장소
    private List<Score> scoreStore;

    // 스캐너
    private Scanner sc = new Scanner(System.in);

    // 관리용 클래스
    private StudentManager sm;

    public CampManager() {
        scoreStore = new ArrayList<>();
        sm = new StudentManager();
    }

    public void start() {
        initData();
        try {
            displayMainView();
        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }

    // 초기 데이터 생성
    private void initData() {
    }

    private void displayMainView() throws InterruptedException {
        boolean flag = true;
        while (flag) {
            printMenu();
            int input = sc.nextInt();

            switch (input) {
                case 1 -> sm.displayView(); // 수강생 관리
                case 2 -> displayScoreView(); // 점수 관리
                case 3 -> flag = false; // 프로그램 종료
                default -> {
                    System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
                    Thread.sleep(2000);
                }
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    private void displayScoreView() {
        boolean flag = true;
        while (flag) {
            Menu.printManagePoint();
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    private String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        return sc.next();
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private void createScore() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        System.out.println("시험 점수를 등록합니다...");
        // 기능 구현

        System.out.println("## 등록 가능한 과목");
        scoreStore.forEach(s -> System.out.print(s.getScore()));
        System.out.println("과목을 입력하세요: ");
        System.out.println("");
        System.out.println("");

        System.out.println("\n점수 등록 성공!");
    }

    // 수강생의 과목별 회차 점수 수정
    private void updateRoundScoreBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (수정할 과목 및 회차, 점수)
        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private void inquireRoundGradeBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (조회할 특정 과목)
        System.out.println("회차별 등급을 조회합니다...");
        // 기능 구현
        System.out.println("\n등급 조회 성공!");
    }

    public void printMenu() {
        System.out.println("\n==================================");
        System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
        System.out.println("1. 수강생 관리");
        System.out.println("2. 점수 관리");
        System.out.println("3. 프로그램 종료");
        System.out.print("관리 항목을 선택하세요...");
    }
}
