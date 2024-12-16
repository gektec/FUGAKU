package com.example.platformerplain.model.Interpreter;

// AddExpression class, representing the addition operation
public class AddExpression implements Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Constructs an AddExpression with the specified left and right expressions.
     *
     * @param left  the left expression to be added
     * @param right the right expression to be added
     */
    public AddExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Interprets the addition operation by evaluating the left and right expressions
     * within the given score context, and returning their sum.
     *
     * @param context the ScoreContext used for interpretation, providing necessary context
     *                for evaluating the expressions
     * @return the result of adding the evaluated left and right expressions
     */
    @Override
    public int interpret(ScoreContext context) {
        return left.interpret(context) + right.interpret(context);
    }
}
