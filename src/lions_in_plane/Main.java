package lions_in_plane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Main extends Application {
    private static Scene scene;
    private Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    public static Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        Main.scene = scene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        scene = new Scene(root, 1600, 900);
        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());
        root.setMaxWidth(Region.USE_PREF_SIZE);
        root.setMaxHeight(Region.USE_PREF_SIZE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lions In Plane");
        primaryStage.setWidth(1600);
        primaryStage.setHeight(900);
        primaryStage.setResizable(true);
        primaryStage.show();

        controller = new Controller(primaryStage, root);
    }
}
