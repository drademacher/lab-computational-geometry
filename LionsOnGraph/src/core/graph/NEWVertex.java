package core.graph;

import core.util.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jens on 20.06.2017.
 */
public abstract class NEWVertex {

    protected ArrayList<NEWVertex> adjacentVertices = new ArrayList<>();
    protected Point coordinates;
    protected int id;

    public NEWVertex(int id, Point coordinates){
        this.coordinates = coordinates;
        this.id = id;
    }


    public boolean registerAdjacentVertex(NEWVertex vertex){
        return this.adjacentVertices.add(vertex);
    }

    public boolean unregisterAdjacentVertex(NEWVertex vertex){
        return this.adjacentVertices.remove(vertex);
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public int getId(){
        return id;
    }

    public ArrayList<NEWVertex> getAdjacentVertices() {
        return adjacentVertices;
    }
}
