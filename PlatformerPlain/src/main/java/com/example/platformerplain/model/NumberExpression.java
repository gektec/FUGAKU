package com.example.platformerplain.model;

// NumberExpression class, representing a numeric value
public class NumberExpression implements Expression {
    private int number;

    public NumberExpression(int number) {
        this.number = number;
    }

    @Override
    public int interpret(ScoreContext context) {
        return number;
    }
}
