package com.example.platformerplain.model.Interpreter;

// ScoreContext class, which stores the variables needed for calculation
public class ScoreContext {
    private int maxScore;
    private int penaltyPerSecond;
    private long elapsedTime;
    private int killedEnemy;
    private static final int KILL_BONUS = 200; // Rewards points for each enemy killed

    /**
     * Constructs a ScoreContext with the specified parameters.
     *
     * @param maxScore         the maximum score attainable in the game
     * @param penaltyPerSecond the penalty points deducted per second
     * @param elapsedTime      the total time elapsed since the start of the game
     * @param killedEnemy      the number of enemies killed during the game
     */
    public ScoreContext(int maxScore, int penaltyPerSecond, long elapsedTime, int killedEnemy) {
        this.maxScore = maxScore;
        this.penaltyPerSecond = penaltyPerSecond;
        this.elapsedTime = elapsedTime;
        this.killedEnemy = killedEnemy;
    }

    /**
     * Gets the maximum score.
     *
     * @return the maximum score attainable in the game
     */
    public int getMaxScore() {
        return maxScore;
    }

    /**
     * Gets the penalty points per second.
     *
     * @return the penalty points deducted for each second elapsed in the game
     */
    public int getPenaltyPerSecond() {
        return penaltyPerSecond;
    }

    /**
     * Gets the elapsed time since the game started.
     *
     * @return the total elapsed time in milliseconds
     */
    public long getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Gets the number of enemies killed.
     *
     * @return the total number of enemies killed during the game
     */
    public int getKilledEnemy() {
        return killedEnemy;
    }

    /**
     * Gets the kill bonus for each enemy killed.
     *
     * @return the points awarded for each killed enemy
     */
    public static int getKillBonus() {
        return KILL_BONUS;
    }
}
