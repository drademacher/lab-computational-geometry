package visualization;

import core.CoreController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
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
    private MenuItem addNodeButton, removeNodeButton, relocateNodeButton, addEdgeButton, removeEdgeButton;

    @FXML
    private ZoomScrollPane zoomScrollPane;

    @FXML
    private CheckMenuItem edgeLengthButton, shortestDistanceButton, shortestPathButton;

    @FXML
    private RadioMenuItem viewAllEdgeStepsMenuItem;


    @FXML
    private ToggleGroup useModeToggle;

    @FXML
    private RadioMenuItem editGraphModeButton, editEntityModeButton, playModeButton;


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
        this.graphHolder = new GraphHolder(zoomScrollPane); // baseCanvas, edgeLengthCanvas, edgeStepsActiveCanvas, edgeStepsAllCanvas, shortestDistanceCanvas, shortestPathCanvas


        // TODO: global key commands here!
//        mainPane.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
//            if (oldScene != null) oldScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
//            if (newScene != null) newScene.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
//        });


        initGraphButtons();
//        initAddNodeButton();
//        initRemoveNodeButton();
//        initRelocateNodeButton();
//        initAddEdgeButton();
//        initRemoveEdgeButton();
        useModeToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == this.editGraphModeButton) {
                graphHolder.setGraphEditMode();
            } else if (newValue == this.editEntityModeButton) {
                graphHolder.setEntityEditMode();
            } else if (newValue == this.playModeButton) {
                graphHolder.setPlayMode();
            }
        });

        initViews();
    }

    private void initGraphButtons() {
        emptyMapMenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            this.graphHolder.setGraph(coreController.setEmptyGraph());
            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
        });

        graph1MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            this.graphHolder.setGraph(coreController.setDefaultGraph1());
            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
        });

        graph2MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            this.graphHolder.setGraph(coreController.setDefaultGraph2());
            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
        });

        graph3MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            this.graphHolder.setGraph(coreController.setDefaultGraph3());
            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
        });

        graph4MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            this.graphHolder.setGraph(coreController.setDefaultGraph4());
            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
        });

        graph5MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            this.graphHolder.setGraph(coreController.setDefaultGraph5());
            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
        });

        randomGraphMenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            this.graphHolder.setGraph(coreController.setRandomGraph());
            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
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
