package com.example.projectfour;

public class Sphere {
    Vector centre;
    private double radius;
    private Vector color;

    public Sphere(double radius, Vector centre, Vector color) {
        this.radius = radius;
        this.centre = centre;
        this.color = color;

    }
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Vector getColor() {
        return color;
    }

    public void setColor(Vector color) {
        this.color = color;
    }

    public Vector getCentre() {
        return centre;
    }

    public void setCentre(Vector centre) {
        this.centre = centre;
    }

    @Override
    public String toString() {
        return "SphereClass{" +
                "radius=" + radius +
                ", color=" + color +
                ", centre=" + centre +
                '}';
    }
}