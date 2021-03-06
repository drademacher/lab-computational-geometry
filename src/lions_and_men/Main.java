package lions_and_men;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lions_and_men.applet_graph.UserInterface;

public class Main extends Application {
    private static Stage stageChoose;
    private static Stage stageOne;
    private static Stage stageTwo;

    public static void showChooser() {
        stageOne.hide();
        stageTwo.hide();
        stageChoose.show();
    }

    @Override
    public void start(Stage stageMain) throws Exception {
        // stage one
        stageOne = new Stage();
        BorderPane rootOne = new BorderPane();
        Scene sceneOne = new Scene(rootOne, 1600, 900, Color.WHITE);
        rootOne.prefWidthProperty().bind(sceneOne.widthProperty());
        rootOne.prefHeightProperty().bind(sceneOne.heightProperty());
        rootOne.setMaxWidth(Region.USE_PREF_SIZE);
        rootOne.setMaxHeight(Region.USE_PREF_SIZE);
        stageOne.setScene(sceneOne);
        stageOne.setTitle("Lions On Graph");
        stageOne.setWidth(1600);
        stageOne.setHeight(900);
        stageOne.setResizable(true);
        new UserInterface(stageOne, rootOne);

        // stage two
        stageTwo = new Stage();
        BorderPane rootTwo = new BorderPane();
        Scene sceneTwo = new Scene(rootTwo, 1600, 900, Color.WHITE);
        rootTwo.prefWidthProperty().bind(sceneTwo.widthProperty());
        rootTwo.prefHeightProperty().bind(sceneTwo.heightProperty());
        rootTwo.setMaxWidth(Region.USE_PREF_SIZE);
        rootTwo.setMaxHeight(Region.USE_PREF_SIZE);
        stageTwo.setScene(sceneTwo);
        stageTwo.setTitle("Lions In Plane");
        stageTwo.setWidth(1600);
        stageTwo.setHeight(900);
        stageTwo.setResizable(true);
        new lions_and_men.applet_plane.UserInterface(stageTwo, rootTwo);


        stageChoose = new Stage();
        BorderPane rootChoose = new BorderPane();
        Scene sceneChoose = new Scene(rootChoose, 1600, 900, Color.WHITE);
        rootTwo.prefWidthProperty().bind(sceneTwo.widthProperty());
        rootTwo.prefHeightProperty().bind(sceneTwo.heightProperty());
        rootTwo.setMaxWidth(Region.USE_PREF_SIZE);
        rootTwo.setMaxHeight(Region.USE_PREF_SIZE);
        stageChoose.setScene(sceneChoose);
        stageChoose.setTitle("Choose Application");
        stageChoose.setWidth(1600);
        stageChoose.setHeight(900);
        stageChoose.setResizable(true);


        // top text
        Label infobox = new Label("Choose one application to start.");
        infobox.setStyle("-fx-font-size: 36; -fx-font-weight: bold;");
        rootChoose.setTop(infobox);

        // left
        Button chooseOne = new Button("Lions On Graph");
        chooseOne.setOnAction(event -> {
            stageChoose.hide();
            stageOne.show();
        });

        Label labelOne = new Label("App visualizes algorithms of the 'Lions And Men' problem on arbitrary graphs with discrete time steps and equal speed of lions and men. The proposed algorithm helps the man escaping from two lions.");
        labelOne.setWrapText(true);
        labelOne.setMaxWidth(400);
        labelOne.setPadding(new Insets(50, 10, 10, 10));

        VBox boxOne = new VBox(chooseOne, labelOne);
        boxOne.setAlignment(Pos.CENTER);
        rootChoose.setLeft(boxOne);

        // right
        Button chooseTwo = new Button("Lions In Plane");

        chooseTwo.setOnAction(event -> {
            stageChoose.hide();
            stageTwo.show();
        });

        Label labelTwo = new Label("App visualizes algorithms of the 'Lions And Men' problem in the infinite plane. The proposed algorithm works for one man and an arbitrary amount of lions. The man is faster than the lions.");
        labelTwo.setWrapText(true);
        labelTwo.setMaxWidth(400);
        labelTwo.setPadding(new Insets(50, 10, 10, 10));

        VBox boxTwo = new VBox(chooseTwo, labelTwo);
        boxTwo.setAlignment(Pos.CENTER);
        rootChoose.setRight(boxTwo);


        rootChoose.setStyle("-fx-font-size: 24;");
        rootChoose.setPadding(new Insets(150, 50, 150, 50));
        BorderPane.setAlignment(infobox, Pos.CENTER);
        BorderPane.setAlignment(boxOne, Pos.CENTER);
        BorderPane.setAlignment(boxTwo, Pos.CENTER);

        stageChoose.show();
    }
}
