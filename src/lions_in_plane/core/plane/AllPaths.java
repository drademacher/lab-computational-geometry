package lions_in_plane.core.plane;

import util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 25.08.2017.
 */
public class AllPaths {

    private ArrayList<Point> manPath = new ArrayList<>();
    private ArrayList<ArrayList<Point>> lionPaths = new ArrayList<>();

    public AllPaths(ArrayList<Point> manPath, ArrayList<ArrayList<Point>> lionPaths){
        this.manPath = manPath;
        this.lionPaths = lionPaths;
    }

    public ArrayList<Point> getManPath(){
        return manPath;
    }

    public ArrayList<ArrayList<Point>> getLionPaths(){
        return lionPaths;
    }
}
