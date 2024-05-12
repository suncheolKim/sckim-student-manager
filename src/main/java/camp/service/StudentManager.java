package camp.service;

import camp.common.StringHelper;
import camp.enumtype.StudentMenu;
import camp.enumtype.SubjectList;
import camp.enumtype.SubjectType;
import camp.model.Student;
import camp.model.Subject;

import java.util.*;

public class StudentManager {
    private final List<Student> studentStore;
    private final List<Subject> subjectStore;

    // 스캐너
    private Scanner sc = new Scanner(System.in);

    public StudentManager() {
        studentStore = new ArrayList<>();
        subjectStore = new ArrayList<>();
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

            final StudentMenu selectedMenu;
            try {
                selectedMenu = StudentMenu.get(Integer.parseInt(inputString));
            } catch (IndexOutOfBoundsException e) {
                System.out.println("존재하지 않는 메뉴 입니다.\n");
                continue;
            }

            switch (selectedMenu) {
                case CREATE -> createStudent(); // 수강생 등록
                case INQUIRY -> inquireStudent(); // 수강생 목록 조회
                case TO_MAIN -> flag = false; // 메인 화면 이동
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
        if (!StringHelper.isDigit(inputValue)) {
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
            printStudent(i, studentStore);
        }

        System.out.println("\n수강생 목록 조회 성공!");
    }

    private void printStudent(int idx, List<?> store) {
        System.out.println((idx+1) + " : " + store.get(idx));
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
