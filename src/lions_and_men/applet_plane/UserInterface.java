package lions_and_men.applet_plane;

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
import javafx.util.StringConverter;
import lions_and_men.Main;
import lions_and_men.applet_plane.algorithm.plane.AllPaths;
import lions_and_men.applet_plane.algorithm.strategies.lion.StrategyEnumLion;
import lions_and_men.applet_plane.algorithm.strategies.man.StrategyEnumMan;
import lions_and_men.applet_plane.visualization.*;
import lions_and_men.exceptions.WrongConfigurationException;
import lions_and_men.util.ContextMenuHolder;
import lions_and_men.util.ZoomScrollPane;

import java.io.File;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import static lions_and_men.applet_plane.visualization.Constants.ANIMATION_DURATION;

/**
 * User Interface with all buttons, menus and gui components
 */
public class UserInterface {
    private Timer player;

    private ZoomScrollPane zoomScrollPane;
    private Slider speedSlider;
    private HBox buttonBarCenter;
    private Button appletToggleButton = new Button("Choose App");
    private Button modeToggleButton = new Button("Edit Mode");
    private Group entityShapes = new Group(), convexHullShapes = new Group(), currentLionsPathShapes = new Group(), oldLionsPathShapes = new Group(), currentManPathShapes = new Group(), oldManPathShapes = new Group(), boundingPointsShapes = new Group();
    private Button playAnimationButton = new Button("Play");
    private Button stopAnimationButton = new Button("Stop");
    private Button stepAnimationButton = new Button("Single Step");
    private MenuButton setGraphButton = new MenuButton("Set Entities"), setParameterButton = new MenuButton("Set Parameter"), setViewMenu = new MenuButton("View");
    private Button newPermutationButton = new Button("Permute Lion Insertion Order");
    private BooleanProperty editMode, activePlaying;

    private VisualizedCoreController coreController = new VisualizedCoreController();

    private Stage stage;


    public UserInterface(Stage stage, BorderPane root) {
        this.stage = stage;

        zoomScrollPane = new ZoomScrollPane();
        root.setCenter(zoomScrollPane);

        buttonBarCenter = new HBox();
        buttonBarCenter.setPadding(new Insets(10, 10, 10, 10));
        buttonBarCenter.setSpacing(25);

        HBox buttonBarRight = new HBox(appletToggleButton);
        buttonBarRight.setPadding(new Insets(10, 10, 10, 150));
        buttonBarRight.setSpacing(25);

        HBox buttonBarLeft = new HBox(modeToggleButton);
        buttonBarLeft.setPadding(new Insets(10, 150, 10, 10));
        buttonBarLeft.setSpacing(25);

        BorderPane buttonPane = new BorderPane();
        root.setBottom(buttonPane);
        buttonPane.setLeft(buttonBarLeft);
        buttonPane.setCenter(buttonBarCenter);
        buttonPane.setRight(buttonBarRight);


        editMode = new SimpleBooleanProperty(true);
        activePlaying = new SimpleBooleanProperty(false);
        editMode.addListener((observable, oldValue, newValue) -> {
            try {
                buttonBarCenter.getChildren().clear();

                if (!newValue) {
                    modeToggleButton.setText("Play Mode");
                    buttonBarCenter.getChildren().addAll(playAnimationButton, speedSlider, stopAnimationButton, stepAnimationButton, setViewMenu);
                } else {
                    clearAnimationShapes();

                    activePlaying.set(false);

                    modeToggleButton.setText("Edit Mode");
                    buttonBarCenter.getChildren().addAll(setGraphButton, setParameterButton, newPermutationButton, setViewMenu);
                    oldLionsPathShapes.getChildren().clear();
                }

                this.coreController.setEditMode(newValue);
                zoomScrollPane.autoZoom();
            } catch (WrongConfigurationException e) {
                editMode.set(oldValue);
            }

        });

        initSpeedSlider();
        initEditButtons();
        initPlayButtons();
        initModeButton();
        initContextMenu();
        initZoomScrollPane();
    }

