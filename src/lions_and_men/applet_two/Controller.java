package lions_and_men.applet_two;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lions_and_men.Main;
import lions_and_men.applet_two.core.plane.AllPaths;
import lions_and_men.applet_two.core.strategies.lion.StrategyEnumLion;
import lions_and_men.applet_two.core.strategies.man.StrategyEnumMan;
import lions_and_men.applet_two.visualization.*;
import lions_and_men.util.ContextMenuHolder;
import lions_and_men.util.ZoomScrollPane;

import java.io.File;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import static lions_and_men.applet_two.visualization.Constants.SINGLE_STEP_DURATION;


public class Controller {
    private Timer player;

    private ZoomScrollPane zoomScrollPane;
    private HBox buttonBar;
    private Button helpToggleButton = new Button("Help");
    private Button appletToggleButton = new Button("Switch Application");
    private Button modeToggleButton = new Button("Edit Mode");
    private Group entityShapes = new Group(), lionRangeShapes = new Group(), convexHullShapes = new Group(), currentLionsPathShapes = new Group(), oldLionsPathShapes = new Group(), currentManPathShapes = new Group(), oldManPathShapes = new Group(), boundingPathShapes = new Group(), boundingPointsShapes = new Group();
    private Button playAnimationButton = new Button("Play");
    private Button stopAnimationButton = new Button("Stop");
    private Button stepAnimationButton = new Button("Single Step");
    private MenuButton setGraphButton = new MenuButton("Set Graph"), setParameterButton = new MenuButton("Set Parameter"), setViewMenu = new MenuButton("View");
    private Button newPermutationButton = new Button("Permute Lion Insertion Order");
    private BooleanProperty editMode, activePlaying;

    private VisualizedCoreController coreController = new VisualizedCoreController();

    private Stage stage;


    public Controller(Stage stage, BorderPane root) {
        this.stage = stage;

        zoomScrollPane = new ZoomScrollPane();
        root.setCenter(zoomScrollPane);

        buttonBar = new HBox();
        root.setBottom(buttonBar);
        buttonBar.setPadding(new Insets(10, 10, 10, 10));
        buttonBar.setSpacing(25);

        editMode = new SimpleBooleanProperty(true);
        activePlaying = new SimpleBooleanProperty(false);
        editMode.addListener((observable, oldValue, newValue) -> {
            this.coreController.setEditMode(newValue);
        });

        initEditButtons();
        initPlayButtons();
        initModeButton();
        initContextMenu();
        initZoomScrollPane();
//        initGameOverAlert();
    }

    /**
     * Initialize the mode toggle button and add the button logic.
     */
    private void initModeButton() {
        helpToggleButton.setStyle("-fx-font-style: italic");
        appletToggleButton.setStyle("-fx-font-style: italic");

        appletToggleButton.setOnAction(event -> {
            Main.switchStage();
        });

        modeToggleButton.setOnAction(event -> {
            buttonBar.getChildren().clear();

            if (editMode.getValue()) {
                editMode.set(false);

                modeToggleButton.setText("Edit Mode");
                buttonBar.getChildren().addAll(appletToggleButton, helpToggleButton, modeToggleButton, playAnimationButton, stopAnimationButton, stepAnimationButton, setViewMenu);
            } else {
                clearAnimationShapes();

                editMode.set(true);
                activePlaying.set(false);

                modeToggleButton.setText("Play Mode");
                buttonBar.getChildren().addAll(appletToggleButton, helpToggleButton, modeToggleButton, setGraphButton, setParameterButton, newPermutationButton, setViewMenu);
                oldLionsPathShapes.getChildren().clear();

                zoomScrollPane.autoZoom();
            }
        });

    }


