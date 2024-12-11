package com.example.platformerplain.model;

// ScoreInterpreter class, used to build and interpret expression trees
public class ScoreInterpreter {
    private Expression expression;

    public ScoreInterpreter() {
        // 构建表达式树对应于 maxScore - (penaltyPerSecond * secondsElapsed) + (killedEnemy * 200)
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

    public int interpret(ScoreContext context) {
        return expression.interpret(context);
    }
}
