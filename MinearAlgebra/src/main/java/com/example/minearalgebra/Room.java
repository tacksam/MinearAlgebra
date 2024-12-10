package com.example.minearalgebra;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Room {

    private final Vector width;
    private final Vector height;
    private final Vector depth;
    private List<Vector> vectors = new ArrayList<>();
    private Map<String, Vector> vectorRegistry = new HashMap<>();

    public Room(Vector width, Vector height, Vector depth){
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public double getX(){
        return width.getX();
    }
    public double getY(){
        return height.getY();
    }
    public double getZ(){
        return depth.getZ();
    }

    public Vector getWidth(){
        return width;
    }
    public Vector getHeight(){
        return height;
    }public Vector getDepth(){
        return depth;
    }
    public boolean isWithinBounds(Vector v) {
        return v.getX() >= 0 && v.getX() <= getX() &&
               v.getY() >= 0 && v.getY() <= getY() &&
               v.getZ() >= 0 && v.getZ() <= getZ();
    }

    public boolean addVector(Vector v, String string) {
        if (isWithinBounds(v)) {
            vectorRegistry.put(string, v);
            System.out.println("Added vector: " + v.toString());
            return true;
        }
        System.out.println("Vector: " + v.toString() + " is out of bounds");
        return false;
    }   

    public boolean removeVector(String string) {
        if (vectorRegistry.get(string) == null) {
            System.out.println("Removed vector: " + string);
            return true;
        }
        System.out.println("Vector not found: " + string);
        return false;
    }

    public Map<String, Vector> getVectorMap(){
        return vectorRegistry;
    }

    public void clearRoom(){
        vectorRegistry.clear();
    }


    public void printVectors() {
        System.out.println("Vectors in the room:");
        for (Vector v : vectors) {
            System.out.println(" - " + v);
        }
    }

    @Override
    public String toString() {
        return "Room Dimensions: " + width + ", " + height + ", " + depth +
               " || Number of Vectors: " + vectors.size();
    }

}
