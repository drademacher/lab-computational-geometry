package lions_on_graph;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lions_on_graph.core.CoreController;
import lions_on_graph.visualization.*;
import util.ContextMenuHolder;
import util.TickTimer;
import util.ZoomScrollPane;

import java.io.File;
import java.util.Optional;


class Controller {
    private TickTimer.Ticker animation;


    private ZoomScrollPane zoomScrollPane;
    private HBox buttonBar;
    private Button modeToggleButton = new Button("Edit Mode");
    private Group vertexShapes = new Group(), edgeShapes = new Group(), entityShapes = new Group(), lionRangeShapes = new Group(), stepPreviewShapes = new Group(), choisePointShapes = new Group();
    private Button playAnimationButton = new Button("Play");
    private Button stopAnimationButton = new Button("Stop");
    private Button stepAnimationButton = new Button("Single Step");
    private MenuButton setGraphButton, setParameterButton = new MenuButton("Set Parameter"), setViewMenu = new MenuButton("View");
    private Alert gameOverAlert;
    private BooleanProperty editMode, activePlaying;

    private CoreController coreController = new CoreController();

    private Stage stage;


    Controller(Stage stage, BorderPane root) {
        this.stage = stage;

        zoomScrollPane = new ZoomScrollPane();
        root.setCenter(zoomScrollPane);

        buttonBar = new HBox();
        root.setBottom(buttonBar);
        buttonBar.setPadding(new Insets(10, 10, 10, 10));
        buttonBar.setSpacing(25);

        editMode = new SimpleBooleanProperty(true);
        activePlaying = new SimpleBooleanProperty(false);
        editMode.addListener((observable, oldValue, newValue) -> this.coreController.setEditMode(newValue));

        initEditButtons();
        initPlayButtons();
        initModeButton();
        initAnimationTimer();
        initContextMenu();
        initZoomScrollPane();
        initGameOverAlert();
    }

    /**
     * Initialize the mode toggle button and add the button logic.
     */
    private void initModeButton() {
        modeToggleButton.setOnMouseClicked(event -> {
            buttonBar.getChildren().clear();

            if (editMode.getValue()) {
                editMode.set(false);

                modeToggleButton.setText("Edit Mode");
                buttonBar.getChildren().addAll(modeToggleButton, playAnimationButton, stopAnimationButton, stepAnimationButton);

                zoomScrollPane.autoZoom();

            } else {
                editMode.set(true);
                activePlaying.set(false);

                modeToggleButton.setText("Play Mode");
                buttonBar.getChildren().addAll(modeToggleButton, setGraphButton, setParameterButton, setViewMenu);
            }
        });

    }


    /**
     * Initialize the buttons of the edit mode and add the button logic.
     */
    private void initEditButtons() {
        setGraphButton = new MenuButton("Set Graph");
        MenuItem emptyMapMenuItem = new MenuItem("Empty Graph"),
                graph1MenuItem = new MenuItem("Paper Graph, 3 Lions catch man"),
                graph2MenuItem = new MenuItem("Paper Graph, Paper Strategy"),
                graph3MenuItem = new MenuItem("Test Graph"),
                openMapMenuItem = new MenuItem("Open"),
                saveMapMenuItem = new MenuItem("Save");


        setGraphButton.getItems().addAll(emptyMapMenuItem, new SeparatorMenuItem(), graph2MenuItem, graph1MenuItem, graph3MenuItem, new SeparatorMenuItem(), openMapMenuItem, saveMapMenuItem);

        buttonBar.getChildren().addAll(modeToggleButton, setGraphButton, setParameterButton, setViewMenu);

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

        MenuItem setEdgeWeight = new MenuItem("Set Edge Weight");
        MenuItem setManMinDistance = new MenuItem("Set Man Minimum Distance");
//        MenuItem setManFixDistance = new MenuItem("Set Man Fixed Distance");
        MenuItem setLionRange = new MenuItem("Set Lion Range");

        Menu manMenu = new Menu("Set Man Strategy");
        MenuItem setManStrategyWait = new MenuItem("Wait");
        setManStrategyWait.setOnAction(event -> coreController.setAllManStrategy(CoreController.ManStrategy.DoNothing));
        MenuItem setManStrategyGreedy = new MenuItem("Greedy");
        setManStrategyGreedy.setOnAction(event -> coreController.setAllManStrategy(CoreController.ManStrategy.RunAwayGreedy));
        MenuItem setManStrategyRandom = new MenuItem("Random");
        setManStrategyRandom.setOnAction(event -> coreController.setAllManStrategy(CoreController.ManStrategy.Random));
        MenuItem setManStrategyManual = new MenuItem("Manual");
        setManStrategyManual.setOnAction(event -> coreController.setAllManStrategy(CoreController.ManStrategy.Manually));
        MenuItem setManStrategyPaper = new MenuItem("Paper");
        setManStrategyPaper.setOnAction(event -> coreController.setAllManStrategy(CoreController.ManStrategy.Paper));
        manMenu.getItems().addAll(setManStrategyWait, setManStrategyRandom, setManStrategyGreedy, setManStrategyManual, setManStrategyPaper);

        Menu lionMenu = new Menu("Set Lion Strategy");
        MenuItem setLionsStrategyWait = new MenuItem("Wait");
        setLionsStrategyWait.setOnAction(event -> coreController.setAllLionStrategy(CoreController.LionStrategy.DoNothing));
        MenuItem setLionsStrategyGreedy = new MenuItem("Greedy");
        setLionsStrategyGreedy.setOnAction(event -> coreController.setAllLionStrategy(CoreController.LionStrategy.AggroGreedy));
        MenuItem setLionsStrategyClever = new MenuItem("Clever");
        setLionsStrategyClever.setOnAction(event -> coreController.setAllLionStrategy(CoreController.LionStrategy.Clever));
        MenuItem setLionsStrategyRandom = new MenuItem("Random");
        setLionsStrategyRandom.setOnAction(event -> coreController.setAllLionStrategy(CoreController.LionStrategy.Random));
        MenuItem setLionsStrategyManual = new MenuItem("Manual");
        setLionsStrategyManual.setOnAction(event -> coreController.setAllLionStrategy(CoreController.LionStrategy.Manually));
        lionMenu.getItems().addAll(setLionsStrategyWait, setLionsStrategyRandom, setLionsStrategyGreedy, setLionsStrategyClever, setLionsStrategyManual);


        setParameterButton.getItems().addAll(setEdgeWeight, new SeparatorMenuItem(), manMenu, lionMenu, new SeparatorMenuItem(), setManMinDistance, /*setManFixDistance,*/ setLionRange);

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
                } catch (Exception ignore) {
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
                } catch (Exception ignore) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Input was not a number.");
                    alert.showAndWait();
                }
            }
        });


