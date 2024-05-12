package camp.service;

import camp.common.Menu;
import camp.enumtype.SubjectList;
import camp.enumtype.SubjectType;
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
        final Student student = new Student(studentName); // 수강생 인스턴스 생성 예시 코드
        studentStore.add(student);

        // 필수과목 선택과목으로 분류
        final List<Subject> mandatorySubjects = new ArrayList<>();
        final List<Subject> optionalSubjects = new ArrayList<>();
        for (Subject sbj : subjectStore) {
            if (SubjectType.MANDATORY.equals(sbj.getType())) {
                mandatorySubjects.add(sbj);
            }
            else if (SubjectType.OPTIONAL.equals(sbj.getType())) {
                optionalSubjects.add(sbj);
            }
        }

        // 필수과목 입력 받기
        putMandatorySubjects(student, mandatorySubjects);

        // 선택과목 입력 받기
        putOptionalSubjects(student, optionalSubjects);

        // 기능 구현
        System.out.println(student);
    }

    private static void putMandatorySubjects(Student student, List<Subject> mandatorySubjects) {
        putSubjects(mandatorySubjects, student, SubjectType.MANDATORY);
    }

    private static void putOptionalSubjects(Student student, List<Subject> optionalSubjects) {
        putSubjects(optionalSubjects, student, SubjectType.OPTIONAL);
    }

    private static void putSubjects(List<Subject> subjects, Student student, SubjectType subjectType) {
        final int minCountOfSubject;
        final List<Subject> subscribedSubjects;
        if (SubjectType.MANDATORY.equals(subjectType)) {
            minCountOfSubject = SubjectList.MIN_COUNT_OF_MANDATORY_SUBJECT;
            subscribedSubjects = student.getMandatorySubjects();
        }
        else {
            minCountOfSubject = SubjectList.MIN_COUNT_OF_OPTIONAL_SUBJECT;
            subscribedSubjects = student.getOptionalSubjects();
        }

        for (int i = 0; i< subjects.size(); i++) {
            System.out.print((i+1) + ": " + subjects.get(i).getName() + "\t");
        }

        while(true) {
            String inputSubject = "";
            if (minCountOfSubject <= subscribedSubjects.size()) {
                System.out.println("\n" + subjectType.getDesc() + "과목 입력 (최소 " + minCountOfSubject + "개) - 추가를 끝내시려면 `q`를 눌러주세요 : ");
                inputSubject = sc.next();
                if ("q".equalsIgnoreCase(inputSubject)) {
                    break;
                }
            }
            else {
                System.out.println("\n" + subjectType.getDesc() + "과목 입력 (최소 " + minCountOfSubject + "개) : ");
                inputSubject = sc.next();
            }

            final boolean isDigit = inputSubject.matches("^[0-9]+$");

            if (!isDigit) {
                System.out.println("숫자만 입력해주세요.");
                continue;
            }
            final int subjectNum = Integer.parseInt(inputSubject);
            if (subjectNum > subjects.size() || 0 == subjectNum) {
                System.out.println("없는 과목 번호 입니다. (입력가능 1 ~ " + subjects.size() + ")");
                continue;
            }

            final Subject selectedSubject = subjects.get(subjectNum - 1);

            if (subscribedSubjects.contains(selectedSubject)) {
                System.out.println("이미 수강한 과목입니다.");
                if (SubjectType.MANDATORY.equals(subjectType)) {
                    student.printMandatorySubjects();
                }
                else {
                    student.printOptionalSubjects();
                }
                continue;
            }


            if (SubjectType.MANDATORY.equals(subjectType)) {
                student.addMandatorySubject(selectedSubject);
                student.printMandatorySubjects();
            }
            else {
                student.addOptionalSubject(selectedSubject);
                student.printOptionalSubjects();
            }

            if (subjects.size() > subscribedSubjects.size()) {
                continue;
            }

            break;
        }
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
        scoreStore.add(new Score(1,10));
        scoreStore.add(new Score(2,20));
        scoreStore.add(new Score(3, 30));
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

        System.out.println("## 등록 가능한 과목");
        scoreStore.forEach(s -> System.out.print(s.getScore()));
        System.out.println("과목을 입력하세요: ");
        System.out.println("");
        System.out.println("");

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
