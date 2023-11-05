package cz.vse.basi02.adventura4it115.main;

import cz.vse.basi02.adventura4it115.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Game game = new Game();

    public void interfaceGameStart(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent loadScene = loader.load(getClass().getResource("loadScreen.fxml"));
        Scene openScene = new Scene(loadScene);
        //openScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        String css = this.getClass().getResource("styles.css").toExternalForm();
        openScene.getStylesheets().add(css);

        Image icon = new Image(getClass().getResource("/cz/vse/basi02/adventura4it115/main/8537038.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("The Unknown House Escape");
        primaryStage.setScene(openScene);
        //  primaryStage.setFullScreen(true);
        // primaryStage.setFullScreenExitHint("YOU CAN'T ESCAPE \n" + "But you can try *q*uit the game, who knows...");
        // primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));

        primaryStage.show();
    }

    public void switchScenePlay(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent loadScene = loader.load(getClass().getResource("root.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(loadScene);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.show();
    }



}
