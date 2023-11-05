package cz.vse.basi02.adventura4it115.main;

import cz.vse.basi02.adventura4it115.Game;
import cz.vse.basi02.adventura4it115.Item;
import cz.vse.basi02.adventura4it115.Location;
import cz.vse.basi02.adventura4it115.Plot;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.util.*;
import java.util.stream.Collectors;

public class RootController implements Observer {
    public ImageView background;
    public ListView listItems;
    public Button inventoryButton;
    public ImageView itemsImage;
    @FXML
    private ImageView player;
    @FXML
    private ListView listVychodu;
    @FXML
    private Button buttonSend;
    @FXML
    public TextArea vystup;
    @FXML
    public  TextField vstup;
    private Game game = new Game();
    @FXML
    private ObservableList<String> vychody = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> items = FXCollections.observableArrayList();

    private Map<String, Point2D> coordinates = new HashMap<>();

    static RootController rootController = new RootController();

    public static RootController getRootController() {
        return rootController;
    }

    // public TextField getVstup(){
  //      return vstup;
   // }

    //  public static String getVstupText(TextField vstup) {
     //   String text = vstup.getText();
     //   return text;
    //}

    @FXML
    private void initialize() {
        vystup.appendText(game.getPrologue() + "\n\n");
        Platform.runLater(() -> vstup.requestFocus());
        listVychodu.setItems(vychody);
        listItems.setItems(items);
        game.getGameWorld().observe(GameChange.LOCATION_CHANGE, this);
        game.getGameWorld().observe(GameChange.LOCATION_CHANGE, () -> {
            updateExitList();
            initializePlayerLocation();
            updateItemList();
        });
        game.observe(GameChange.GAME_END, () -> updateGameEnd());
        updateExitList();
        coordinates.put("hala", new Point2D(135, 57));
        coordinates.put("východ", new Point2D(129, 14));
        coordinates.put("kuchyn", new Point2D(25, 80));
        coordinates.put("ložnice", new Point2D(294, 74));
        coordinates.put("obývák", new Point2D(137, 79));
        coordinates.put("koridor", new Point2D(206, 76));
        coordinates.put("sklad", new Point2D(74, 155));
        coordinates.put("knihovna", new Point2D(254, 158));
        coordinates.put("suterén", new Point2D(77, 198));
    }

    private void initializePlayerLocation() {
        String location = game.getGameWorld().getCurrentLocation().getName();
        player.setLayoutX(coordinates.get(location).getX());
        player.setLayoutY(coordinates.get(location).getY());

    }

    private void updateExitList() {
        vychody.clear();
        vychody.addAll(game.getGameWorld().getCurrentLocation().getExitList());
    }

    public void updateItemList(){
        items.clear();
        items.addAll(game.getGameWorld().getInventory().getInventoryList());
        backtoDefaultImage();
    }


    public void showHelp(ActionEvent actionEvent) {
        vystup.appendText(" \n Musíš najít klíč a dostat se z tohoto děsivého domu. \n" +
                "Seznam dostupných užitečných příkazů : \n" +
                "jdi\n" +
                "inventář\n" +
                "mapa\n" +
                "lokace");
    }

    public void endGame(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void sendInput(ActionEvent actionEvent) {
        String command = vstup.getText();
        vstup.clear();
        proccesCommand(command);
    }

    private void proccesCommand(String command) {
        vystup.appendText(">" + command + "\n");
        String vysledek = game.processCommand(command);
        vystup.appendText(vysledek + "\n\n");
    }

    public void showMap(ActionEvent actionEvent) {
        vystup.appendText(game.getGameWorld().getMap());
    }

    @Override
    public void update() {
        updateGameEnd();
    }

    private void updateGameEnd() {
        if (game.isGameOver()) {
            vystup.appendText(game.getEpilogue());
            vstup.setDisable(true);
            buttonSend.setDisable(true);
            listVychodu.setDisable(true);

        }
    }

    @FXML
    private void ClicklPanelExit(MouseEvent mouseEvent) {
        Object target = listVychodu.getSelectionModel().getSelectedItem();
        if (target == null) {
            return;
        } else {
            String command = "jdi " + target;
            proccesCommand(command);

            String filename = target + ".jpg";
            Image icon = new Image(getClass().getResource("/cz/vse/basi02/adventura4it115/main/" + filename).toExternalForm());
            background.setImage(icon);
        }
    }

    public void ClickPanelItems(MouseEvent mouseEvent) {
        Object target = listItems.getSelectionModel().getSelectedItem();
        if (target == null) {
            return;
        } else {
          String command = "použij " + target;
          proccesCommand(command);

            String filename = target + ".jpg";
            Image icon = new Image(getClass().getResource("/cz/vse/basi02/adventura4it115/main/" + filename).toExternalForm());
            itemsImage.setImage(icon);
        }
    }

    public void backtoDefaultImage(){
        Image defaultImage = new Image(getClass().getResource("/cz/vse/basi02/adventura4it115/main/background_item.jpg" ).toExternalForm());
        itemsImage.setImage(defaultImage);
    }

    public String firstRiddle(){
        Location currentLocation = game.getGameWorld().getCurrentLocation();
        game.getPlot().setRiddleIndex(1);
        vystup.appendText(game.getPlot().getFirstRiddleText());
        readInput();
        if(game.isGameOver()){
            if(game.getPlot().shouldBeDead){
                return game.getEpilogue();
            }
            return "";
        }
        currentLocation.addItem(new Item("lampa", true));
      //  currentLocation.removeItem(item);
        game.getPlot().isAnswered = false;
        game.getPlot().setRiddleIndex(0);;
        return "Hlas se rozloučil a zmizel.\n" +
                "Otevřel jsi truhlu a v ní jsi našel lampu!\n" +
                "Předměty v místnosti: " +
                game.getGameWorld().getCurrentLocation().getItems().stream().map(Item::getName).collect(Collectors.joining(", "));
    }



    private void readInput() {
        buttonSend.setDisable(true);
        vstup.requestFocus();

        vstup.setOnAction(event -> {
            String commandName = vstup.getText().trim();
            vstup.clear();

            String[] possibleCommands = new String[5];
            possibleCommands[0] = game.getCommand("stres").getName();
            possibleCommands[1] = game.getCommand("inventář").getName();
            possibleCommands[2] = game.getCommand("použij").getName();
            possibleCommands[3] = game.getCommand("odpověz").getName();
            possibleCommands[4] = game.getCommand("konec").getName();

            boolean commandFound = false;
            for (int i = 0; i < 5; i++) {
                if (possibleCommands[i].equals(commandName)) {
                    commandFound = true;
                    break;
                }
            }

            if (commandFound) {
                proccesCommand(commandName);
            } else {
                vystup.appendText("Tomu teď nerozumím, nejdříve odpověz na hádanku!\n");
            }

            if (commandName.equals("konec") || game.getPlot().shouldBeDead) {
                game.setGameOver(true);
            }
        });
    }
}