package camp.enumtype;

// 인덱스 타입
public enum IndexGenerator {
     STUDENT("ST",new Sequence())
    ,SUBJECT("SU",new Sequence())
    ,SCORE  ("SC",new Sequence())
    ;

    private final String prefix;
    private final Sequence seq;

    IndexGenerator(String prefix, Sequence seq) {
        this.prefix = prefix;
        this.seq = seq;
    }

    public String nextSeq() {
        final int currentIndex = this.seq.getSeq();
        this.seq.increase();

        return this.prefix + "-" + currentIndex;
    }
}
