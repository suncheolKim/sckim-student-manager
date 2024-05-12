package camp.enumtype;

// 과목 타입
public enum SubjectType {
    MANDATORY("필수"), OPTIONAL("선택");

    private final String desc;

    SubjectType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
