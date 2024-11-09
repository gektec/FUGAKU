package com.example.platformerplain;

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
}