    private void initSpeedSlider() {
        speedSlider = new Slider();
        speedSlider.setAccessibleText("hi");
        speedSlider.setPrefWidth(250);
        speedSlider.setMin(10);
        speedSlider.setMax(5000);
        speedSlider.setValue(ANIMATION_DURATION);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setMajorTickUnit(250);
        speedSlider.setMinorTickCount(0);
        speedSlider.setBlockIncrement(250);
        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> ANIMATION_DURATION = newValue.intValue());
        speedSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n <= 10) return "Fast";
                if (n >= 5000) return "Slow";

                return "";
            }

            @Override
            public Double fromString(String string) {
                switch (string) {
                    case "Fast":
                        return 10d;
                    case "Slow":
                        return 5000d;

                    default:
                        return 5000d;
                }
            }
        });
    }

    /**
     * Initialize the mode toggle button and add the button logic.
     */
    private void initModeButton() {
        appletToggleButton.setStyle("-fx-font-style: italic");
        appletToggleButton.setOnAction(event -> Main.showChooser());

        modeToggleButton.setOnAction(event -> editMode.set(!editMode.get()));

    }


    /**
     * Initialize the buttons of the edit mode and add the button logic.
     */
    private void initEditButtons() {
        MenuItem emptyMapMenuItem = new MenuItem("Empty Plane"),
                graph1MenuItem = new MenuItem("Example 1"),
                graph2MenuItem = new MenuItem("Example 2"),
                graph3MenuItem = new MenuItem("Example 3"),
                graph4MenuItem = new MenuItem("Example 4"),
                randomConfigurationButton = new MenuItem("Random Plane"),
                openMapMenuItem = new MenuItem("Open"),
                saveMapMenuItem = new MenuItem("Save");


        setGraphButton.getItems().addAll(emptyMapMenuItem, new SeparatorMenuItem(), graph1MenuItem, graph2MenuItem, graph3MenuItem, graph4MenuItem, randomConfigurationButton, new SeparatorMenuItem(), openMapMenuItem, saveMapMenuItem);


        buttonBarCenter.getChildren().addAll(setGraphButton, setParameterButton, newPermutationButton, setViewMenu);

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

        graph4MenuItem.setOnAction(event -> {
            clearGraphShapes();
            this.coreController.setDefaultGraph4();
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


        MenuItem setGlobalManSpeed = new MenuItem("Man Epsilon");

        Menu manMenu = new Menu("Man Strategy");
        MenuItem setManStrategyPaper = new MenuItem("Paper");
        setManStrategyPaper.setOnAction(event -> coreController.setAllManStrategy(StrategyEnumMan.Paper));
        manMenu.getItems().addAll(setManStrategyPaper/*, setManStrategyRandom, setManStrategyGreedy, setManStrategyManual*/);

        Menu lionMenu = new Menu("Lion Strategy");
        MenuItem setLionsStrategyGreedy = new MenuItem("Greedy");
        setLionsStrategyGreedy.setOnAction(event -> coreController.setAllLionStrategy(StrategyEnumLion.Greedy));
        lionMenu.getItems().addAll(setLionsStrategyGreedy/*, setLionsStrategyRandom, setLionsStrategyManual*/);

        setGlobalManSpeed.setOnAction(event -> {
            double currentValue = this.coreController.getDefaultMenEpsilon();


            TextInputDialog dialog = new TextInputDialog("" + currentValue);
            dialog.setTitle("Set Man Epsilon");
            dialog.setHeaderText("Choose epsilon > 0 to change the man speed.");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {
                    double inputValue = Double.parseDouble(result.get());
                    this.coreController.setDefaultMenEpsilon(inputValue);
                } catch (NumberFormatException ignore) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Input was not a number.");
                    alert.showAndWait();
                }
            }
        });

        MenuItem setMinPathLength = new MenuItem("Minimum Path Length");

        setMinPathLength.setOnAction(event -> {
            int currentValue = this.coreController.getStepsToGoAfterEscape();


            TextInputDialog dialog = new TextInputDialog("" + currentValue);
            dialog.setTitle("Set Minimum Path Length");
            dialog.setHeaderText("Choose the minimum path length which is displayed.");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {
                    int inputValue = Integer.parseInt(result.get());
                    this.coreController.setStepsToGoAfterEscape(inputValue);
                } catch (NumberFormatException ignore) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Input was not a number.");
                    alert.showAndWait();
                }
            }
        });

        setParameterButton.getItems().addAll(setGlobalManSpeed, setMinPathLength, new SeparatorMenuItem(), manMenu, lionMenu);

        CheckMenuItem viewEntities = new CheckMenuItem("Entities");
        entityShapes.visibleProperty().bind(viewEntities.selectedProperty());
        viewEntities.setSelected(true);

        CheckMenuItem viewConvexHull = new CheckMenuItem("Lion Bounding");
        convexHullShapes.visibleProperty().bind(viewConvexHull.selectedProperty().or(editMode));
        viewConvexHull.setSelected(false);

        CheckMenuItem viewManPath = new CheckMenuItem("Most Recent Man Paths");
        currentManPathShapes.visibleProperty().bind(viewManPath.selectedProperty());
        viewManPath.setSelected(true);

        CheckMenuItem viewPreviousManPath = new CheckMenuItem("Previous Man Path");
        oldManPathShapes.visibleProperty().bind(viewPreviousManPath.selectedProperty());
        viewPreviousManPath.setSelected(true);

        CheckMenuItem viewLatestLionPath = new CheckMenuItem("Most Recent Lion Paths");
        currentLionsPathShapes.visibleProperty().bind(viewLatestLionPath.selectedProperty());
        viewLatestLionPath.setSelected(true);

        CheckMenuItem viewLionPath = new CheckMenuItem("Old Lion Paths");
        oldLionsPathShapes.visibleProperty().bind(viewLionPath.selectedProperty());
        viewLionPath.setSelected(false);

        setViewMenu.getItems().addAll(viewEntities, viewConvexHull, viewManPath, viewPreviousManPath, viewLionPath, viewLatestLionPath);
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
                }, 0, (long) (ANIMATION_DURATION * 1.2));
            } else {
                player.cancel();
            }
        });

        newPermutationButton.setOnAction(event -> coreController.shuffleLionOrder());
    }

    /**
     * Initialize the logic of the context menu for adding new vertices to the graph.
     */
    private void initContextMenu() {
        zoomScrollPane.setOnContextMenuRequested(event1 -> {
            if (!this.coreController.isEditMode())
                return;

            ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem addManItem = new MenuItem("Set Man");
            addManItem.setOnAction(event2 -> coreController.createMan(zoomScrollPane.getLocalCoordinates(event1.getX(), event1.getY())));

            MenuItem addLionItem = new MenuItem("Add Lion");
            addLionItem.setOnAction(event2 -> coreController.createLion(zoomScrollPane.getLocalCoordinates(event1.getX(), event1.getY())));

            MenuItem closeItem = new MenuItem("Close");

            contextMenu.getItems().addAll(addManItem, addLionItem, closeItem);
            contextMenu.show(zoomScrollPane, event1.getScreenX(), event1.getScreenY());
        });
    }

    /**
     * Initialize the ZoomScrollPane which is the center of all visualization.
     */
    private void initZoomScrollPane() {
        this.coreController.setEmptyGraph();

        Shape.setCoreController(coreController);
        Shape.setPane(zoomScrollPane);

        zoomScrollPane.getNodesHolder().clear();
        zoomScrollPane.getNodesHolder().addAll(convexHullShapes, oldManPathShapes, currentManPathShapes, currentLionsPathShapes, oldLionsPathShapes, entityShapes, boundingPointsShapes);


        Man.setGroup(entityShapes);
        Lion.setGroup(entityShapes);
        LionsPolygon.setGroup(convexHullShapes);

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
        boundingPointsShapes.getChildren().clear();
    }

}
