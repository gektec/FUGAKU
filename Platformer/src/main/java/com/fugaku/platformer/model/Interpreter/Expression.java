package com.fugaku.platformer.model.Interpreter;

// Expression interface
public interface Expression {
    /**
     * Interprets the expression within the given score context.
     *
     * @param context the ScoreContext used for interpretation, providing necessary state
     *                for evaluating the expression
     * @return the integer result of interpreting the expression based on the context
     */
    int interpret(ScoreContext context);
}
