package lions_and_men.applet_graph;

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
import lions_and_men.applet_graph.algorithm.strategies.StrategyEnumLion;
import lions_and_men.applet_graph.algorithm.strategies.StrategyEnumMan;
import lions_and_men.applet_graph.visualization.*;
import lions_and_men.exceptions.WrongConfigurationException;
import lions_and_men.util.ContextMenuHolder;
import lions_and_men.util.ZoomScrollPane;

import java.io.File;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import static lions_and_men.applet_graph.visualization.Constants.ANIMATION_DURATION;


/**
 * User Interface with all buttons, menus and gui components.
 */
public class UserInterface {
    private Timer player;

    private ZoomScrollPane zoomScrollPane;
    private Slider speedSlider;
    private HBox buttonBarCenter;
    private Button modeToggleButton = new Button("Edit Mode");
    private Button appletToggleButton = new Button("Choose App");
    private Group vertexShapes = new Group(), edgeShapes = new Group(), entityShapes = new Group(), entityRangeShapes = new Group(), stepPreviewShapes = new Group(), choicePointShapes = new Group();
    private Button playAnimationButton = new Button("Play");
    private Button stopAnimationButton = new Button("Stop");
    private Button stepAnimationButton = new Button("Single Step");
    private MenuButton setGraphButton, setParameterButton = new MenuButton("Set Parameter"), setViewMenu = new MenuButton("View");
    private Alert gameOverAlert;
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

                    activePlaying.set(false);

