public class Score {
    private int scoreCounter;

    public Score(int scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    public int getScore() {
        return scoreCounter;
    }

    public void increment() {
        scoreCounter++;
    }
}
