//package applet_two;
//
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.Region;
//import javafx.stage.Stage;
//
//public class Main extends Application {
//    private static Scene sceneTwo;
//    private Controller controllerTwo;
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    public static Scene getSceneTwo() {
//        return sceneTwo;
//    }
//
//    public void setScene(Scene scene) {
//        Main.sceneTwo = scene;
//    }
//
//    @Override
//    public void start(Stage stageTwo) throws Exception {
//        BorderPane rootTwo = new BorderPane();
//        sceneTwo = new Scene(rootTwo, 1600, 900);
//        rootTwo.prefWidthProperty().bind(sceneTwo.widthProperty());
//        rootTwo.prefHeightProperty().bind(sceneTwo.heightProperty());
//        rootTwo.setMaxWidth(Region.USE_PREF_SIZE);
//        rootTwo.setMaxHeight(Region.USE_PREF_SIZE);
//        stageTwo.setScene(sceneTwo);
//        stageTwo.setTitle("Lions In Plane");
//        stageTwo.setWidth(1600);
//        stageTwo.setHeight(900);
//        stageTwo.setResizable(true);
//        stageTwo.show();
//
//        controllerTwo = new Controller(stageTwo, rootTwo);
//    }
//}