    /**
     * Initialize the buttons of the edit mode and add the button logic.
     */
    private void initEditButtons() {
        MenuItem emptyMapMenuItem = new MenuItem("Empty Graph"),
                graph1MenuItem = new MenuItem("Example 1"),
                graph2MenuItem = new MenuItem("Example 2"),
                graph3MenuItem = new MenuItem("Example 3"),
                randomConfigurationButton = new MenuItem("Random"),
                openMapMenuItem = new MenuItem("Open"),
                saveMapMenuItem = new MenuItem("Save");


        setGraphButton.getItems().addAll(emptyMapMenuItem, new SeparatorMenuItem(), graph1MenuItem, graph2MenuItem, graph3MenuItem, randomConfigurationButton, new SeparatorMenuItem(), openMapMenuItem, saveMapMenuItem);

        buttonBar.getChildren().addAll(appletToggleButton, helpToggleButton, modeToggleButton, setGraphButton, setParameterButton, newPermutationButton, setViewMenu);

        emptyMapMenuItem.setOnAction(event -> {
            clearGraphShapes();
            this.coreController.setEmptyGraph();
        });

        graph1MenuItem.setOnAction(event -> {
            clearGraphShapes();
            this.coreController.setDefaultGraph1();
            this.zoomScrollPane.autoZoom();
        });

        graph2MenuItem.setOnAction(event -> {
            clearGraphShapes();
            this.coreController.setDefaultGraph2();
            this.zoomScrollPane.autoZoom();
        });

        graph3MenuItem.setOnAction(event -> {
            clearGraphShapes();
            this.coreController.setDefaultGraph3();
            this.zoomScrollPane.autoZoom();
        });

        randomConfigurationButton.setOnAction(event -> {
            clearGraphShapes();
            this.coreController.setRandomConfiguration();
            this.zoomScrollPane.autoZoom();
        });

        openMapMenuItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource Configuration");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Config Files", "*.config"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(this.stage);
            if (selectedFile != null) {
                try {
                    clearGraphShapes();
                    coreController.setGraphFromFile(selectedFile);
                    this.zoomScrollPane.autoZoom();
                } catch (Exception ignored) {

                }
            }
        });

        saveMapMenuItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Current Configuration");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Config Files", "*.config"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showSaveDialog(this.stage);
            if (selectedFile != null) {
                this.coreController.saveGraphToFile(selectedFile);
            }
        });


        MenuItem setGlobalManSpeed = new MenuItem("Set Man Speed");
        MenuItem setGlobalLionSpeed = new MenuItem("Set Lion Speed");
        MenuItem setGlobalLionRange = new MenuItem("Set Lion Range");

        Menu manMenu = new Menu("Set Man Strategy");
        MenuItem setManStrategyWait = new MenuItem("Wait");
        setManStrategyWait.setOnAction(event -> coreController.setAllManStrategy(StrategyEnumMan.Wait));
        MenuItem setManStrategyPaper = new MenuItem("Paper");
        setManStrategyPaper.setOnAction(event -> coreController.setAllManStrategy(StrategyEnumMan.Paper));
        manMenu.getItems().addAll(setManStrategyWait, setManStrategyPaper/*, setManStrategyRandom, setManStrategyGreedy, setManStrategyManual*/);

        Menu lionMenu = new Menu("Set Lion Strategy");
        MenuItem setLionsStrategyWait = new MenuItem("Wait");
        setLionsStrategyWait.setOnAction(event -> coreController.setAllLionStrategy(StrategyEnumLion.Wait));
        MenuItem setLionsStrategyGreedy = new MenuItem("Greedy");
        setLionsStrategyGreedy.setOnAction(event -> coreController.setAllLionStrategy(StrategyEnumLion.Greedy));
        lionMenu.getItems().addAll(setLionsStrategyWait, setLionsStrategyGreedy/*, setLionsStrategyRandom, setLionsStrategyManual*/);


        setParameterButton.getItems().addAll(setGlobalManSpeed, setGlobalLionSpeed, setGlobalLionRange, new SeparatorMenuItem(), manMenu, lionMenu);

        setGlobalManSpeed.setOnAction(event -> {
            double currentValue = this.coreController.getDefaultMenEpsilon();


            TextInputDialog dialog = new TextInputDialog("" + currentValue);
            dialog.setTitle("Set Man Epsilon");
            dialog.setHeaderText("-----.");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {

                    double inputValue = Double.parseDouble(result.get());
                    this.coreController.setDefaultMenEpsilon(inputValue);
                } catch (Exception ignore) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Input was not a number.");
                    alert.showAndWait();
                }
            }
        });

        setGlobalLionSpeed.setOnAction(event -> {
            double currentValue = this.coreController.getDefaultLionsSpeed();

            TextInputDialog dialog = new TextInputDialog("" + currentValue);
            dialog.setTitle("Set Lion Speed");
            dialog.setHeaderText("----.");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {
                    double inputValue = Double.parseDouble(result.get());
                    this.coreController.setDefaultLionsSpeed(inputValue);
                } catch (Exception ignore) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Input was not a number.");
                    alert.showAndWait();
                }
            }
        });


        setGlobalLionRange.setOnAction(event -> {
            double currentValue = this.coreController.getDefaultLionsRange();


            TextInputDialog dialog = new TextInputDialog("" + currentValue);
            dialog.setTitle("Set Lion Range");
            dialog.setHeaderText("Enter the range in which a lion can catch a man.");
            dialog.setContentText("0 means that the lions has to be on the same vertex. > 0 means that the lions can jump and catch a man slightly farther away.");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {
                    double inputValue = Double.parseDouble(result.get());
                    this.coreController.setDefaultLionsRange(inputValue);
                } catch (Exception ignore) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Input was not a number.");
                    alert.showAndWait();
                }
            }
        });

        CheckMenuItem viewEntities = new CheckMenuItem("View Entities");
        entityShapes.visibleProperty().bind(viewEntities.selectedProperty());
        viewEntities.setSelected(true);

        CheckMenuItem viewLionRanges = new CheckMenuItem("View Lion Ranges");
        lionRangeShapes.visibleProperty().bind(viewLionRanges.selectedProperty());
        viewLionRanges.setSelected(true);

        CheckMenuItem viewConvexHull = new CheckMenuItem("View Lion Bounding");
        convexHullShapes.visibleProperty().bind(viewConvexHull.selectedProperty().or(editMode));
        viewConvexHull.setSelected(false);

        CheckMenuItem viewManPath = new CheckMenuItem("View Most Recent Man Paths");
        currentManPathShapes.visibleProperty().bind(viewManPath.selectedProperty());
        viewManPath.setSelected(true);

        CheckMenuItem viewPreviousManPath = new CheckMenuItem("View Previous Man Path");
        oldManPathShapes.visibleProperty().bind(viewPreviousManPath.selectedProperty());
        viewPreviousManPath.setSelected(true);

        CheckMenuItem viewLatestLionPath = new CheckMenuItem("View Most Recent Lion Paths");
        currentLionsPathShapes.visibleProperty().bind(viewLatestLionPath.selectedProperty());
        viewLatestLionPath.setSelected(true);

        CheckMenuItem viewLionPath = new CheckMenuItem("View Old Lion Paths");
        oldLionsPathShapes.visibleProperty().bind(viewLionPath.selectedProperty());
        viewLionPath.setSelected(false);

        setViewMenu.getItems().addAll(viewEntities, viewLionRanges, viewConvexHull, viewManPath, viewPreviousManPath, viewLionPath, viewLatestLionPath);
    }


    /**
     * Initialize the buttons of the play mode and add the button logic.
     */
    private void initPlayButtons() {
        stepAnimationButton.disableProperty().bind(editMode.or(activePlaying));
        playAnimationButton.disableProperty().bind(editMode.or(activePlaying));
        stopAnimationButton.disableProperty().bind(editMode.or(activePlaying.not()));


        stepAnimationButton.setOnAction(event -> {
            activePlaying.set(false);
            coreController.simulateStep();
        });
        playAnimationButton.setOnAction(event -> activePlaying.set(true));
        stopAnimationButton.setOnAction(event -> activePlaying.set(false));

        activePlaying.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                player = new Timer("Computational Geometry Lab");
                player.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            AllPaths animation = coreController.simulateStep();
                            if (animation != null && animation.finished) {
                                activePlaying.set(false);
                            }
                        });
                    }
                }, 0, SINGLE_STEP_DURATION);
            } else {
                player.cancel();
            }
        });

        newPermutationButton.setOnAction(event -> {
            coreController.shuffleLionOrder();
        });
    }

    /**
     * Initialize the logic of the context menu for adding new vertices to the graph.
     */
    private void initContextMenu() {
        zoomScrollPane.setOnContextMenuRequested(event1 -> {
            if (!this.coreController.isEditMode())
                return;

            // TODO: IMPLEMENT THIS
            ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem addManItem = new MenuItem("Add Man");
            addManItem.setOnAction(event2 -> coreController.createMan(zoomScrollPane.getLocalCoordinates(event1.getX(), event1.getY())));

            MenuItem addLionItem = new MenuItem("Add Lion");
            addLionItem.setOnAction(event2 -> coreController.createLion(zoomScrollPane.getLocalCoordinates(event1.getX(), event1.getY())));

            MenuItem closeItem = new MenuItem("Close");

            contextMenu.getItems().addAll(addManItem, addLionItem, closeItem);
            contextMenu.show(zoomScrollPane, event1.getScreenX(), event1.getScreenY());
        });
    }

    /**
     * Initialize the animation timer which is used in the play mode.
     * It works on a fixed amount of FPS (60 is the standard).
     */
    private void initAnimationTimer() {

    }

    /**
     * Initialize the ZoomScrollPane which is the center of all visualization.
     */
    private void initZoomScrollPane() {
        this.coreController.setEmptyGraph();

        Shape.setCoreController(coreController);
        Shape.setPane(zoomScrollPane);

        zoomScrollPane.getNodesHolder().clear();
        zoomScrollPane.getNodesHolder().addAll(lionRangeShapes, convexHullShapes, oldManPathShapes, currentManPathShapes, currentLionsPathShapes, oldLionsPathShapes, entityShapes, boundingPathShapes, boundingPointsShapes);


        Man.setGroup(entityShapes);
        Lion.setGroup(entityShapes);
        LionsPolygon.setGroup(convexHullShapes);
        InvisiblePath.setGroup(boundingPathShapes);
        InvisiblePoints.setGroup(boundingPointsShapes);
        ManPath.setGroup1(currentManPathShapes);
        ManPath.setGroup2(oldManPathShapes);
        LionPath.setGroup1(currentLionsPathShapes);
        LionPath.setGroup2(oldLionsPathShapes);

    }


    /**
     * Clear all group which represent shape holder.
     * This will clear up the ZoomScrollPane.
     */
    private void clearGraphShapes() {
        entityShapes.getChildren().clear();
        clearAnimationShapes();
    }

    private void clearAnimationShapes() {
        convexHullShapes.getChildren().clear();
        oldLionsPathShapes.getChildren().clear();
        currentLionsPathShapes.getChildren().clear();
        oldManPathShapes.getChildren().clear();
        currentManPathShapes.getChildren().clear();
        boundingPathShapes.getChildren().clear();
        boundingPointsShapes.getChildren().clear();
    }

//    private void initGameOverAlert() {
//        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
//        gameOverAlert.setTitle("Game Over");
//        gameOverAlert.setHeaderText("The lions have won the game.");
//        gameOverAlert.setContentText("You can start a new game by playing a new man into the graph.");
//    }
}
