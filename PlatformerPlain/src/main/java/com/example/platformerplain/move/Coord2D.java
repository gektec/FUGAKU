package com.example.platformerplain.move;

public class Coord2D {
    private float x;
    private float y;

    public Coord2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Coord2D() {
        this.x = 0;
        this.y = 0;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float[] get() {
        return new float[] {x, y};
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    public void reduce(float dx, float dy) {
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

    public void smoothReduce(float ax, float ay, float tx, float ty) {
        float ratioX = 0, ratioY = 0;
        if(tx != 0) ratioX = (float) Math.min(1.5, (Math.abs(x / tx)) + 0.25);
        if(ty != 0) ratioY = (float) Math.min(1.5, (Math.abs(y / ty)) + 0.25);
        reduce(ax * ratioX, ay * ratioY);
    }

    public void smoothMinus(float ax, float ay, float tx, float ty) {
        float ratioX = 0, ratioY = 0;
        if(tx != 0) ratioX = (float) Math.min(1.5, (Math.abs(x / tx)) + 0.5);
        if(ty != 0) ratioY = (float) Math.min(1.5, (Math.abs(y / ty)) + 0.5);
        add(ax * ratioX, ay * ratioY);
    }

    public void smoothAdd(float ax, float ay, float tx, float ty) {
        float ratioX = 0, ratioY = 0;
        if(tx != 0) ratioX = (float) Math.min(1.5, (Math.abs((x-tx) / tx)) + 0.25);
        if(ty != 0) ratioY = (float) Math.min(1.5, (Math.abs((y-ty) / ty)) + 0.25);
        add(ax * ratioX, ay * ratioY);
    }
}