//        setManFixDistance.setOnAction(event -> {
//            int currentValue = this.coreController.getExactManDistance();
//
//            TextInputDialog dialog = new TextInputDialog("" + currentValue);
//            dialog.setTitle("Set Minimum Distance");
//            dialog.setHeaderText("Enter the minimum distance men must be afar from each other.");
//
//            Optional<String> result = dialog.showAndWait();
//
//            if (result.isPresent()) {
//                try {
//                    int inputValue = Integer.parseInt(result.get());
//                    this.coreController.setExactManDistance(inputValue);
//                } catch (Exception ignore) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Error");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Input was not a number.");
//                    alert.showAndWait();
//                }
//            }
//        });

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

        CheckMenuItem viewPreviews = new CheckMenuItem("View Preview");
        stepPreviewShapes.visibleProperty().bind(viewPreviews.selectedProperty());
        viewPreviews.setSelected(false);

        setViewMenu.getItems().addAll(viewEntities, viewLionRanges, viewPreviews);
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
                TickTimer.getInstance().addTicker(animation);
            } else {
                TickTimer.getInstance().removeTicker(animation);
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
        animation = new TickTimer.Ticker() {
            int tickAccount = 0;
            int ticksPerStep = 20;

            @Override
            public void action() {
                tickAccount += 1;
                if (tickAccount >= ticksPerStep) {
                    tickAccount -= ticksPerStep;
                    if (coreController.getMenWithManualInput().isEmpty() && coreController.getLionsWithManualInput().isEmpty()) {
                        boolean gameOver = coreController.simulateStep();
                        if (gameOver) {
                            gameOverAlert.show();
                            activePlaying.set(false);
                        }
                    }
                }
            }
        };

        // TickTimer.getInstance().addTicker(animation);
    }

    /**
     * Initialize the ZoomScrollPane which is the center of all visualization.
     */
    private void initZoomScrollPane() {
        this.coreController.setEmptyGraph();

        zoomScrollPane.getNodesHolder().clear();
        zoomScrollPane.getNodesHolder().addAll(edgeShapes, vertexShapes, stepPreviewShapes, lionRangeShapes, entityShapes, choisePointShapes);

        BigVertex.setMainPane(zoomScrollPane);
        BigVertex.setShapeGroup(vertexShapes);
        SmallVertex.setShapeGroup(vertexShapes);
        Edge.setShapeGroup(edgeShapes);
        Man.setMainPane(zoomScrollPane);
        Man.setShapeGroup(entityShapes);
        Lion.setMainPane(zoomScrollPane);
        Lion.setShapeGroup(entityShapes);
        Range.setShapeGroup(lionRangeShapes);
        StepPreview.setShapeGroup(stepPreviewShapes);
        ChoicePoint.setShapeGroup(choisePointShapes);
    }


    /**
     * Clear all group which represent shape holder.
     * This will clear up the ZoomScrollPane.
     */
    private void clearGraphShapes() {
        vertexShapes.getChildren().clear();
        edgeShapes.getChildren().clear();
        entityShapes.getChildren().clear();
        lionRangeShapes.getChildren().clear();
    }

    private void initGameOverAlert() {
        gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
        gameOverAlert.setTitle("Game Over");
        gameOverAlert.setHeaderText("The lions have won the game.");
        gameOverAlert.setContentText("You can start a new game by playing a new man into the graph.");
    }
}
