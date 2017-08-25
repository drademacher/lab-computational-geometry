package lions_in_plane.visualization;

import javafx.animation.Transition;
import javafx.scene.paint.Color;
import lions_in_plane.core.CoreController;
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
    private ArrayList<ArrayList<Point>> allPaths;
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
            calcAllPaths();
            for (Lion lion : lions) {
                lion.getShape().setVisible(false);
            }
        }
    }

    private void reset() {
        this.men = new ArrayList<>();
        this.lions = new ArrayList<>();
        this.manPoint = null;
        this.allLionPoints = new ArrayList<>();
        this.allPaths = new ArrayList<>();
        this.pathCount = 1;
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
        calcAllPaths();
    }

    @Override
    public void setDefaultGraph2() {
        super.setDefaultGraph2();
        calcAllPaths();

    }

    @Override
    public void setDefaultGraph3() {
        super.setDefaultGraph3();
        calcAllPaths();
    }

    public void setRandomConfiguration() {
        super.setRandomConfiguration();
        lions.forEach(lion -> System.out.print(lion.getPosition() + ", "));
        System.out.println();
        calcAllPaths();
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
            update();
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

    private void update() {
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

        if (pathCount >= allPaths.size()) {
            return false;
        }

        if (pathStoneCount == 0) {
            if (pathCount == 1) {
                new PolygonalPath(allPaths.get(0), Color.BLUE);
            }

            new PolygonalPath(allPaths.get(pathCount), Color.RED);
            lions.get(pathCount - 1).getShape().setVisible(true);

        }


        for (int i = 0; i <= pathCount; i++) {
            if (i == 0) {
                manPoint.getShape().setCenterX(allPaths.get(i).get(pathStoneCount).getX());
                manPoint.getShape().setCenterY(allPaths.get(i).get(pathStoneCount).getY());
            } else {
                lions.get(i - 1).getShape().setCenterX(allPaths.get(i).get(pathStoneCount).getX());
                lions.get(i - 1).getShape().setCenterY(allPaths.get(i).get(pathStoneCount).getY());
            }


//            Path path = new Path();
//            path.getElements().add(new MoveTo(allPaths.get(pathCount).get(0).getX(), allPaths.get(pathCount).get(0).getY()));
//            for (int i = 1; i < allPaths.get(pathCount).size(); i++) {
//                path.getElements().add(new LineTo(allPaths.get(pathCount).get(i).getX(), allPaths.get(pathCount).get(i).getY()));
//            }
//
//
//            PathTransition pathTransition = new PathTransition();
//            pathTransition.setDuration(Duration.millis(1000));
//            pathTransition.setPath(path);
//            pathTransition.setNode(manPoint.getShape());
//
//            animations.add(new SequentialTransition(
//                    new PauseTransition(Duration.millis(300)),
//                    pathTransition
//            ));

        }

        if (allPaths.get(pathCount).size() == pathStoneCount) {
            pathStoneCount = 0;
            pathCount++;
        }

        pathStoneCount++;


//
//
//        Path path = new Path();
//        path.getElements().add(new MoveTo(allPaths.get(pathCount).get(0).getX(), allPaths.get(pathCount).get(0).getY()));
//        for (int i = 1; i < allPaths.get(pathCount).size(); i++) {
//            path.getElements().add(new LineTo(allPaths.get(pathCount).get(i).getX(), allPaths.get(pathCount).get(i).getY()));
//        }
//        // path.getElements().add(new LineTo(allPaths.get(pathCount).get(0).getX(), allPaths.get(pathCount).get(0).getY()));
//
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setDuration(Duration.millis(1000));
//        pathTransition.setPath(path);
//        pathTransition.setNode(lions.get(pathCount - 1).getShape());
//
//        animations.add(new SequentialTransition(
//                new PauseTransition(Duration.millis(300)),
//                pathTransition
//        ));
//
        if (allPaths.get(pathCount).size() == pathStoneCount) {
            pathStoneCount = 0;
            pathCount++;
        }


//
//        for (Transition ft : animations) {
//            ft.play();
//        }

        return res;
    }


    @Override
    protected ArrayList<ArrayList<Point>> calcAllPaths() {
        allPaths = super.calcAllPaths();

        Point[] newHull = new Point[allPaths.size() - 1];

        //draw lion paths (position >= 1 in list)
        if (allPaths.size() > 1) {
            for (int i = 1; i < allPaths.size(); i++) {
                newHull[i - 1] = allPaths.get(i).get(allPaths.get(i).size() - 1);
            }
        }

        // update();

        // draw man path (position == = in list)
        if (allPaths.size() > 0) {
            // new PolygonalPath(allPaths.get(0), Color.BLUE);
        }

        return allPaths;
    }
}
