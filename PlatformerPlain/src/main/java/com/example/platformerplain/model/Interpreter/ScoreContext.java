package com.example.platformerplain.model.Interpreter;

// ScoreContext class, which stores the variables needed for calculation
public class ScoreContext {
    private int maxScore;
    private int penaltyPerSecond;
    private long elapsedTime;
    private int killedEnemy;
    private static final int KILL_BONUS = 200; // Rewards points for each enemy killed

    public ScoreContext(int maxScore, int penaltyPerSecond, long elapsedTime, int killedEnemy) {
        this.maxScore = maxScore;
        this.penaltyPerSecond = penaltyPerSecond;
        this.elapsedTime = elapsedTime;
        this.killedEnemy = killedEnemy;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getPenaltyPerSecond() {
        return penaltyPerSecond;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public int getKilledEnemy() {
        return killedEnemy;
    }

    public static int getKillBonus() {
        return KILL_BONUS;
    }
}
