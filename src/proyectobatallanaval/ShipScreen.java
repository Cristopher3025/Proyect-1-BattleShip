package proyectobatallanaval;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ShipScreen extends Application {

    private String difficulty;
    private String gameMode;
    private String playerName;
    private int gridSize; 
    private static final int CELL_SIZE = 30; 

    public ShipScreen(String difficulty, String gameMode, String playerName) {
        this.difficulty = difficulty;
        this.gameMode = gameMode;
        this.playerName = playerName;

        
        switch (difficulty) {
            case "Easy":
                gridSize = 6; 
                break;
            case "Medium":
                gridSize = 10; 
                break;
            case "Hard":
                gridSize = 15; 
                break;
            default:
                gridSize = 10; 
        }
    }

    @Override
    public void start(Stage primaryStage) {
        
        Label title = new Label("Place Your Ships, " + playerName + "!");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

       
        Label info = new Label("Difficulty: " + difficulty + " | Mode: " + gameMode);

        
        GridPane grid = new GridPane();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.LIGHTBLUE); 
                cell.setStroke(Color.BLACK);   

                
                cell.setOnMouseClicked(e -> {
                    cell.setFill(Color.GRAY); 
                });

                grid.add(cell, col, row);
            }
        }

        
        Button btnBack = new Button("Back to Main Menu");
        btnBack.setOnAction(e -> {
            MenuScreen menuScreen = new MenuScreen();
            menuScreen.start(new Stage());
            primaryStage.close();
        });

        
        VBox layout = new VBox(15, title, info, grid, btnBack);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

       
        Scene scene = new Scene(layout, 600, 600); 
        primaryStage.setTitle("Ship Placement");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

