package camp.view;

import camp.common.StringHelper;
import camp.enumtype.SubjectList;
import camp.enumtype.SubjectType;
import camp.model.Student;
import camp.model.StudentCreateRequest;
import camp.model.Subject;
import camp.service.StudentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StudentView {
    private final List<Subject> subjectList;
    private final Scanner sc;

    private final StudentService studentService;

    public StudentView() {
        this.subjectList = new ArrayList<>();
        this.studentService = new StudentService();
        this.sc = new Scanner(System.in);
        initSubjects(subjectList);
    }

    // 수강생 등록
    public void createStudent() {
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();

        // 필수과목 선택과목으로 분류
        final List<Subject> mandatorySubjects = new ArrayList<>();
        final List<Subject> optionalSubjects = new ArrayList<>();
        for (Subject sbj : subjectList) {
            if (SubjectType.MANDATORY.equals(sbj.getType())) {
                mandatorySubjects.add(sbj);
            }
            else if (SubjectType.OPTIONAL.equals(sbj.getType())) {
                optionalSubjects.add(sbj);
            }
        }

        // 필수과목 입력 받기
        final List<Subject> mandatory = getMandatorySubjects(mandatorySubjects);

        // 선택과목 입력 받기
        final List<Subject> optional = getOptionalSubjects(optionalSubjects);

        // 학생 등록 요청
        final Student newStudent;
        try {
            newStudent = studentService.createStudent(new StudentCreateRequest(studentName, mandatory, optional));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println(newStudent);
    }

    private List<Subject> getMandatorySubjects(List<Subject> mandatorySubjects) {
        return getSubjects(mandatorySubjects, SubjectType.MANDATORY);
    }

    private List<Subject> getOptionalSubjects(List<Subject> optionalSubjects) {
        return getSubjects(optionalSubjects, SubjectType.OPTIONAL);
    }

    private List<Subject> getSubjects(List<Subject> subjects, SubjectType subjectType) {
        final int minCountOfSubject = SubjectList.getMinCountBy(subjectType);

        for (int i = 0; i< subjects.size(); i++) {
            System.out.print((i+1) + ": " + subjects.get(i).getName() + "\t");
        }

        final List<Subject> subscribed = new ArrayList<>(subjects.size());
        boolean hasMinSubjects = false;
        while (true) {
            // 수강 가능한 모든 과목을 선택하면 종료
            if (subjects.size() <= subscribed.size()) {
                break;
            }

            if (minCountOfSubject <= subscribed.size()) {
                hasMinSubjects = true;
            }

            final String msg = getPrintMessage(subjectType, minCountOfSubject, hasMinSubjects);
            System.out.println(msg);
            final String inputValue = sc.next();

            // 최소 입력 과목수를 만족하고
            if (hasMinSubjects && "q".equalsIgnoreCase(inputValue)) {
                break;
            }

            if (!validate(inputValue, subscribed, subjects)) {
                continue;
            }

            final int inputNum = Integer.parseInt(inputValue);
            final Subject selectedSubject = subjects.get(inputNum - 1); // 화면에 index + 1 한 값을 출력했으므로 -1 해줌
            subscribed.add(selectedSubject);
            printSubjects(subscribed);
        }

        return subscribed;
    }

    private String getPrintMessage(SubjectType subjectType, int minCountOfSubject, boolean hasMinSubjects) {
        final StringBuilder msg = new StringBuilder("\n" + subjectType.getDesc() +
                                                        " 과목 입력 (최소 " + minCountOfSubject + "개)");
        if (hasMinSubjects) {
            msg.append(" - 추가를 끝내시려면 `q`를 눌러주세요");
        }

        msg.append(" : ");

        return msg.toString();
    }

    private boolean validate(String inputValue, List<Subject> subscribed, List<Subject> subjects) {
        if (StringHelper.isNotDigit(inputValue)) {
            System.out.println("숫자만 입력해주세요.");
            return false;
        }

        final int subjectNum = Integer.parseInt(inputValue);
        if (subjectNum > subjects.size() || 0 >= subjectNum) {
            System.out.println("없는 과목 번호 입니다. (입력가능 1 ~ " + subjects.size() + ")");
            return false;
        }

        final Subject selectedSubject = subjects.get(subjectNum - 1);
        if (subscribed.contains(selectedSubject)) {
            System.out.println("이미 수강한 과목입니다.");
            printSubjects(subscribed);
            return false;
        }

        return true;
    }

    public void printSubjects(List<Subject> subjects) {
        if (subjects.size() <= 0) {
            System.out.println("## 수강중인 과목이 없습니다.");
            return;
        }

        System.out.print("## 수강한 " + subjects.get(0).getType().getDesc() + " 과목: [");
        for (int i = 0; i< subjects.size(); i++) {
            if (i >= 1) {
                System.out.print(",\t");
            }
            System.out.print((i+1) + ": " + subjects.get(i).getName());
        }
        System.out.println("]");
    }

    public void inquireStudent() {
        final List<Student> studentList = studentService.getStudentList();

        if (studentList.isEmpty()) {
            System.out.println("\n등록된 수강생이 없습니다...");
        }
        else {
            System.out.println("\n수강생 목록을 조회합니다...");

            for (int i = 0; i < studentList.size(); i++) {
                System.out.println("<<" + (i + 1) + ">>\n" + studentList.get(i));
            }

            System.out.println("\n수강생 목록 조회 성공!");
        }
    }

    private void initSubjects(List<Subject> subjectList) {
        Arrays.stream(SubjectList.values())
                .forEach(enumSubject -> subjectList.add(new Subject(enumSubject)));
    }
}
