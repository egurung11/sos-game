package main;

public class Player {
    int score;

    public Player() {
        score = 0;
    }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public void incScore(int num) { this.score += num; }
}
