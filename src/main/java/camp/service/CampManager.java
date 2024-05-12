package camp.service;

import camp.common.Menu;
import camp.enumtype.SubjectList;
import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CampManager {
    // 데이터 저장소
    private static List<Student> studentStore;
    private static List<Subject> subjectStore;
    private static List<Score> scoreStore;

    // 스캐너
    private static Scanner sc = new Scanner(System.in);

    public void start() {
        setInitData();
        try {
            displayMainView();
        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }

    // 초기 데이터 생성
    private static void setInitData() {
        studentStore = new ArrayList<>();
        subjectStore = List.of(
                new Subject(SubjectList.JAVA)
                ,new Subject(SubjectList.OOP)
                ,new Subject(SubjectList.SPRING)
                ,new Subject(SubjectList.JPA)
                ,new Subject(SubjectList.MYSQL)
                ,new Subject(SubjectList.DESIGN_PATTERN)
                ,new Subject(SubjectList.SPRING_SECURITY)
                ,new Subject(SubjectList.REDIS)
                ,new Subject(SubjectList.MONGODB)
        );
        scoreStore = new ArrayList<>();
    }

    private static void displayMainView() throws InterruptedException {
        boolean flag = true;
        while (flag) {
            Menu.printMainMenu();
            int input = sc.nextInt();

            switch (input) {
                case 1 -> displayStudentView(); // 수강생 관리
                case 2 -> displayScoreView(); // 점수 관리
                case 3 -> flag = false; // 프로그램 종료
                case 99 -> setTestData(); // 테스트 데이터 생성
                default -> {
                    System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
                    Thread.sleep(2000);
                }
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    private static void displayStudentView() {
        boolean flag = true;
        while (flag) {
            Menu.printManageStudent();
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createStudent(); // 수강생 등록
                case 2 -> inquireStudent(); // 수강생 목록 조회
                case 3 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    // 수강생 등록
    private static void createStudent() {
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();
        // 기능 구현 (필수 과목, 선택 과목)

        Student student = new Student(studentName); // 수강생 인스턴스 생성 예시 코드
        studentStore.add(student);

        // 기능 구현
        System.out.println(student);
    }

    // 수강생 목록 조회
    private static void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 기능 구현
        for (int i=0; i<studentStore.size(); i++) {
            printStudent(i, studentStore);
        }

        System.out.println("\n수강생 목록 조회 성공!");
    }

    private static void printStudent(int idx, List<?> store) {
        System.out.println(idx + " : " + store.get(idx));
    }

    private static void displayScoreView() {
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

    private static void setTestData() {
        studentStore.add(new Student("아이유"));
        studentStore.add(new Student("마동석"));
        studentStore.add(new Student("카즈하"));
        studentStore.add(new Student("안유진"));
        studentStore.add(new Student("차은우"));
        scoreStore.add(new Score(10));
        scoreStore.add(new Score(20));
        scoreStore.add(new Score(30));
        System.out.println("테스트 데이터 생성 완료!");
    }

    private static String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        return sc.next();
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private static void createScore() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        System.out.println("시험 점수를 등록합니다...");
        // 기능 구현
        System.out.println("\n점수 등록 성공!");
    }

    // 수강생의 과목별 회차 점수 수정
    private static void updateRoundScoreBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (수정할 과목 및 회차, 점수)
        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private static void inquireRoundGradeBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (조회할 특정 과목)
        System.out.println("회차별 등급을 조회합니다...");
        // 기능 구현
        System.out.println("\n등급 조회 성공!");
    }
}
