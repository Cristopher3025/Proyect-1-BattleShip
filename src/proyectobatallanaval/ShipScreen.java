package proyectobatallanaval;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ShipScreen extends Application {
    private int gridSize;
    private int[][] playerBoard;
    private boolean isPlayer1Turn = true;
    private String player1Name, player2Name;
    private GridPane gridPane;
    private Label turnLabel;
    private Stage mainStage;
    private int placedShips = 0;
    private String currentShipType = "Submarine";
    private Map<String, Integer> shipLimits = new HashMap<>();
    private int[][] player1Board;
    private int[][] player2Board;

    public ShipScreen(String difficulty, String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        setDifficulty(difficulty);
        this.playerBoard = new int[gridSize][gridSize];
        initializeShipLimits();
    }

    private void setDifficulty(String difficulty) {
        switch (difficulty) {
            case "Easy": gridSize = 6; break;
            case "Medium": gridSize = 10; break;
            case "Hard": gridSize = 15; break;
            default: gridSize = 10;
        }
        player1Board = new int[gridSize][gridSize];
        player2Board = new int[gridSize][gridSize];
    }

    private void initializeShipLimits() {
        shipLimits.put("Submarine", 4);
        shipLimits.put("Destroyer", 3);
        shipLimits.put("Cruiser", 2);
        shipLimits.put("Battleship", 1);
    }

    @Override
    public void start(Stage primaryStage) {
        this.mainStage = primaryStage;
        setupPlacementScreen();
    }

    private void setupPlacementScreen() {
        turnLabel = new Label("Turn: " + (isPlayer1Turn ? player1Name : player2Name) + " - " + currentShipType);
        turnLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        gridPane = new GridPane();
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setAlignment(Pos.CENTER);
        
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Rectangle cell = createCell(row, col);
                gridPane.add(cell, col, row);
            }
        }

        Button confirmButton = new Button("Confirm Placement");
        confirmButton.setOnAction(e -> switchTurnOrStartBattle());
        
        VBox layout = new VBox(15, turnLabel, gridPane, confirmButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");
        
        Scene scene = new Scene(layout, 400 + (gridSize * 10), 500 + (gridSize * 5)); 
        mainStage.setTitle("Ship Placement");
        mainStage.setScene(scene);
        mainStage.show();
    }

    private Rectangle createCell(int row, int col) {
        Rectangle cell = new Rectangle(30, 30);
        cell.setStroke(Color.BLACK);
        cell.setFill(Color.LIGHTGRAY);
        cell.setOnMouseClicked(e -> placeShip(cell, row, col));
        return cell;
    }

    private void placeShip(Rectangle cell, int row, int col) {
        if (placedShips >= shipLimits.get(currentShipType) || playerBoard[row][col] == 1) {
            return;
        }
        playerBoard[row][col] = 1;
        cell.setFill(Color.GRAY);
        placedShips++;
        if (placedShips >= shipLimits.get(currentShipType)) {
            switchToNextShip();
        }
    }

    private void switchToNextShip() {
        placedShips = 0;
        currentShipType = switch (currentShipType) {
            case "Submarine" -> "Destroyer";
            case "Destroyer" -> "Cruiser";
            case "Cruiser" -> "Battleship";
            default -> {
                turnLabel.setText("All ships placed! Confirm to continue.");
                yield "";
            }
        };
        if (!currentShipType.isEmpty()) {
            turnLabel.setText("Turn: " + (isPlayer1Turn ? player1Name : player2Name) + " - " + currentShipType);
        }
    }

    private void switchTurnOrStartBattle() {
        if (isPlayer1Turn) {
            player1Board = playerBoard;
            isPlayer1Turn = false;
            currentShipType = "Submarine";
            placedShips = 0;
            playerBoard = new int[gridSize][gridSize];
            setupPlacementScreen();
        } else {
            player2Board = playerBoard;
            BattleScreen battleScreen = new BattleScreen(gridSize, player1Board, player2Board, player1Name, player2Name);
            Stage battleStage = new Stage();
            battleScreen.start(battleStage);
            mainStage.close();
        }
    }
}
