package lions_and_men.applet_plane.algorithm.plane;

import lions_and_men.util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 25.08.2017.
 */
public class AllPaths {

    public final ArrayList<Point> manPath;
    public final ArrayList<ArrayList<Point>> lionPaths;
    public final int pathSize;
    public final boolean finished;

    public AllPaths() {
        this.manPath = new ArrayList<>();
        this.lionPaths = new ArrayList<>();
        this.pathSize = 0;
        this.finished = true;
    }

    public AllPaths(ArrayList<Point> manPath, ArrayList<ArrayList<Point>> lionPaths, boolean finished) {
        this.manPath = (ArrayList<Point>) manPath.clone();
        this.lionPaths = lionPaths;
        this.pathSize = manPath.size();
        this.finished = finished;

        //just check the sizes
        for (ArrayList<Point> lionPath : this.lionPaths) {
            if (lionPath.size() != this.pathSize) {
                System.out.println("Paths sizes are not equal!");
                throw new Error("Paths sizes are not equal!");
            }
        }
    }

    @Override
    public String toString() {
        String debugString = "#####";
        debugString += "\nman: ";
        for(Point point : manPath){
            debugString += point + " ";
        }
        for(ArrayList<Point> lionPath: lionPaths){
            debugString += "\nlion: ";
            for(Point point : lionPath){
                debugString += point + " ";
            }
        }
        return debugString;
    }
}