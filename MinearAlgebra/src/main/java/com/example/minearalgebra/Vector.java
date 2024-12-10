package com.example.minearalgebra;


public class Vector implements Comparable<Vector>{
    private double x;
    private double y;
    private double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector add(Vector other){
        return new Vector(this.x + other.x, this.y+other.y, this.z+other.z);
    }

    public Vector subtract(Vector other){
        return new Vector(this.x - other.x, this.y-other.y, this.z-other.z);
    }


    public double dot(Vector other){
        return (this.x*other.x)+(this.y*other.y)+(this.z*other.z);
    }

    public double length(){
        return Math.sqrt(x*x+y*y+z*z);
    }

    public Vector scalar(double constant){
        return new Vector(x*constant, y*constant, z*constant);
    }

    public Vector normalize(){
        double length = length();
        if(length == 0) throw new ArithmeticException("Cannot normalize vector with length 0");
        return new Vector(x/length, y/length, z/length);
    }

    public Vector projection(Vector v) {
        double scalar = dot(v) / Math.pow(v.length(), 2);
        return v.scalar(scalar);
    }

    public Vector mirror(Vector v) {
        Vector projectionVector = projection(v);
        return projectionVector.scalar(2).subtract(this);
    }

    @Override 
    public String toString(){
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @Override
    public int compareTo(Vector other) {
    double length1 = this.length();
    double length2 = other.length();
    return Double.compare(length1, length2);
}


}
