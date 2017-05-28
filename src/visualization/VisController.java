package visualization;

import core.CoreController;
import core.graph.Graph;
import core.graph.Vertex;
import core.util.Point;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class VisController implements Initializable {

    // TODO: rename all the names, since they are not even close to be consistent
    // some end with MenuItem, some have prefix "view", or postfix "toggleGroup"

    @FXML
    private BorderPane mainPane;

    @FXML
    private MenuItem emptyMapMenuItem, graph1MenuItem, graph2MenuItem, graph3MenuItem, graph4MenuItem, graph5MenuItem, randomGraphMenuItem, openMapMenuItem, saveMapMenuItem;

    @FXML
    private MenuItem addNodeButton, removeNodeButton, addEdgeButton, removeEdgeButton;

    @FXML
    private StackPane canvasStacker;
    // private Canvas baseCanvas, edgeLengthCanvas, edgeStepsActiveCanvas, edgeStepsAllCanvas, shortestDistanceCanvas, shortestPathCanvas;

    @FXML
    private CheckMenuItem edgeLengthButton, shortestDistanceButton, shortestPathButton;

    @FXML
    private RadioMenuItem viewAllEdgeStepsMenuItem;


    // TODO: inject the coreController here
    private CoreController coreController = new CoreController();

    private GraphHolder graphHolder;

    private Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stage = (Stage) resources.getObject(null);

/*        StackPane pane = (StackPane) baseCanvas.getParent();

        Point2D cameraDim = new Point2D.Double(pane.getWidth(), pane.getHeight());


        Canvas canvas = baseCanvas.getGraphicsContext2D().getCanvas();
        GraphicsContext gc = baseCanvas.getGraphicsContext2D();

        canvas.setHeight(cameraDim.getX());
        canvas.setWidth(cameraDim.getY());

        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.BLACK);
        gc.fillRect(10, 10, 10,10);

        System.out.println(canvas.getHeight());*/

        //Init mapHolder
        this.graphHolder = new GraphHolder(canvasStacker); // baseCanvas, edgeLengthCanvas, edgeStepsActiveCanvas, edgeStepsAllCanvas, shortestDistanceCanvas, shortestPathCanvas




        final EventHandler<KeyEvent> keyEventHandler = e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                this.graphHolder.moveCamera(new Point(1, 0));
            }
            if (e.getCode() == KeyCode.LEFT) {
                this.graphHolder.moveCamera(new Point(-1, 0));
            }
            if (e.getCode() == KeyCode.UP) {
                this.graphHolder.moveCamera(new Point(0, -1));
            }
            if (e.getCode() == KeyCode.DOWN) {
                this.graphHolder.moveCamera(new Point(0, 1));
            }
            if (e.getCode() == KeyCode.SPACE) {
                this.coreController.simulateStep();
            }
            e.consume();
        };

        mainPane.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene != null) oldScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
            if (newScene != null) newScene.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
        });

        final ChangeListener listener = (observable, oldValue, newValue) -> {
            if (graphHolder == null) return;
            graphHolder.refreshMap();
        };

        mainPane.widthProperty().addListener(listener);
        mainPane.heightProperty().addListener(listener);


        initGraphButtons();
        initAddNodeButton();
        initRemoveNodeButton();
        initAddEdgeButton();
        initRemoveEdgeButton();
        initViews();





        // TODO: remove this dirty hack
        // edgeStepsAllCanvas.visibleProperty().bind(viewAllEdgeStepsMenuItem.selectedProperty());
    }

    private void initGraphButtons() {
        emptyMapMenuItem.setOnAction(event -> {
            this.graphHolder.setGraph(coreController.setEmptyGraph());
        });

        graph1MenuItem.setOnAction(event -> {
            this.graphHolder.setGraph(coreController.setDefaultGraph1());
            this.graphHolder.setState(coreController.getState());
        });

        graph2MenuItem.setOnAction(event -> {
            this.graphHolder.setGraph(coreController.setDefaultGraph2());
        });

        graph3MenuItem.setOnAction(event -> {
            this.graphHolder.setGraph(coreController.setDefaultGraph3());
        });

        graph4MenuItem.setOnAction(event -> {
            this.graphHolder.setGraph(coreController.setDefaultGraph4());
        });

        graph5MenuItem.setOnAction(event -> {
            this.graphHolder.setGraph(coreController.setDefaultGraph5());
        });

        randomGraphMenuItem.setOnAction(event -> {
            this.graphHolder.setGraph(coreController.setRandomGraph());
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
                        this.graphHolder.setGraph(coreController.setGraphFromFile(selectedFile));
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

    private void initAddNodeButton() {

        addNodeButton.setOnAction(event -> this.graphHolder.setOnMouseClickedCallback((coordinate -> {
            this.graphHolder.setNode(coordinate);
            this.graphHolder.setOnMouseClickedCallback(null);
        })));
    }

    private void initRemoveNodeButton() {

        removeNodeButton.setOnAction(event -> this.graphHolder.setOnMouseClickedCallback((coordinate -> {
            this.graphHolder.removeNode(coordinate);
            this.graphHolder.setOnMouseClickedCallback(null);
        })));
    }

    private void initAddEdgeButton() {
        addEdgeButton.setOnAction(event -> {
            this.graphHolder.setOnMouseClickedCallback((from -> {
                this.graphHolder.setOnMouseClickedCallback(to -> {
                    this.graphHolder.setEdge(from, to);
                    this.graphHolder.setOnMouseClickedCallback(null);
                });
            }));
        });
    }
    private void initRemoveEdgeButton() {
        removeEdgeButton.setOnAction(event -> {
            this.graphHolder.setOnMouseClickedCallback((from -> {
                this.graphHolder.setOnMouseClickedCallback(to -> {
                    this.graphHolder.removeEdge(from, to);
                    this.graphHolder.setOnMouseClickedCallback(null);
                });
            }));
        });
    }

    private void initViews() {
        // TODO: visibiility properties set up is missing
//        edgeLengthCanvas.visibleProperty().bind(edgeLengthButton.selectedProperty());
//        edgeLengthCanvas.setMouseTransparent(true);
//
//        shortestDistanceCanvas.visibleProperty().bind(shortestDistanceButton.selectedProperty());
//        shortestDistanceCanvas.setMouseTransparent(true);
//
//        shortestPathCanvas.visibleProperty().bind(shortestPathButton.selectedProperty());
//        shortestPathCanvas.setMouseTransparent(true);
//
//        edgeStepsActiveCanvas.setMouseTransparent(true);
//        edgeStepsAllCanvas.setMouseTransparent(true);
    }
}
