package com.example.platformerplain.move;

/**
 * Represents a 2D coordinate with x and y components.
 * Provides methods for manipulating and retrieving the coordinate values.
 *
 * @author Changyu Li
 * @date 2024/12/15
 */
public class Coord2D {
    private double x;
    private double y;

    /**
     * Creates a new coordinate with specified x and y values.
     *
     * @param x The x-coordinate value.
     * @param y The y-coordinate value.
     */
    public Coord2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new coordinate at the origin (0, 0).
     */
    public Coord2D() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Gets the x-coordinate value.
     *
     * @return The x-coordinate value.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate value.
     *
     * @param x The new x-coordinate value.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate value.
     *
     * @return The y-coordinate value.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate value.
     *
     * @param y The new y-coordinate value.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns the coordinates as an array of doubles.
     *
     * @return An array containing the x and y coordinates.
     */
    public double[] get() {
        return new double[] {x, y};
    }

    /**
     * Sets the coordinates to the specified x and y values.
     *
     * @param x The new x-coordinate value.
     * @param y The new y-coordinate value.
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds the specified increments to the x and y coordinates.
     *
     * @param dx The increment to add to the x-coordinate.
     * @param dy The increment to add to the y-coordinate.
     */
    public void add(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Reduces the x and y coordinates by the specified amounts, ensuring they do not go below zero.
     *
     * @param dx The amount to reduce from the x-coordinate.
     * @param dy The amount to reduce from the y-coordinate.
     */
    public void reduce(double dx, double dy) {
        dx = Math.abs(dx);
        dy = Math.abs(dy);
        if (x > 0) {
            x = Math.max(0, x - dx);
        } else {
            x = Math.min(0, x + dx);
        }

        if (y > 0) {
            y = Math.max(0, y - dy);
        } else {
            y = Math.min(0, y + dy);
        }
    }

    /**
     * Smoothly reduces the coordinates based on acceleration and target values.
     * It will reduce more if the target is further away.
     *
     *
     * @param ax The acceleration to apply to the x-coordinate.
     * @param ay The acceleration to apply to the y-coordinate.
     * @param tx The target x-coordinate for the smooth reduction.
     * @param ty The target y-coordinate for the smooth reduction.
     */
    public void smoothReduce(double ax, double ay, double tx, double ty) {
        double ratioX = 0, ratioY = 0;
        if(tx != 0) ratioX = Math.min(1.5, (Math.abs(x / tx)) + 0.25);
        if(ty != 0) ratioY = Math.min(1.5, (Math.abs(y / ty)) + 0.25);
        reduce(ax * ratioX, ay * ratioY);
    }

    /**
     * Smoothly adds to the coordinates based on acceleration and target values.
     * It will minus less when closer to target.
     * Used for gravity, create a longer stay on highest point.
     *
     * @param ax The acceleration to apply to the x-coordinate.
     * @param ay The acceleration to apply to the y-coordinate.
     * @param tx The target x-coordinate for the smooth addition.
     * @param ty The target y-coordinate for the smooth addition.
     */
    public void smoothMinus(double ax, double ay, double tx, double ty) {
        double ratioX = 0, ratioY = 0;
        if(tx != 0) ratioX = Math.min(1.5, (Math.abs(x / tx)) + 0.5);
        if(ty != 0) ratioY = Math.min(1.5, (Math.abs(y / ty)) + 0.5);
        add(ax * ratioX, ay * ratioY);
    }

    /**
     * Smoothly adds to the coordinates based on acceleration and target values.
     * It will add more if the target is further away.
     * Used for acceleration.
     *
     * @param ax The acceleration to apply to the x-coordinate.
     * @param ay The acceleration to apply to the y-coordinate.
     * @param tx The target x-coordinate for the smooth addition.
     * @param ty The target y-coordinate for the smooth addition.
     */
    public void smoothAdd(double ax, double ay, double tx, double ty) {
        double ratioX = 0, ratioY = 0;
        if(tx != 0) ratioX = Math.min(1.5, (Math.abs((x-tx) / tx)) + 0.25);
        if(ty != 0) ratioY = Math.min(1.5, (Math.abs((y-ty) / ty)) + 0.25);
        add(ax * ratioX, ay * ratioY);
    }
}
