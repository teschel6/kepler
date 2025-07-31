package com.teschel6.kepler;

public class Point {
    private double x;
    private double y;

    Point() {
        this.x = 0;
        this.y = 0;
    }

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double distance(Point p) {
        return Math.hypot(x - p.x, y - p.y);
    }

    Point add(Point p) {
        return new Point(this.x + p.x, this.y + p.y);
    }

    Point add(Vector v) {
        return new Point(x + v.getX(), y + v.getY());
    }

    Vector subtract(Point p) {
        return new Vector(x - p.x, y - p.y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
