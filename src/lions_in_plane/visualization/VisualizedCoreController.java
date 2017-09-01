package lions_in_plane.visualization;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import lions_in_plane.core.CoreController;
import lions_in_plane.core.plane.AllPaths;
import util.ConvexHull;
import util.Point;

import java.util.ArrayList;
import java.util.List;

import static lions_in_plane.visualization.Constants.ANIMATION_DURATION;
import static lions_in_plane.visualization.Constants.FADE_IN_DURATION;


public class VisualizedCoreController extends CoreController {
    private List<Lion> lions;
    private ConvexHull hull;
    private Man manPoint;
    private AllPaths allPaths;
    private int pathCount;
    private int pathStoneCount;

    public VisualizedCoreController() {
        reset();
    }

    @Override
    public void setEditMode(boolean editMode) {
        super.setEditMode(editMode);

        if (!editMode) {
//            calcAllPaths(1);
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

    public void reset() {
        if (this.lions != null) {
            this.lions.forEach(Lion::clear);
        }
        this.lions = new ArrayList<>();
        for (lions_in_plane.core.plane.Lion lion : plane.getLions()) {
            this.lions.add(new Lion(lion.getPosition()));
        }
        if (this.manPoint != null) {
            this.manPoint.clear();
        }
        if (plane.getMan() != null) {
            this.manPoint = new Man(plane.getMan().getPosition());
        }

        this.pathCount = 0;
        this.pathStoneCount = 0;
        update(lions);
    }


    @Override
    public void setEmptyGraph() {
        reset();
    }

    @Override
    public void setDefaultGraph1() {
        super.setDefaultGraph1();
//        calcAllPaths(1);
    }

    @Override
    public void setDefaultGraph2() {
        super.setDefaultGraph2();
//        calcAllPaths(1);

    }

    @Override
    public void setDefaultGraph3() {
        super.setDefaultGraph3();
//        calcAllPaths(1);
    }

    public void setRandomConfiguration() {
        super.setRandomConfiguration();
        lions.forEach(lion -> System.out.print(lion.getPosition() + ", "));
        System.out.println();
//        calcAllPaths(1);
    }


    @Override
    public void createMan(Point coordinates) {
        super.createMan(coordinates);

        manPoint = new Man(coordinates);
    }

    @Override
    public void removeMan(Point coordinates) {
        super.removeMan(coordinates);

        manPoint.clear();
        manPoint = null;
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
    public void relocateMan(Point to) {
        super.relocateMan(to);
        manPoint.setPosition(to);
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
    public AllPaths simulateStep() {
         allPaths = super.simulateStep();

        if (allPaths.finished) {
            reset();
            return allPaths;
        }

        // draw path stuff
        ManPath.transfer();
        LionPath.clear();
        InvisiblePath.clear();
        new InvisiblePath(allPaths.manPath);
        new ManPath(allPaths.manPath);
        for (int i = 0; i <= pathCount; i++) {
            new InvisiblePath(allPaths.lionPaths.get(i));
            new LionPath(allPaths.lionPaths.get(i));
        }

        // fade in next lion
        lions.get(pathCount).getShape().setVisible(true);
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setNode(lions.get(pathCount).getShape());
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setDuration(Duration.millis(FADE_IN_DURATION));
        fadeIn.play();


        ParallelTransition allPathTransition = new ParallelTransition();

        // man transition
        Path manPath = new Path();
        manPath.getElements().add(new MoveTo(manPoint.getPosition().getX(), manPoint.getPosition().getY()));
        for (int i = 0; i < allPaths.pathSize; i++) {
            manPath.getElements().add(new LineTo(allPaths.manPath.get(i).getX(), allPaths.manPath.get(i).getY()));
        }

        PathTransition manTransition = new PathTransition();
        manTransition.setDuration(Duration.millis(ANIMATION_DURATION));
        manTransition.setPath(manPath);
        manTransition.setNode(manPoint.getShape());
        allPathTransition.getChildren().add(manTransition);


        // lions transitions
        for (int i = 0; i <= pathCount; i++) {
            Path lionPath = new Path();
            lionPath.getElements().add(new MoveTo(lions.get(i).getPosition().getX(), lions.get(i).getPosition().getY()));
            for (int j = 0; j < allPaths.pathSize; j++) {
                lionPath.getElements().add(new LineTo(allPaths.lionPaths.get(i).get(j).getX(), allPaths.lionPaths.get(i).get(j).getY()));
            }

            PathTransition lionTransition = new PathTransition();
            lionTransition.setDuration(Duration.millis(ANIMATION_DURATION));
            lionTransition.setPath(lionPath);
            lionTransition.setNode(lions.get(i).getShape());
            allPathTransition.getChildren().add(lionTransition);


//            lions.get(i).setPosition(allPaths.lionPaths.get(i).get(pathStoneCount));
        }

        if (pathStoneCount == 0) {
            update(lions.subList(0, pathCount + 1));
        }




        pathCount++;
//        allPaths = simulateStep();

        SequentialTransition fullTransition = new SequentialTransition();
        fullTransition.getChildren().addAll(fadeIn, allPathTransition);
        fullTransition.play();

        return allPaths;
    }

}