                    modeToggleButton.setText("Edit Mode");
                    buttonBarCenter.getChildren().addAll(setGraphButton, setParameterButton, setViewMenu);
                }

                this.coreController.setEditMode(newValue);
                zoomScrollPane.autoZoom();
            } catch (WrongConfigurationException e) {
                editMode.set(oldValue);
            }
        });

        initSpeedSlider();
        initAnimationTimer();
        initEditButtons();
        initPlayButtons();
        initModeButton();
        initContextMenu();
        initZoomScrollPane();
        initGameOverAlert();
    }

    private void initSpeedSlider() {
        speedSlider = new Slider();
        speedSlider.setPrefWidth(250);
        speedSlider.setMin(10);
        speedSlider.setMax(1000);
        speedSlider.setValue(ANIMATION_DURATION);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setMajorTickUnit(50);
        speedSlider.setMinorTickCount(0);
        speedSlider.setBlockIncrement(50);
        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> ANIMATION_DURATION = newValue.intValue());
        speedSlider.disableProperty().bind(activePlaying);
        speedSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n <= 10) return "Fast";
                if (n >= 1000) return "Slow";

                return "";
            }

            @Override
            public Double fromString(String string) {
                switch (string) {
                    case "Fast":
                        return 10d;
                    case "Slow":
                        return 1000d;

                    default:
                        return 1000d;
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
        setGraphButton = new MenuButton("Set Graph");
        MenuItem emptyMapMenuItem = new MenuItem("Empty Graph"),
                graph1MenuItem = new MenuItem("Paper Graph, 3 Lions catch man"),
                graph2MenuItem = new MenuItem("Paper Graph, Paper Strategy"),
                graph3MenuItem = new MenuItem("Paper Graph, Invariant not fulfilled"),
                openMapMenuItem = new MenuItem("Open"),
                saveMapMenuItem = new MenuItem("Save");


        setGraphButton.getItems().addAll(emptyMapMenuItem, new SeparatorMenuItem(), graph2MenuItem, graph1MenuItem, graph3MenuItem, new SeparatorMenuItem(), openMapMenuItem, saveMapMenuItem);

        buttonBarCenter.getChildren().addAll(setGraphButton, setParameterButton, setViewMenu);

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

        MenuItem setEdgeWeight = new MenuItem("Edge Weight");
        MenuItem setManMinDistance = new MenuItem("Man Minimum Distance");
        MenuItem setManRange = new MenuItem("Man Range");
        MenuItem setLionRange = new MenuItem("Lion Range");

        Menu manMenu = new Menu("Man Strategy");
        MenuItem setManStrategyWait = new MenuItem("Wait");
        setManStrategyWait.setOnAction(event -> coreController.setAllManStrategy(StrategyEnumMan.DoNothing));
        MenuItem setManStrategyGreedy = new MenuItem("Greedy");
        setManStrategyGreedy.setOnAction(event -> coreController.setAllManStrategy(StrategyEnumMan.RunAwayGreedyMan));
        MenuItem setManStrategyRandom = new MenuItem("RandomChoice");
        setManStrategyRandom.setOnAction(event -> coreController.setAllManStrategy(StrategyEnumMan.RandomChoice));
        MenuItem setManStrategyManual = new MenuItem("Manual");
        setManStrategyManual.setOnAction(event -> coreController.setAllManStrategy(StrategyEnumMan.Manual));
        MenuItem setManStrategyPaper = new MenuItem("Paper");
        setManStrategyPaper.setOnAction(event -> coreController.setAllManStrategy(StrategyEnumMan.PaperMan));
        manMenu.getItems().addAll(setManStrategyWait, setManStrategyRandom, setManStrategyGreedy, setManStrategyManual, setManStrategyPaper);

        Menu lionMenu = new Menu("Lion Strategy");
        MenuItem setLionsStrategyWait = new MenuItem("Wait");
        setLionsStrategyWait.setOnAction(event -> coreController.setAllLionStrategy(StrategyEnumLion.DoNothing));
        MenuItem setLionsStrategyGreedy = new MenuItem("Greedy");
        setLionsStrategyGreedy.setOnAction(event -> coreController.setAllLionStrategy(StrategyEnumLion.AggroGreedyLion));
        MenuItem setLionsStrategyClever = new MenuItem("Clever");
        setLionsStrategyClever.setOnAction(event -> coreController.setAllLionStrategy(StrategyEnumLion.CleverLion));
        MenuItem setLionsStrategyRandom = new MenuItem("RandomChoice");
        setLionsStrategyRandom.setOnAction(event -> coreController.setAllLionStrategy(StrategyEnumLion.RandomChoice));
        MenuItem setLionsStrategyManual = new MenuItem("Manual");
        setLionsStrategyManual.setOnAction(event -> coreController.setAllLionStrategy(StrategyEnumLion.Manual));
        lionMenu.getItems().addAll(setLionsStrategyWait, setLionsStrategyRandom, setLionsStrategyGreedy, setLionsStrategyClever, setLionsStrategyManual);


        setParameterButton.getItems().addAll(setEdgeWeight, new SeparatorMenuItem(), manMenu, lionMenu, new SeparatorMenuItem(), setManMinDistance, setManRange, setLionRange);

        setEdgeWeight.setOnAction(event -> {
            int currentValue = this.coreController.getDefaultEdgeWeight();

            TextInputDialog dialog = new TextInputDialog("" + currentValue);
            dialog.setTitle("Set Edge Weight");
            dialog.setHeaderText("Enter the weight for all edges.");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {
                    int inputValue = Integer.parseInt(result.get());
                    this.coreController.setAllEdgeWeight(inputValue);
                } catch (NumberFormatException ignore) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Input was not a number.");
                    alert.showAndWait();
                }
            }
        });

        setManMinDistance.setOnAction(event -> {
            int currentValue = this.coreController.getMinimumManDistance();

            TextInputDialog dialog = new TextInputDialog("" + currentValue);
            dialog.setTitle("Set Minimum Distance");
            dialog.setHeaderText("Enter the minimum distance men must be afar from each other.");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {
                    int inputValue = Integer.parseInt(result.get());
                    this.coreController.setMinimumManDistance(inputValue);
                } catch (NumberFormatException ignore) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Input was not a number.");
                    alert.showAndWait();
                }
            }
        });


        setManRange.setOnAction(event -> {
            int currentValue = this.coreController.getDefaultManRange();

            TextInputDialog dialog = new TextInputDialog("" + currentValue);
            dialog.setTitle("Set Man Range");
            dialog.setHeaderText("Enter the range for all men.");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {
                    int inputValue = Integer.parseInt(result.get());
                    this.coreController.setAllManRange(inputValue);
                } catch (NumberFormatException ignore) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Input was not a number.");
                    alert.showAndWait();
                }
            }
        });

        setLionRange.setOnAction(event -> {
            int currentValue = this.coreController.getDefaultLionRange();

            TextInputDialog dialog = new TextInputDialog("" + currentValue);
            dialog.setTitle("Set Lion Range");
            dialog.setHeaderText("Enter the range in which a lion can catch a man.");
            dialog.setContentText("0 means that the lions has to be on the same vertex. > 0 means that the lions can jump and catch a man slightly farther away.");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                try {
                    int inputValue = Integer.parseInt(result.get());
                    this.coreController.setAllLionRange(inputValue);
                } catch (NumberFormatException ignore) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Input was not a number.");
                    alert.showAndWait();
                }
            }
        });


        CheckMenuItem viewEntities = new CheckMenuItem("Entities");
        entityShapes.visibleProperty().bind(viewEntities.selectedProperty());
        viewEntities.setSelected(true);

        CheckMenuItem viewEntityRanges = new CheckMenuItem("Entity Ranges");
        entityRangeShapes.visibleProperty().bind(viewEntityRanges.selectedProperty());
        viewEntityRanges.setSelected(true);

        CheckMenuItem viewPreviews = new CheckMenuItem("Preview");
        stepPreviewShapes.visibleProperty().bind(viewPreviews.selectedProperty());
        viewPreviews.setSelected(false);

        setViewMenu.getItems().addAll(viewEntities, viewEntityRanges, viewPreviews);
    }


    /**
     * Initialize the buttons of the play mode and add the button logic.
     */
    private void initPlayButtons() {
        stepAnimationButton.disableProperty().bind(editMode.or(activePlaying));
        playAnimationButton.disableProperty().bind(editMode.or(activePlaying));
        stopAnimationButton.disableProperty().bind(editMode.or(activePlaying.not()));


        stepAnimationButton.setOnMouseClicked(event -> {
            activePlaying.set(false);
            boolean gameOver = coreController.simulateStep();
            if (gameOver) {
                gameOverAlert.show();
            }
        });
        playAnimationButton.setOnMouseClicked(event -> activePlaying.set(true));
        stopAnimationButton.setOnMouseClicked(event -> activePlaying.set(false));

        activePlaying.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                player = new Timer("Computational Geometry Lab");
                player.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            if (coreController.getMenWithManualInput().isEmpty() && coreController.getLionsWithManualInput().isEmpty()) {
                                boolean gameOver = coreController.simulateStep();
                                if (gameOver) {
                                    gameOverAlert.show();
                                    activePlaying.set(false);
                                }
                            }
                        });
                    }
                }, 0, (long) (ANIMATION_DURATION * 1.3));
            } else {
                player.cancel();
            }

        });
    }

    /**
     * Initialize the logic of the context menu for adding new vertices to the graph.
     */
    private void initContextMenu() {

        zoomScrollPane.setOnContextMenuRequested(event1 -> {
            if (!this.coreController.isEditMode())
                return;

            ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem item1 = new MenuItem("Add Node");
            item1.setOnAction(event2 -> coreController.createVertex(zoomScrollPane.getLocalCoordinates(event1.getX(), event1.getY())));
            MenuItem item2 = new MenuItem("Close");

            contextMenu.getItems().addAll(item1, item2);
            contextMenu.show(zoomScrollPane, event1.getScreenX(), event1.getScreenY());
        });
    }

    /**
     * Initialize the animation timer which is used in the play mode.
     * It works on a fixed amount of FPS (60 is the standard).
     */
    private void initAnimationTimer() {
//        playerTask = ;


        // TickTimer.getInstance().addTicker(animation);
    }

    /**
     * Initialize the ZoomScrollPane which is the center of all visualization.
     */
    private void initZoomScrollPane() {
        this.coreController.setEmptyGraph();

        zoomScrollPane.getNodesHolder().clear();
        zoomScrollPane.getNodesHolder().addAll(edgeShapes, vertexShapes, stepPreviewShapes, entityRangeShapes, entityShapes, choicePointShapes);

        BigVertex.setMainPane(zoomScrollPane);
        BigVertex.setShapeGroup(vertexShapes);
        SmallVertex.setShapeGroup(vertexShapes);
        Edge.setShapeGroup(edgeShapes);
        Man.setMainPane(zoomScrollPane);
        Man.setShapeGroup(entityShapes);
        Lion.setMainPane(zoomScrollPane);
        Lion.setShapeGroup(entityShapes);
        Range.setShapeGroup(entityRangeShapes);
        StepPreview.setShapeGroup(stepPreviewShapes);
        ChoicePoint.setShapeGroup(choicePointShapes);
    }


    /**
     * Clear all group which represent shape holder.
     * This will clear up the ZoomScrollPane.
     */
    private void clearGraphShapes() {
        vertexShapes.getChildren().clear();
        edgeShapes.getChildren().clear();
        entityShapes.getChildren().clear();
        entityRangeShapes.getChildren().clear();
    }

    private void initGameOverAlert() {
        gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
        gameOverAlert.setTitle("Game Over");
        gameOverAlert.setHeaderText("The lions have won the game.");
        gameOverAlert.setContentText("You can start a new game by playing a new man into the graph.");
    }
}
