package lions_in_plane.visualization;

import javafx.animation.Transition;
import lions_in_plane.core.CoreController;
import lions_in_plane.core.plane.AllPaths;
import util.ConvexHull;
import util.Point;

import java.util.ArrayList;
import java.util.List;


public class VisualizedCoreController extends CoreController {
    private ArrayList<Man> men;
    private List<Lion> lions;
    private ConvexHull hull;
    private Man manPoint;
    private ArrayList<Lion> allLionPoints;
    private AllPaths allPaths;
    private int pathCount;
    private int pathStoneCount;
    private ArrayList<Transition> animations;

    public VisualizedCoreController() {
        reset();
    }

    @Override
    public void setEditMode(boolean editMode) {
        super.setEditMode(editMode);

        if (!editMode) {
            calcAllPaths(1);
            for (Lion lion : lions) {
                new InvisiblePoints(lion.getPosition());
                lion.getShape().setVisible(false);
            }
        } else {
            for (Lion lion : lions) {
                lion.getShape().setVisible(true);
            }
        }
    }

    private void reset() {
        this.men = new ArrayList<>();
        this.lions = new ArrayList<>();
        this.manPoint = null;
        this.allLionPoints = new ArrayList<>();
        this.pathCount = 0;
        this.pathStoneCount = 0;
        this.animations = new ArrayList<>();
//        this.hull = new Point[0];
    }


    @Override
    public void setEmptyGraph() {
        reset();
    }

    @Override
    public void setDefaultGraph1() {
        super.setDefaultGraph1();
        calcAllPaths(1);
    }

    @Override
    public void setDefaultGraph2() {
        super.setDefaultGraph2();
        calcAllPaths(1);

    }

    @Override
    public void setDefaultGraph3() {
        super.setDefaultGraph3();
        calcAllPaths(1);
    }

    public void setRandomConfiguration() {
        super.setRandomConfiguration();
        lions.forEach(lion -> System.out.print(lion.getPosition() + ", "));
        System.out.println();
        calcAllPaths(1);
    }


    @Override
    public void createMan(Point coordinates) {
        super.createMan(coordinates);

        manPoint = new Man(coordinates);
        men.add(manPoint);
//        // TODO: total debug
//        ArrayList<Point> path = new ArrayList<>(Arrays.asList(hull));
//        new PolygonalPath(path, Color.BLACK);
    }

    @Override
    public void removeMan(Point coordinates) {
        super.removeMan(coordinates);

        men.stream().filter(man -> man.getPosition() == coordinates).forEach(Man::clear);
        men.removeIf(man -> man.getPosition() == coordinates);
    }

    @Override
    public void createLion(Point coordinates) {
        super.createLion(coordinates);
        lions.add(new Lion(coordinates));

        if (hull == null || !hull.insideHull(coordinates)) {
            update(lions);
        }
    }

    @Override
    public void removeLion(Point coordinates) {
        super.removeLion(coordinates);

        lions.stream().filter(lion -> lion.getPosition().equals(coordinates)).forEach(Lion::clear);
        lions.removeIf(lion -> lion.getPosition().equals(coordinates));

//        update();
    }

    @Override
    public void relocateMan(Point from, Point to) {
        super.relocateMan(from, to);

        men.stream().filter(man -> man.getPosition() == from).forEach(man -> man.setPosition(to));
    }

    @Override
    public void relocateLion(Point from, Point to) {
        super.relocateLion(from, to);

        lions.stream().filter(e -> e.getPosition().equals(from)).forEach(e -> e.setPosition(to));

//        update();

    }

    private void update(List<Lion> lions) {
        hull = new ConvexHull(lions);
        Lion[] lionsInHull = new Lion[hull.getPoints().length];
        for (int i = 0; i < lionsInHull.length; i++) {
            for (Lion l : lions) {
                if (l.getPosition().equals(hull.getPoints()[i])) {
                    lionsInHull[i] = l;
                    break;
                }
            }
            // System.out.println(lionsInHull[i]);
        }
        LionsPolygon.clear();
        new LionsPolygon(lionsInHull);
    }

    @Override
    public void setLionRange(Point coordinates, double range) {
        // TODO: IMPLEMENT THIS
    }


    @Override
    public boolean simulateStep() {
        boolean res = super.simulateStep();


        if (pathCount >= lions.size()) {
            return false;
        }

        if (pathStoneCount == 0) {
            ManPath.clear();
            LionPath.clear();
            InvisiblePath.clear();
            new InvisiblePath(allPaths.manPath);
            new ManPath(allPaths.manPath);
            for (int i = 0; i <= pathCount; i++) {
                new InvisiblePath(allPaths.lionPaths.get(i));
                new LionPath(allPaths.lionPaths.get(i));
            }
            lions.get(pathCount).getShape().setVisible(true);
        }


        for (int i = 0; i <= pathCount; i++) {
            if (i == 0) {
                manPoint.setPosition(allPaths.manPath.get(pathStoneCount));
            }

            lions.get(i).setPosition(allPaths.lionPaths.get(i).get(pathStoneCount));
        }

        if (pathStoneCount == 0) {
            update(lions.subList(0, pathCount + 1));
        }

        pathStoneCount++;

        if (allPaths.pathSize == pathStoneCount) {
            pathStoneCount = 0;
            pathCount++;
            if (pathCount >= lions.size()) {
                return false;
            }
            allPaths = calcAllPaths(pathCount + 1);
//            update();
        }

        return res;
    }


    @Override
    protected AllPaths calcAllPaths(int maxInductionsStep) {
        //TODO allPathsObject vs allPaths
        allPaths = super.calcAllPaths(maxInductionsStep);

//        Point[] newHull = new Point[allPaths.pathSize];
//
//        //draw lion paths (position >= 1 in list)
//        if (allPaths.pathSize > 1) {
//            for (int i = 1; i < allPaths.pathSize; i++) {
//                newHull[i - 1] = allPaths.lionPaths.get(i).get(allPaths.lionPaths.get(i).size() - 1);
//            }
//        }
//
//        // update();
//
//        // draw man path (position == = in list)
//        if (allPaths.pathSize > 0) {
//            // new PolygonalPath(allPaths.get(0), Color.BLUE);
//        }

        return allPaths;
    }
}
