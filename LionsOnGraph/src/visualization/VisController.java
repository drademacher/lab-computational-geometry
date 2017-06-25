package visualization;

import graph.GraphController;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.Point;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class VisController implements Initializable {

    // TODO: rename all the names, since they are not even close to be consistent
    // some end with MenuItem, some have prefix "view", or postfix "toggleGroup"

    @FXML
    private MenuItem emptyMapMenuItem, graph1MenuItem, graph2MenuItem, graph3MenuItem, graph4MenuItem, graph5MenuItem, randomGraphMenuItem, openMapMenuItem, saveMapMenuItem;


    @FXML
    private ZoomScrollPane zoomScrollPane;


    @FXML
    private Button animationToggleButton, stepAnimationButton, playAnimationButton, stopAnimationButton;


    private BooleanProperty editMode, activePlaying;
    private AnimationTimer animationTimer;
    private int passedTicks = 0;
    private double lastNanoTime = System.nanoTime();
    private double time = 0;
    private int tickAccount = 0;
    final private int TICKS_PER_STEP = 20;



    private GraphController coreController = new GraphController();


    private Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stage = (Stage) resources.getObject(null);

        initAnimationTimer();
        initGraphButtons();
        initButtonBar();
        initContextMenu();
    }

    private void initButtonBar() {
        editMode = new SimpleBooleanProperty(true);
        activePlaying  = new SimpleBooleanProperty(false);
        editMode.addListener((observable, oldValue, newValue) -> {
            this.coreController.setEditMode(newValue);
        });

        animationToggleButton.setOnMouseClicked(event -> {
            if (editMode.getValue()) {
                editMode.set(false);
                animationToggleButton.setText("Edit Mode");
            } else {
                editMode.set(true);
                animationToggleButton.setText("Play Mode");
            }
        });

        stepAnimationButton.disableProperty().bind(editMode.or(activePlaying));
        playAnimationButton.disableProperty().bind(editMode.or(activePlaying));
        stopAnimationButton.disableProperty().bind(editMode.or(activePlaying.not()));

        stepAnimationButton.setOnMouseClicked(event -> {
            this.coreController.simulateStep();
        });

        playAnimationButton.setOnMouseClicked(event -> {
            animationTimer.start();
            activePlaying.set(true);
        });

        stopAnimationButton.setOnMouseClicked(event -> {
            animationTimer.stop();
            activePlaying.set(false);
        });
    }

    private void initContextMenu() {
        zoomScrollPane.getGround().setOnMouseClicked(event1 -> {
            System.out.println("ground " + new Point((int) event1.getX(), (int) event1.getY()));
//            System.out.println("scene " + new Point((int) event1.getSceneX(), (int) event1.getSceneY()));
//            System.out.println(zoomScrollPane.getGround().localToParent(new Point2D(0, 0)));

        });
        zoomScrollPane.setOnMouseClicked(event -> {
            Bounds subValues = zoomScrollPane.getGround().localToScene(zoomScrollPane.getGround().getBoundsInLocal());
            Bounds addValues = zoomScrollPane.localToScene(zoomScrollPane.getGround().getBoundsInLocal());
//            System.out.println("bounds " + new Point((int)  addValues.getMinX(), (int)  addValues.getMinY()));

            System.out.println("new " + new Point((int) (event.getX() - subValues.getMinX() + addValues.getMinX()), (int) (event.getY() - subValues.getMinY() + addValues.getMinY())));
        });

        zoomScrollPane.getGround().setOnContextMenuRequested(event1 -> {
            if (!this.coreController.isEditMode())
                return;

            final ContextMenu contextMenu = new ContextMenu();
            MenuItem item1 = new MenuItem("Add Node");
            item1.setOnAction(event2 -> {
                coreController.createVertex(new Point((int) event1.getX(), (int) event1.getY()));
            });
            MenuItem item2 = new MenuItem("Close");

            contextMenu.getItems().addAll(item1, item2);
            contextMenu.show(zoomScrollPane.getGround(), event1.getScreenX(), event1.getScreenY());
        });
    }

    private void initAnimationTimer() {
        final double fps = 60.0;
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                time += (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;
                passedTicks = (int) Math.floor(time * fps);
                time -= passedTicks / fps;
                if (passedTicks >= 1) {
                    // TODO: maybe make a tick counter, so just every 10 ticks the steps are done!?
                    tickAccount += 1;
                    if (tickAccount >= TICKS_PER_STEP) {
                        tickAccount -= TICKS_PER_STEP;
                        coreController.simulateStep();
                    }
                }
            }
        };
    }


    private void initGraphButtons() {
        emptyMapMenuItem.setOnAction(event -> {

            this.zoomScrollPane.clear();
            coreController.setEmptyGraph();
//            this.graphHolder.setGraph(coreController.setEmptyGraph());
//            this.graphHolder.setState(coreController.getState());
//            this.editGraphModeButton.setSelected(true);
        });

        graph1MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            coreController.setDefaultGraph1();
//            this.graphHolder.setGraph(coreController.setDefaultGraph1());
//            this.graphHolder.setState(coreController.getState());
//            this.editGraphModeButton.setSelected(true);
        });

        graph2MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            coreController.setDefaultGraph2();
//            this.graphHolder.setGraph(coreController.setDefaultGraph2());
//            this.graphHolder.setState(coreController.getState());
//            this.editGraphModeButton.setSelected(true);
        });

        graph3MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            coreController.setDefaultGraph3();
//            this.graphHolder.setGraph();
//            this.graphHolder.setState(coreController.getState());
//            this.editGraphModeButton.setSelected(true);
        });

        graph4MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
//            this.graphHolder.setGraph(coreController.setDefaultGraph4());
//            this.graphHolder.setState(coreController.getState());
//            this.editGraphModeButton.setSelected(true);
        });

        graph5MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
//            this.graphHolder.setGraph(coreController.setDefaultGraph5());
//            this.graphHolder.setState(coreController.getState());
//            this.editGraphModeButton.setSelected(true);
        });

        randomGraphMenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
//            this.graphHolder.setGraph(coreController.setRandomGraph());
//            this.graphHolder.setState(coreController.getState());
//            this.editGraphModeButton.setSelected(true);
        });

        openMapMenuItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Map Files", "*.map"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(this.stage);
            if (selectedFile != null) {
                try {
                    coreController.setGraphFromFile(selectedFile);
//                    this.graphHolder.setGraph(coreController.setGraphFromFile(selectedFile));
                } catch (Exception e) {
                    e.printStackTrace();
                    //Todo: openMapMenuItem.setOnAction - nice exception handling!!!
                }
            }
        });

        saveMapMenuItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Current Map");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Map Files", "*.map"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showSaveDialog(this.stage);
            if (selectedFile != null) {
                this.coreController.saveGraphToFile(selectedFile);
            }
        });

    }

}
