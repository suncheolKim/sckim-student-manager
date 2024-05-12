package camp.service;

import camp.common.Menu;
import camp.enumtype.SubjectList;
import camp.enumtype.SubjectType;
import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CampManager {
    // 데이터 저장소
    private List<Student> studentStore;
    private List<Subject> subjectStore;
    private List<Score> scoreStore;

    // 스캐너
    private Scanner sc = new Scanner(System.in);

    public CampManager() {
        studentStore = new ArrayList<>();
        subjectStore = new ArrayList<>();
        scoreStore = new ArrayList<>();
    }

    public void start() {
        setInitData();
        try {
            displayMainView();
        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }

    // 초기 데이터 생성
    private void setInitData() {
        Arrays.stream(SubjectList.values())
                .forEach(enumSubject -> subjectStore.add(new Subject(enumSubject)));
    }

    private void displayMainView() throws InterruptedException {
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

    private void displayStudentView() {
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
    private void createStudent() {
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

        // 등록한 학생 출력
        System.out.println(student);
    }

    private void putMandatorySubjects(Student student, List<Subject> mandatorySubjects) {
        putSubjects(mandatorySubjects, student, SubjectType.MANDATORY);
    }

    private void putOptionalSubjects(Student student, List<Subject> optionalSubjects) {
        putSubjects(optionalSubjects, student, SubjectType.OPTIONAL);
    }

    private void putSubjects(List<Subject> subjects, Student student, SubjectType subjectType) {
        final int minCountOfSubject = SubjectList.getMinCountBy(subjectType);
        final List<Subject> subscribed = student.getSubjects(subjectType);

        for (int i = 0; i< subjects.size(); i++) {
            System.out.print((i+1) + ": " + subjects.get(i).getName() + "\t");
        }

        while(true) {
            final String inputValue;
            if (minCountOfSubject <= subscribed.size()) {
                System.out.println("\n" + subjectType.getDesc() + "과목 입력 (최소 " + minCountOfSubject + "개) - 추가를 끝내시려면 `q`를 눌러주세요 : ");
                inputValue = sc.next();
                if ("q".equalsIgnoreCase(inputValue)) {
                    break;
                }
            }
            else {
                System.out.println("\n" + subjectType.getDesc() + "과목 입력 (최소 " + minCountOfSubject + "개) : ");
                inputValue = sc.next();
            }

            if (!validateInputValue(inputValue, subjects, student, subjectType)) {
                continue;
            }

            final int inputNum = Integer.parseInt(inputValue);
            final Subject selectedSubject = subjects.get(inputNum - 1); // 화면에 index + 1 한 값을 출력했으므로 -1 해줌

            student.addSubject(selectedSubject);
            student.printSubjects(subjectType);

            if (subjects.size() > subscribed.size()) {
                continue;
            }

            break;
        }
    }

    private boolean validateInputValue(String inputValue, List<Subject> subjects, Student student, SubjectType subjectType) {
        final boolean isDigit = inputValue.matches("^-?[0-9]+$");

        if (!isDigit) {
            System.out.println("숫자만 입력해주세요.");
            return false;
        }

        final int subjectNum = Integer.parseInt(inputValue);
        if (subjectNum > subjects.size() || 0 >= subjectNum) {
            System.out.println("없는 과목 번호 입니다. (입력가능 1 ~ " + subjects.size() + ")");
            return false;
        }

        final List<Subject> subscribedSubjects = student.getSubjects(subjectType);
        final Subject selectedSubject = subjects.get(subjectNum - 1);

        if (subscribedSubjects.contains(selectedSubject)) {
            System.out.println("이미 수강한 과목입니다.");
            student.printSubjects(subjectType);
            return false;
        }

        return true;
    }

    // 수강생 목록 조회
    private void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 기능 구현
        for (int i=0; i<studentStore.size(); i++) {
            printStudent(i+1, studentStore);
        }

        System.out.println("\n수강생 목록 조회 성공!");
    }

    private void printStudent(int idx, List<?> store) {
        System.out.println(idx + " : " + store.get(idx));
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

    private void setTestData() {
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
}
