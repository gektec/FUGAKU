package com.example.platformerplain.move;

public class Coord2D {
    private int x;
    private int y;

    public Coord2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coord2D() {
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void reduce(int dx, int dy) {
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
}
