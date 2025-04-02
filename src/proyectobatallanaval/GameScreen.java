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
import java.util.Random;

public class GameScreen extends Application {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 30;
    private static final int[] SHIPS = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; 
    private boolean[][] player1Board = new boolean[GRID_SIZE][GRID_SIZE];
    private boolean[][] player2Board = new boolean[GRID_SIZE][GRID_SIZE];
    private boolean player1Turn = true;
    private GridPane player1Grid = new GridPane();
    private GridPane player2Grid = new GridPane();
    private Label statusLabel = new Label("Player 1's Turn");
    
    @Override
    public void start(Stage primaryStage) {
        setupGrid(player1Grid, player1Board);
        setupGrid(player2Grid, player2Board);

        Button switchTurn = new Button("End Turn");
        switchTurn.setOnAction(e -> switchPlayer());

        VBox layout = new VBox(10, statusLabel, player1Grid, switchTurn);
        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setTitle("BattleShip Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupGrid(GridPane grid, boolean[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.LIGHTBLUE);
                cell.setStroke(Color.BLACK);
                
                final int r = row, c = col;
                cell.setOnMouseClicked(e -> handleShot(cell, r, c, board));
                grid.add(cell, col, row);
            }
        }
    }

    private void handleShot(Rectangle cell, int row, int col, boolean[][] board) {
        if (board[row][col]) {
            cell.setFill(Color.RED);
            statusLabel.setText("Hit!");
        } else {
            cell.setFill(Color.GRAY);
            statusLabel.setText("Miss!");
            switchPlayer();
        }
    }

    private void switchPlayer() {
        player1Turn = !player1Turn;
        statusLabel.setText(player1Turn ? "Player 1's Turn" : "Player 2's Turn");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

