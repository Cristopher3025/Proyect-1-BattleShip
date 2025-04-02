package proyectobatallanaval;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuScreen extends Application {
    private String player1Name = "";
    private String player2Name = "";
    private String difficulty = "Medium";
    private String gameMode = "Vs Bot";

    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("Battleship - Main Menu");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Label nameLabel1 = new Label("Player 1 Name:");
        TextField nameInput1 = new TextField();
        nameInput1.setPromptText("Enter name");
        
        Label nameLabel2 = new Label("Player 2 Name:");
        TextField nameInput2 = new TextField();
        nameInput2.setPromptText("Enter name");
        nameLabel2.setVisible(false);
        nameInput2.setVisible(false);

        Label difficultyLabel = new Label("Select Difficulty:");
        ChoiceBox<String> difficultyBox = new ChoiceBox<>();
        difficultyBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyBox.setValue("Medium");
        
        Label modeLabel = new Label("Select Game Mode:");
        ChoiceBox<String> modeBox = new ChoiceBox<>();
        modeBox.getItems().addAll("Vs Bot", "Vs Human");
        modeBox.setValue("Vs Bot");

        modeBox.setOnAction(e -> {
            boolean isHumanMode = modeBox.getValue().equals("Vs Human");
            nameLabel2.setVisible(isHumanMode);
            nameInput2.setVisible(isHumanMode);
        });

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            player1Name = nameInput1.getText();
            player2Name = modeBox.getValue().equals("Vs Human") ? nameInput2.getText() : "Computer";
            difficulty = difficultyBox.getValue();
            
            ShipScreen shipScreen = new ShipScreen(difficulty, player1Name, player2Name);
            Stage shipStage = new Stage();
            shipScreen.start(shipStage);
            primaryStage.close();
        });

        VBox layout = new VBox(15, title, nameLabel1, nameInput1, nameLabel2, nameInput2, difficultyLabel, difficultyBox, modeLabel, modeBox, startButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");
        
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
