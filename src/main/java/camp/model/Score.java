package camp.model;

import camp.enumtype.IndexGenerator;

public class Score {
    private static final IndexGenerator indexGenerator = IndexGenerator.SCORE;

    private final String id;
    private final int round;
    private final int score;

    public Score(int round, int score) {
        this.id = indexGenerator.nextSeq();
        this.round = round;
        this.score = score;
    }

    // Getter
    public String getId() {
        return id;
    }

    public int getRound() {
        return round;
    }

    public int getScore() {
        return score;
    }
}
