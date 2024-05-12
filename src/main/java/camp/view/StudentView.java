package camp.view;

import camp.common.StringHelper;
import camp.enumtype.SubjectList;
import camp.enumtype.SubjectType;
import camp.model.Student;
import camp.model.StudentCreateRequest;
import camp.model.Subject;
import camp.service.StudentCreateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentView {
    private final List<Subject> subjectStore;
    private final List<Student> studentStore;
    private final Scanner sc;

    private final StudentCreateService studentCreateService;

    public StudentView(List<Student> studentStore, List<Subject> subjectStore) {
        this.subjectStore = subjectStore;
        this.studentStore = studentStore;
        this.studentCreateService = new StudentCreateService(studentStore);
        this.sc = new Scanner(System.in);
    }

    // 수강생 등록
    public void createStudent() {
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();

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
        final List<Subject> mandatory = getMandatorySubjects(mandatorySubjects);

        // 선택과목 입력 받기
        final List<Subject> optional = getOptionalSubjects(optionalSubjects);

        // 학생 등록 요청
        final Student newStudent = studentCreateService.createStudent(new StudentCreateRequest(studentName, mandatory, optional));

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

            if (!validate(inputValue, subscribed, subjects)) {
                continue;
            }

            final int inputNum = Integer.parseInt(inputValue);
            final Subject selectedSubject = subjects.get(inputNum - 1); // 화면에 index + 1 한 값을 출력했으므로 -1 해줌
            subscribed.add(selectedSubject);

            if (subjects.size() > subscribed.size()) {
                continue;
            }

            break;
        }
        return subscribed;
    }

    private boolean validate(String inputValue, List<Subject> subscribed, List<Subject> subjects) {
        if (!StringHelper.isDigit(inputValue)) {
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
}
