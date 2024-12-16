package com.fugaku.platformer.model.Interpreter;

// ScoreInterpreter class, used to build and interpret expression trees
public class ScoreInterpreter {
    private final Expression expression;

    /**
     * Constructs a ScoreInterpreter instance and initializes the expression tree.
     * The expression tree corresponds to the calculation:
     * maxScore - (penaltyPerSecond * elapsedSeconds) + (killedEnemy * 200).
     */
    public ScoreInterpreter() {
        // Constructing an expression tree corresponds to maxScore - (penaltyPerSecond * secondsElapsed) + (killedEnemy * 200)
        expression = new AddExpression(
                new SubtractExpression(
                        new VariableExpression(VariableExpression.VariableType.MAX_SCORE),
                        new MultiplyExpression(
                                new VariableExpression(VariableExpression.VariableType.PENALTY_PER_SECOND),
                                new VariableExpression(VariableExpression.VariableType.ELAPSED_SECONDS)
                        )
                ),
                new VariableExpression(VariableExpression.VariableType.KILLED_ENEMY)
        );
    }

    /**
     * Interprets the expression tree using the provided ScoreContext.
     *
     * @param context the ScoreContext that provides the necessary variable values for interpretation
     * @return the result of the expression evaluation based on the current score context
     */
    public int interpret(ScoreContext context) {
        return expression.interpret(context);
    }
}
