package com.example.platformerplain.model;

// VariableExpression class, representing a variable
public class VariableExpression implements Expression {
    private VariableType type;

    public VariableExpression(VariableType type) {
        this.type = type;
    }

    @Override
    public int interpret(ScoreContext context) {
        switch (type) {
            case MAX_SCORE:
                return context.getMaxScore();
            case PENALTY_PER_SECOND:
                return context.getPenaltyPerSecond();
            case ELAPSED_SECONDS:
                return (int) (context.getElapsedTime() / 1000);
            case KILLED_ENEMY:
                return context.getKilledEnemy() * ScoreContext.getKillBonus();
            default:
                return 0;
        }
    }

    public enum VariableType {
        MAX_SCORE,
        PENALTY_PER_SECOND,
        ELAPSED_SECONDS,
        KILLED_ENEMY
    }
}
