package com.example.platformerplain.move;

public class Coord2D {
    private double x;
    private double y;

    public Coord2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coord2D() {
        this.x = 0;
        this.y = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double[] get() {
        return new double[] {x, y};
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

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

    public void smoothReduce(double ax, double ay, double tx, double ty) {
        double ratioX = 0, ratioY = 0;
        if(tx != 0) ratioX = Math.min(1.5, (Math.abs(x / tx)) + 0.25);
        if(ty != 0) ratioY = Math.min(1.5, (Math.abs(y / ty)) + 0.25);
        reduce(ax * ratioX, ay * ratioY);
    }

    public void smoothMinus(double ax, double ay, double tx, double ty) {
        double ratioX = 0, ratioY = 0;
        if(tx != 0) ratioX = Math.min(1.5, (Math.abs(x / tx)) + 0.5);
        if(ty != 0) ratioY = Math.min(1.5, (Math.abs(y / ty)) + 0.5);
        add(ax * ratioX, ay * ratioY);
    }

    public void smoothAdd(double ax, double ay, double tx, double ty) {
        double ratioX = 0, ratioY = 0;
        if(tx != 0) ratioX = Math.min(1.5, (Math.abs((x-tx) / tx)) + 0.25);
        if(ty != 0) ratioY = Math.min(1.5, (Math.abs((y-ty) / ty)) + 0.25);
        add(ax * ratioX, ay * ratioY);
    }
}
