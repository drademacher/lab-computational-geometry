package lions_and_men.applet_two_plane.visualization;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.scene.control.Alert;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import lions_and_men.applet_two_plane.core.CoreController;
import lions_and_men.applet_two_plane.core.plane.AllPaths;
import lions_and_men.util.ConvexHull;
import lions_and_men.util.Point;
import lions_and_men.util.WrongConfigurationException;

import java.util.ArrayList;
import java.util.List;

import static lions_and_men.applet_two_plane.visualization.Constants.ANIMATION_DURATION;


public class VisualizedCoreController extends CoreController {
    private List<Lion> lions;
    private ConvexHull hull;
    private Man manPoint;
    private AllPaths allPaths;
    private int pathCount;
    private int pathStoneCount;
    private int minimumPathSize;
    private boolean onGoingAnimationBlockNew;

    public VisualizedCoreController() {
        freshInitialization();
    }

    @Override
    public void setEditMode(boolean editMode) {
        try {
            super.setEditMode(editMode);
        } catch (WrongConfigurationException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Your configuration is missing either a man or a set of lions.");
            alert.showAndWait();
            return;
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
            freshInitialization();
        }
    }

    public void freshInitialization() {
        if (this.lions != null) {
            this.lions.forEach(Lion::clear);
        }

        this.lions = new ArrayList<>();

        for (lions_and_men.applet_two_plane.core.plane.Lion elem : plane.getLions()) {
            Lion lion = new Lion(elem.getPosition());
            this.lions.add(lion);
        }

        if (this.manPoint != null) {
            this.manPoint.clear();
            manPoint = null;
        }
        if (plane.getMan() != null) {
            // TODO: einkommentieren
            this.manPoint = new Man(plane.getMan().getPosition());
        }

        this.pathCount = 0;
        this.pathStoneCount = 0;
        this.minimumPathSize = 0;
        this.onGoingAnimationBlockNew = false;
        update(lions);
    }


    @Override
    public void setEmptyGraph() {
        super.setEmptyGraph();
        freshInitialization();
    }

    @Override
    public void setDefaultGraph1() {
        super.setDefaultGraph1();
//        calcAllPaths(1);
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

    @Override
    public void setDefaultGraph5() {
        super.setDefaultGraph5();
    }

    public void setRandomConfiguration() {
        super.setRandomConfiguration();
        lions.forEach(lion -> System.out.print(lion.getPosition() + ", "));
        System.out.println();
    }


    @Override
    public void createMan(Point coordinates) {
        System.out.println("create Man - visual");
        super.createMan(coordinates);

//        if(manPoint != null){
//            return;
//        }
//        manPoint = new Man(coordinates);

        freshInitialization();
    }

    @Override
    public void relocateMan(Point to) {
        super.relocateMan(to);
        manPoint.setPosition(to);
//        freshInitialization();
    }

    @Override
    public void removeMan(Point coordinates) {
        super.removeMan(coordinates);

        manPoint.clear();
        manPoint = null;

        freshInitialization();
    }

    @Override
    public void createLion(Point coordinates) {
        super.createLion(coordinates);

        lions.add(new Lion(coordinates));

        update(lions);
    }

    @Override
    public void relocateLion(Point from, Point to) {
        super.relocateLion(from, to);

        lions.stream().filter(e -> e.getPosition().equals(from)).forEach(e -> e.setPosition(to));

        update(lions);

    }

    @Override
    public void removeLion(Point coordinates) {
        super.removeLion(coordinates);

        lions.stream().filter(lion -> lion.getPosition().equals(coordinates)).forEach(Lion::clear);
        lions.removeIf(lion -> lion.getPosition().equals(coordinates));

        freshInitialization();
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
    public void shuffleLionOrder() {
        super.shuffleLionOrder();
        freshInitialization();
    }


    @Override
    public AllPaths simulateStep() {

        if (onGoingAnimationBlockNew) {
            return null;
        }

        allPaths = super.simulateStep();

        // draw path stuff
        if (pathCount == 0) {
            minimumPathSize = allPaths.pathSize;
            ManPath.clear();
//            freshInitialization();
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

        // fade in next lion
        lions.get(pathCount).getShape().setVisible(true);
//        FadeTransition fadeIn = new FadeTransition();
//        fadeIn.setNode(lions.get(pathCount).getShape());
//        fadeIn.setFromValue(0.0);
//        fadeIn.setToValue(1.0);
//        fadeIn.setDuration(Duration.millis(ANIMATION_DURATION * 0.2));


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


//            lions.get(i).setPosition(allPaths.lionPaths.get(i).get(pathStoneCount));
        }

        if (pathStoneCount == 0) {
            update(lions.subList(0, pathCount + 1));
        }


        pathCount++;
//        allPaths = simulateStep();

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
