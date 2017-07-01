package visualization;

import graph.CoreController;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import shapes.*;
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
    private Button initButton, animationToggleButton, stepAnimationButton, playAnimationButton, stopAnimationButton;

    @FXML
    private MenuButton setGraphButton;

    private BooleanProperty editMode, activePlaying;
    private AnimationTimer animationTimer;
    private int passedTicks = 0;
    private double lastNanoTime = System.nanoTime();
    private double time = 0;
    private int tickAccount = 0;
    final private int TICKS_PER_STEP = 20;

    private CoreController coreController = new CoreController();


    private Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stage = (Stage) resources.getObject(null);

        initAnimationTimer();
        initContextMenu();
        initButtonBar();
        initGraphButtons();

    }

    private void initButtonBar() {
        editMode = new SimpleBooleanProperty(true);
        activePlaying = new SimpleBooleanProperty(false);
        editMode.addListener((observable, oldValue, newValue) -> {
            this.coreController.setEditMode(newValue);
        });

        animationToggleButton.setOnMouseClicked(event -> {
            if (editMode.getValue()) {
                editMode.set(false);
                animationToggleButton.setText("Edit Mode");
                zoomScrollPane.autoZoom();
            } else {
                editMode.set(true);
                animationToggleButton.setText("Play Mode");
                activePlaying.set(false);
            }
        });

        stepAnimationButton.disableProperty().bind(editMode.or(activePlaying));
        playAnimationButton.disableProperty().bind(editMode.or(activePlaying));
        stopAnimationButton.disableProperty().bind(editMode.or(activePlaying.not()));

        stepAnimationButton.setOnMouseClicked(event -> {
            this.coreController.simulateStep();
        });

        playAnimationButton.setOnMouseClicked(event -> {

            activePlaying.set(true);
        });

        stopAnimationButton.setOnMouseClicked(event -> {

            activePlaying.set(false);
        });

        activePlaying.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                animationTimer.start();
            } else {
                animationTimer.stop();
            }

        });
    }

    private void initContextMenu() {

        zoomScrollPane.setOnContextMenuRequested(event1 -> {
            if (!this.coreController.isEditMode())
                return;

            ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem item1 = new MenuItem("Add Node");
            item1.setOnAction(event2 -> {
                coreController.createVertex(zoomScrollPane.getLocalCoordinates(event1.getX(), event1.getY()));
            });
            MenuItem item2 = new MenuItem("Close");

            contextMenu.getItems().addAll(item1, item2);
            contextMenu.show(zoomScrollPane, event1.getScreenX(), event1.getScreenY());
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

    public void initMainPane() {
        Group vertexShapes = new Group();
        Group edgeShapes = new Group();
        Group entityShapes = new Group();
        Group lionRangeShapes = new Group();

        zoomScrollPane.getNodesHolder().clear();
        zoomScrollPane.getNodesHolder().addAll(edgeShapes, vertexShapes, lionRangeShapes, entityShapes);


        ShapedBigVertex.setMainPane(zoomScrollPane);
        ShapedBigVertex.setShapeGroup(vertexShapes);
        ShapedSmallVertex.setShapeGroup(vertexShapes);
        ShapedEdge.setShapeGroup(edgeShapes);
        ShapedMan.setMainPane(zoomScrollPane);
        ShapedMan.setShapeGroup(entityShapes);
        ShapedLion.setMainPane(zoomScrollPane);
        ShapedLion.setShapeGroup(entityShapes);
        ShapedRange.setShapeGroup(entityShapes);

    }


    private void initGraphButtons() {
        setGraphButton.disableProperty().bind(editMode.not());

        emptyMapMenuItem.setOnAction(event -> {
            this.initMainPane();
            coreController.setEmptyGraph();
        });

        graph1MenuItem.setOnAction(event -> {
            this.initMainPane();
            coreController.setDefaultGraph1();
            this.zoomScrollPane.autoZoom();
        });

        graph2MenuItem.setOnAction(event -> {
            this.initMainPane();
            coreController.setDefaultGraph2();
            this.zoomScrollPane.autoZoom();
        });

        graph3MenuItem.setOnAction(event -> {
            this.initMainPane();
            coreController.setDefaultGraph3();
            this.zoomScrollPane.autoZoom();
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

        initButton.setOnAction(event -> {
            this.initMainPane();
            coreController.setDefaultGraph2();
            this.zoomScrollPane.autoZoom();
//            this.graphHolder.setGraph(coreController.setDefaultGraph2());
//            this.graphHolder.setState(coreController.getState());
//            this.editGraphModeButton.setSelected(true);
        });

    }

}
