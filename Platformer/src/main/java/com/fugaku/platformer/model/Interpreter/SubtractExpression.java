package com.fugaku.platformer.model.Interpreter;

// SubtractExpression class, representing the subtraction operation
public class SubtractExpression implements Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Constructs a SubtractExpression with the specified left and right expressions.
     *
     * @param left  the left expression to be subtracted from
     * @param right the right expression to subtract
     */
    public SubtractExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Interprets the subtraction operation by evaluating the left and right expressions
     * and returning their difference.
     *
     * @param context the ScoreContext that provides the necessary variable values for interpretation
     * @return the result of left expression evaluated in context minus the right expression evaluated in context
     */
    @Override
    public int interpret(ScoreContext context) {
        return left.interpret(context) - right.interpret(context);
    }
}
