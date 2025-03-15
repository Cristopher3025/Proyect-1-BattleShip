package proyectobatallanaval;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuScreen extends Application {

    private String playerName = ""; 
    private String difficulty = "Medium"; 
    private String gameMode = "Vs Bot";

    @Override
    public void start(Stage primaryStage) {
        
        Label title = new Label("Battleship - Main Menu");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

       
        Label nameLabel = new Label("Enter your name:");
        TextField nameInput = new TextField();
        nameInput.setPromptText("Your name here");

        
        Label difficultyLabel = new Label("Select Difficulty:");
        ChoiceBox<String> difficultyBox = new ChoiceBox<>();
        difficultyBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyBox.setValue("Medium"); 

        
        Label modeLabel = new Label("Select Game Mode:");
        ChoiceBox<String> modeBox = new ChoiceBox<>();
        modeBox.getItems().addAll("Vs Bot", "Vs Human");
        modeBox.setValue("Vs Bot"); 

      
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            playerName = nameInput.getText();
            difficulty = difficultyBox.getValue();
            gameMode = modeBox.getValue();

            
            ShipScreen shipScreen = new ShipScreen(difficulty, gameMode, playerName);
            shipScreen.start(new Stage());
            primaryStage.close();
        });

       
        VBox layout = new VBox(15, title, nameLabel, nameInput, difficultyLabel, difficultyBox, modeLabel, modeBox, startButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        
        Scene scene = new Scene(layout, 400, 350);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
