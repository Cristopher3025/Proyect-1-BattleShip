package proyectobatallanaval;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BattleScreen extends Application {
    private int gridSize;
    private int[][] player1Board, player2Board;
    private String player1Name, player2Name;
    private int currentPlayer = 1;
    private GridPane player1Grid, player2Grid;
    private Label turnLabel;
    private Stage mainStage;

    public BattleScreen(int gridSize, int[][] player1Board, int[][] player2Board, String player1Name, String player2Name) {
        this.gridSize = gridSize;
        this.player1Board = player1Board;
        this.player2Board = player2Board;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    @Override
    public void start(Stage primaryStage) {
        this.mainStage = primaryStage;
        showBattleScreen();
    }

    private void showBattleScreen() {
        turnLabel = new Label("Turn: " + (currentPlayer == 1 ? player1Name : player2Name));
        turnLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        player1Grid = createPlayerGrid(player1Board, currentPlayer == 1);
        player2Grid = createPlayerGrid(player2Board, currentPlayer == 2);

        VBox player1Box = new VBox(new Label(player1Name + " Board"), player1Grid);
        VBox player2Box = new VBox(new Label(player2Name + " Board"), player2Grid);
        HBox boardContainer = new HBox(50, player1Box, player2Box);

        VBox layout = new VBox(15, turnLabel, boardContainer);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        Scene scene = new Scene(layout, 900, 600);
        mainStage.setTitle("Battle Phase");
        mainStage.setScene(scene);
        mainStage.show();
    }

    private GridPane createPlayerGrid(int[][] board, boolean isCurrentPlayer) {
        GridPane grid = new GridPane();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Rectangle cell = new Rectangle(30, 30);
                cell.setStroke(Color.BLACK);
                cell.setFill(getCellColor(board[row][col]));

                if (!isCurrentPlayer) {
                    final int finalRow = row;
                    final int finalCol = col;
                    cell.setOnMouseClicked(e -> handleShot(cell, finalRow, finalCol));
                }
                grid.add(cell, col, row);
            }
        }
        return grid;
    }

    private Color getCellColor(int cellValue) {
        return switch (cellValue) {
            case 2 -> Color.RED; 
            case 3 -> Color.BLUE;
            default -> Color.LIGHTBLUE; 
        };
    }

    private void handleShot(Rectangle cell, int row, int col) {
    int[][] enemyBoard = (currentPlayer == 1) ? player2Board : player1Board;

    if (enemyBoard[row][col] == 1) {
        cell.setFill(Color.RED); // Golpe al barco
        enemyBoard[row][col] = 2; // Actualiza el estado de golpe
    } else if (enemyBoard[row][col] == 0) {
        cell.setFill(Color.BLUE); // Agua
        enemyBoard[row][col] = 3; // Actualiza el estado de agua
    }

    switchTurn();
}


    private void switchTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        showBattleScreen();
    }
}
