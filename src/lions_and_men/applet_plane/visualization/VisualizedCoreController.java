package lions_and_men.applet_plane.visualization;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.scene.control.Alert;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import lions_and_men.applet_plane.algorithm.CoreController;
import lions_and_men.applet_plane.algorithm.plane.AllPaths;
import lions_and_men.exceptions.WrongConfigurationException;
import lions_and_men.util.ConvexHull;
import lions_and_men.util.Point;

import java.util.ArrayList;
import java.util.List;

import static lions_and_men.applet_plane.visualization.Constants.ANIMATION_DURATION;

/**
 * Extension of the Controller Class. Using an instance of this controller over an instance of CoreController leads
 * to including animations and visuals on executing actions.
 */
public class VisualizedCoreController extends CoreController {
    private List<Lion> lions;
    private Man manPoint;
    private int pathCount;
    private int pathStoneCount;
    private int minimumPathSize;
    private boolean onGoingAnimationBlockNew;

    public VisualizedCoreController() {
        clean();
    }

    @Override
    public void setEditMode(boolean editMode) throws WrongConfigurationException {
        try {
            super.setEditMode(editMode);
        } catch (WrongConfigurationException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Your configuration is missing either a man or a set of lions.");
            alert.show();
            throw e;
        }

        if (!editMode) {
            new InvisiblePoints(new Point(minX, minY));
            new InvisiblePoints(new Point(minX, maxY));
            new InvisiblePoints(new Point(maxX, minY));
            new InvisiblePoints(new Point(maxX, maxY));

            for (Lion lion : lions) {
                lion.getShape().setVisible(false);
            }
            pathCount = 0;
        } else {
            InvisiblePoints.clear();
            clean();
        }
    }

    private void clean() {
        if (this.lions != null) {
            this.lions.forEach(Lion::clear);
        }

        this.lions = new ArrayList<>();

        for (lions_and_men.applet_plane.algorithm.plane.Lion elem : plane.getLions()) {
            Lion lion = new Lion(elem.getPosition());
            this.lions.add(lion);
        }

        if (this.manPoint != null) {
            this.manPoint.clear();
            manPoint = null;
        }
        if (plane.getMan() != null) {
            this.manPoint = new Man(plane.getMan().getPosition());
        }

        this.pathCount = 0;
        this.pathStoneCount = 0;
        this.minimumPathSize = 0;
        this.onGoingAnimationBlockNew = false;
        updateConvexHull(lions);
    }


    @Override
    public void setEmptyGraph() {
        super.setEmptyGraph();
        clean();
    }

    @Override
    public void setDefaultGraph1() {
        super.setDefaultGraph1();
    }

    @Override
    public void setDefaultGraph2() {
        super.setDefaultGraph2();
    }

    @Override
    public void setDefaultGraph3() {
        super.setDefaultGraph3();
    }

    @Override
    public void setDefaultGraph4() {
        super.setDefaultGraph4();
    }

    public void setRandomConfiguration() {
        super.setRandomConfiguration();
        lions.forEach(lion -> System.out.print(lion.getPosition() + ", "));
        System.out.println();
    }


    @Override
    public void createMan(Point coordinates) {
        super.createMan(coordinates);

        if (manPoint != null) {
            manPoint.clear();
        }

        manPoint = new Man(coordinates);
    }

    @Override
    public void removeMan(Point coordinates) {
        super.removeMan(coordinates);
        clean();
    }

    @Override
    public void createLion(Point coordinates) {
        super.createLion(coordinates);

        lions.add(new Lion(coordinates));

        updateConvexHull(lions);
    }

    @Override
    public void relocateLion(Point from, Point to) {
        super.relocateLion(from, to);
        clean();

    }

    @Override
    public void removeLion(Point coordinates) {
        super.removeLion(coordinates);
        clean();
    }


    private void updateConvexHull(List<Lion> lions) {
        ConvexHull hull = new ConvexHull(lions);
        Lion[] lionsInHull = new Lion[hull.getPoints().length];
        for (int i = 0; i < lionsInHull.length; i++) {
            for (Lion l : lions) {
                if (l.getPosition().equals(hull.getPoints()[i])) {
                    lionsInHull[i] = l;
                    break;
                }
            }
        }
        LionsPolygon.clear();
        new LionsPolygon(lionsInHull);
    }

    @Override
    public void shuffleLionOrder() {
        super.shuffleLionOrder();
        clean();
    }


    @Override
    public AllPaths simulateStep() {
        if (onGoingAnimationBlockNew) {
            return null;
        }

        AllPaths allPaths = super.simulateStep();

        // draw path stuff
        if (pathCount == 0) {
            minimumPathSize = allPaths.pathSize;
            ManPath.clear();
            for (Lion lion : lions) {
                lion.getShape().setVisible(false);
            }
        }
        onGoingAnimationBlockNew = true;
        ManPath.transfer();
        LionPath.clear();


        new ManPath(allPaths.manPath);
        for (int i = 0; i <= pathCount; i++) {

            new LionPath(allPaths.lionPaths.get(i));
        }

        lions.get(pathCount).getShape().setVisible(true);


        ParallelTransition fullTransition = new ParallelTransition();

        // man transition
        Path manPath = new Path();
        manPath.getElements().add(new MoveTo(manPoint.getPosition().getX(), manPoint.getPosition().getY()));
        for (int i = 0; i < allPaths.pathSize; i++) {
            manPath.getElements().add(new LineTo(allPaths.manPath.get(i).getX(), allPaths.manPath.get(i).getY()));
        }

        PathTransition manTransition = new PathTransition();
        manTransition.setDuration(Duration.millis(ANIMATION_DURATION * allPaths.pathSize / minimumPathSize));
        manTransition.setPath(manPath);
        manTransition.setNode(manPoint.getShape());
        fullTransition.getChildren().add(manTransition);


        // lions transitions
        for (int i = 0; i <= pathCount; i++) {
            Path lionPath = new Path();
            lionPath.getElements().add(new MoveTo(lions.get(i).getPosition().getX(), lions.get(i).getPosition().getY()));
            for (int j = 0; j < allPaths.pathSize; j++) {
                lionPath.getElements().add(new LineTo(allPaths.lionPaths.get(i).get(j).getX(), allPaths.lionPaths.get(i).get(j).getY()));
            }

            PathTransition lionTransition = new PathTransition();
            lionTransition.setDuration(Duration.millis(ANIMATION_DURATION * allPaths.pathSize / minimumPathSize));
            lionTransition.setPath(lionPath);
            lionTransition.setNode(lions.get(i).getShape());
            fullTransition.getChildren().add(lionTransition);
        }

        if (pathStoneCount == 0) {
            updateConvexHull(lions.subList(0, pathCount + 1));
        }


        pathCount++;

        fullTransition.play();
        fullTransition.setOnFinished(event -> onGoingAnimationBlockNew = false);


        if (allPaths.finished) {
            fullTransition.setOnFinished(event -> {
                onGoingAnimationBlockNew = false;
                pathCount = 0;
            });

        }

        return allPaths;
    }

}
