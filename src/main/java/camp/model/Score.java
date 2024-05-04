package camp.model;

import camp.enumtype.IndexGenerator;

public class Score {
    private static final IndexGenerator indexGenerator = IndexGenerator.SCORE;

    private final String id;
    private final int score;

    public Score(int score) {
        this.id = indexGenerator.nextSeq();
        this.score = score;
    }

    // Getter
    public String getId() {
        return id;
    }

    public int getScore() {
        return score;
    }
}
