package com.fugaku.platformer.model.Interpreter;

// MultiplyExpression class, representing the multiplication operation
public class MultiplyExpression implements Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Constructs a MultiplyExpression with the specified left and right expressions.
     *
     * @param left  the left expression to be multiplied
     * @param right the right expression to be multiplied
     */
    public MultiplyExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Interprets the multiplication operation by evaluating the left and right expressions
     * within the given score context and returning their product.
     *
     * @param context the ScoreContext used for interpretation, providing necessary context
     *                for evaluating the expressions
     * @return the result of multiplying the evaluated left and right expressions
     */
    @Override
    public int interpret(ScoreContext context) {
        return left.interpret(context) * right.interpret(context);
    }
}
