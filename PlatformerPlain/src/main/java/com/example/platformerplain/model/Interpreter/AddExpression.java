package com.example.platformerplain.model.Interpreter;

// AddExpression class, representing the addition operation
public class AddExpression implements Expression {
    private Expression left;
    private Expression right;

    public AddExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret(ScoreContext context) {
        return left.interpret(context) + right.interpret(context);
    }
}
