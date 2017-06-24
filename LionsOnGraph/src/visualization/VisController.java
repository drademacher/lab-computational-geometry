package visualization;

import graph.GraphController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
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
    private BorderPane mainPane;

    @FXML
    private MenuItem emptyMapMenuItem, graph1MenuItem, graph2MenuItem, graph3MenuItem, graph4MenuItem, graph5MenuItem, randomGraphMenuItem, openMapMenuItem, saveMapMenuItem;


    @FXML
    private ZoomScrollPane zoomScrollPane;


    @FXML
    private ToggleGroup useModeToggle;

    @FXML
    private RadioMenuItem editGraphModeButton, editEntityModeButton, playModeButton;


    // TODO: inject the coreController here
    private GraphController coreController = new GraphController();


    private Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stage = (Stage) resources.getObject(null);


        initGraphButtons();

        useModeToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == this.editGraphModeButton) {
//                graphHolder.setGraphEditMode();
            } else if (newValue == this.editEntityModeButton) {
//                graphHolder.setEntityEditMode();
            } else if (newValue == this.playModeButton) {
//                graphHolder.setPlayMode();
            }
        });


        initContextMenu();


        initViews();
    }

    private void initContextMenu() {
        zoomScrollPane.getGround().setOnContextMenuRequested(event1 -> {
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

    private void initGraphButtons() {
        emptyMapMenuItem.setOnAction(event -> {

            this.zoomScrollPane.clear();
            coreController.setEmptyGraph();
//            this.graphHolder.setGraph(coreController.setEmptyGraph());
//            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
        });

        graph1MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            coreController.setDefaultGraph1();
//            this.graphHolder.setGraph(coreController.setDefaultGraph1());
//            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
        });

        graph2MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            coreController.setDefaultGraph2();
//            this.graphHolder.setGraph(coreController.setDefaultGraph2());
//            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
        });

        graph3MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
            coreController.setDefaultGraph3();
//            this.graphHolder.setGraph();
//            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
        });

        graph4MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
//            this.graphHolder.setGraph(coreController.setDefaultGraph4());
//            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
        });

        graph5MenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
//            this.graphHolder.setGraph(coreController.setDefaultGraph5());
//            this.graphHolder.setState(coreController.getState());
            this.editGraphModeButton.setSelected(true);
        });

        randomGraphMenuItem.setOnAction(event -> {
            this.zoomScrollPane.clear();
//            this.graphHolder.setGraph(coreController.setRandomGraph());
//            this.graphHolder.setState(coreController.getState());
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
