package camp.enumtype;

/**
 * 시퀀스 관리용 클래스
 * 다른 패키지에서 이 클래스를 직접 사용하지 않게 하기 위해 class 접근자를 지정하지 않음
 */
class Sequence {
    private int seq;

    protected Sequence() {
        this.seq = 1;
    }

    protected Sequence(int startNumber) {
        this.seq = startNumber;
    }

    protected void increase() {
        seq++;
    }

    protected int getSeq() {
        return seq;
    }
}
