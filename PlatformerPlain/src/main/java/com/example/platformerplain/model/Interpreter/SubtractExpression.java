package com.example.platformerplain.model.Interpreter;

// SubtractExpression class, representing the subtraction operation
public class SubtractExpression implements Expression {
    private Expression left;
    private Expression right;

    public SubtractExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret(ScoreContext context) {
        return left.interpret(context) - right.interpret(context);
    }
}
