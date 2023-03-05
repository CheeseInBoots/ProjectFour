package com.example.projectfour;

import java.util.ArrayList;

public class RayTracer {

    Vector v;
    double discriminate;
    double t;
    private Vector direction;
    Vector light;
    static ArrayList<Sphere> listOfSpheres;
    private Vector origin;

    public RayTracer() {
        this.listOfSpheres = new ArrayList<>();
        this.origin = new Vector(0, 0, 0);
        this.direction = new Vector(0, 0, 1);
        this.light = new Vector(0, 300, -400);
        this.v = new Vector(0, 0 , 0);
    }
    /**
     * this is a method to call to calculate the t value
     *
     * @param sphere
     * @return return the double value the intersection distance to see if it hits the sphere
     */
    public double rayInteractionsDistance(Sphere sphere) {
        setV(getOrigin().sub(sphere.getCentre()));
        Double a = getDirection().dot(direction);
        Double b = 2 * getV().dot(direction);
        Double c = getV().dot(getV()) - sphere.getRadius() * sphere.getRadius();
        discriminate = b * b - 4 * a * c;
        if (discriminate < 0) {
            return -1;
        } else {

            t = (-b - Math.sqrt(discriminate)) / 2 * a;
            if (t < 0)
                t = (-b + Math.sqrt(discriminate)) / 2 * a;
            if (t < 0) {

                return -1;
            }
            return t;
        }
    }
    public Vector ambientShading(Sphere sphere, RayTracer ray) {
        Vector intersection = ray.getOrigin().add(ray.getDirection().mul(ray.getT()));
        Vector normal = intersection.sub(sphere.getCentre());
        normal.normalise();
        Vector lv = ray.light.sub(intersection);
        lv.normalise();
        double dotProduct = lv.dot(normal);
        if (dotProduct < 0) dotProduct = 0;
        if (dotProduct > 1) dotProduct = 1;
        Vector col = sphere.getColor();
        col = col.mul(dotProduct * .7).add(col.mul(.3));
        return col;

    }

    /**
     * Get the sphere with the closest rayIntersectioinvalue
     * set sphere to null. then we loop thorugh the arraylist, we intersect the first sphere and get its distance. if the
     * distance is  > 0, then its hit and make sure its less then the max(arbitary value)
     * we set the null sphere to the currentsphere and we set the max value to current value,we do that for sphere
     * to get the closest sphere
     * @return Sphere
     */
    public Sphere closestSphereInArray() {
        double maxDistance = Double.MAX_VALUE; // we find the max number to compare and make it the new distance
        Sphere getNewSphere = null; // instance the sphere into null value;
        for (Sphere currentSphere : listOfSpheres) {
            double hitDistance = rayInteractionsDistance(currentSphere);// The intersection value
            if (hitDistance > 0 && hitDistance < maxDistance) {
                getNewSphere = currentSphere;// we return the closest spheres
                maxDistance = hitDistance; //we change the vlaue of max sphere to previous sphere

            }
        }
        return getNewSphere;
    }
    /**
     * void Method to create new spheres and it is stored in the raytrace class  to the rayTrace arraylist of spheres
     *
     * @param radius
     * @param centre
     * @param colour
     */
    public void createNewSpheres(double radius, Vector centre, Vector colour) {
        listOfSpheres.add(new Sphere(radius, centre, colour));

    }

    public ArrayList<Sphere> getSpheres() {
        return listOfSpheres;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public Vector getOrigin() {
        return origin;
    }

    public void setOrigin(Vector origin) {
        this.origin = origin;
    }
    public Vector getV() {
        return v;
    }

    public void setV(Vector v) {
        this.v = v;
    }
    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }




}