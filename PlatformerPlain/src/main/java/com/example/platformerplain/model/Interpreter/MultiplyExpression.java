package com.example.platformerplain.model.Interpreter;

// MultiplyExpression class, representing the multiplication operation
public class MultiplyExpression implements Expression {
    private Expression left;
    private Expression right;

    public MultiplyExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret(ScoreContext context) {
        return left.interpret(context) * right.interpret(context);
    }
}
