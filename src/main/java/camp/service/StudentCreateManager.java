package camp.service;

import camp.common.StringHelper;
import camp.enumtype.SubjectList;
import camp.enumtype.SubjectType;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentCreateManager {
    private final List<Student> studentStore;
    private final List<Subject> subjectStore;

    // 스캐너
    private final Scanner sc;

    public StudentCreateManager(List<Student> studentStore, List<Subject> subjectStore) {
        this.studentStore = studentStore;
        this.subjectStore = subjectStore;
        this.sc = new Scanner(System.in);
    }

    // 수강생 등록
    public void createStudent() {
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

            if (!validate(inputValue, subjects, student, subjectType)) {
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

    private boolean validate(String inputValue, List<Subject> subjects, Student student, SubjectType subjectType) {
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
        final Subject selectedSubject = subjects.get(subjectNum - 1); // 입력받은 숫자는 +1된 값이므로 -1을 해줘야 index가 맞다

        if (subscribedSubjects.contains(selectedSubject)) {
            System.out.println("이미 수강한 과목입니다.");
            student.printSubjects(subjectType);
            return false;
        }

        return true;
    }
}
