package cz.vse.basi02.adventura4it115.main;

import cz.vse.basi02.adventura4it115.Game;
import cz.vse.basi02.adventura4it115.ui.TextInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Spouštěcí třída aplikace.
 *
 * @author Ivan Bassov
 * @version ZS-2022 - 2023, 04.01.2023
 */
public class Start extends Application

{
    public static boolean grafickeRozhrani;

    /**
     * Spouštěcí metoda aplikace. Vytvoří instanci hry, uživatelského rozhraní a zahájí hru.
     *
     * @param args parametry aplikace z příkazové řádky, aktuálně se nijak nevyužívají
     */
    public static void main(String[] args) {

        if(args.length>0 &&args[0].equals("text")) {
            Game game = new Game();
            TextInterface ui = new TextInterface(game);
            grafickeRozhrani = false;
            ui.play();
            Platform.exit();
        }
        else{
            launch();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneController sceneController = new SceneController();
        sceneController.interfaceGameStart(primaryStage);
        grafickeRozhrani = true;
    }
}
