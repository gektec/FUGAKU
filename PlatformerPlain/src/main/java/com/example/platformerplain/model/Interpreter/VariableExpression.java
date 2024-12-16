package com.example.platformerplain.model.Interpreter;

// VariableExpression class, representing a variable
public class VariableExpression implements Expression {
    private final VariableType type;

    /**
     * Constructs a VariableExpression of the specified variable type.
     *
     * @param type the type of variable this expression represents,
     *             which can be MAX_SCORE, PENALTY_PER_SECOND,
     *             ELAPSED_SECONDS, or KILLED_ENEMY
     */
    public VariableExpression(VariableType type) {
        this.type = type;
    }

    /**
     * Interprets the variable expression by retrieving the value corresponding
     * to its type from the provided ScoreContext.
     *
     * @param context the ScoreContext that provides the necessary variable values for interpretation
     * @return the integer value of the variable represented by this expression,
     *         retrieved from the context based on its type
     */
    @Override
    public int interpret(ScoreContext context) {
        return switch (type) {
            case MAX_SCORE -> context.getMaxScore();
            case PENALTY_PER_SECOND -> context.getPenaltyPerSecond();
            case ELAPSED_SECONDS -> (int) (context.getElapsedTime() / 1000);
            case KILLED_ENEMY -> context.getKilledEnemy() * ScoreContext.getKillBonus();
            default -> 0;
        };
    }

    /**
     * Enum representing the types of variables that can be used in expressions.
     */
    public enum VariableType {
        MAX_SCORE,
        PENALTY_PER_SECOND,
        ELAPSED_SECONDS,
        KILLED_ENEMY
    }
}
