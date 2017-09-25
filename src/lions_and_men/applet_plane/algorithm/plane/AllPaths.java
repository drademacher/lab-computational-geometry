package lions_and_men.applet_plane.algorithm.plane;

import lions_and_men.util.Point;

import java.util.ArrayList;


public class AllPaths {

    public final ArrayList<Point> manPath;
    public final ArrayList<ArrayList<Point>> lionPaths;
    public final int pathSize;
    public final boolean finished;

    public AllPaths(ArrayList<Point> manPath, ArrayList<ArrayList<Point>> lionPaths, boolean finished) {
        this.manPath = (ArrayList<Point>) manPath.clone();
        this.lionPaths = lionPaths;
        this.pathSize = manPath.size();
        this.finished = finished;

        //just check the sizes
        for (ArrayList<Point> lionPath : this.lionPaths) {
            if (lionPath.size() != this.pathSize) {
                throw new Error("Paths sizes are not equal!");
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder debugString = new StringBuilder("#####");
        debugString.append("\nman: ");
        for (Point point : manPath) {
            debugString.append(point).append(" ");
        }
        for (ArrayList<Point> lionPath : lionPaths) {
            debugString.append("\nlion: ");
            for (Point point : lionPath) {
                debugString.append(point).append(" ");
            }
        }
        return debugString.toString();
    }
}
