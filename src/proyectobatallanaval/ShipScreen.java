package proyectobatallanaval;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    private ToggleGroup orientationGroup;

    public ShipScreen(String difficulty, String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        setDifficulty(difficulty);
        this.playerBoard = new int[gridSize][gridSize];
        initializeShipLimits();
    }

    private void setDifficulty(String difficulty) {
        switch (difficulty) {
            case "Easy" -> gridSize = 6;
            case "Medium" -> gridSize = 10;
            case "Hard" -> gridSize = 15;
            default -> gridSize = 10;
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

        Label shipInfo = new Label(
            "Barcos por colocar:\n" +
            "• Submarino (1 casilla) x4\n" +
            "• Destructor (2 casillas) x3\n" +
            "• Crucero (3 casillas) x2\n" +
            "• Acorazado (4 casillas) x1"
        );
        shipInfo.setStyle("-fx-font-size: 14px;");

        Label orientationLabel = new Label("Orientación:");
        orientationGroup = new ToggleGroup();
        RadioButton horizontalBtn = new RadioButton("Horizontal");
        RadioButton verticalBtn = new RadioButton("Vertical");
        horizontalBtn.setToggleGroup(orientationGroup);
        verticalBtn.setToggleGroup(orientationGroup);
        horizontalBtn.setSelected(true);
        HBox orientationBox = new HBox(10, orientationLabel, horizontalBtn, verticalBtn);
        orientationBox.setAlignment(Pos.CENTER);

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

        VBox layout = new VBox(15, turnLabel, shipInfo, orientationBox, gridPane, confirmButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        Scene scene = new Scene(layout, 400 + (gridSize * 10), 500 + (gridSize * 5));
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

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
        if (currentShipType.isEmpty()) return;

        int length = switch (currentShipType) {
            case "Submarine" -> 1;
            case "Destroyer" -> 2;
            case "Cruiser" -> 3;
            case "Battleship" -> 4;
            default -> 1;
        };

        boolean isHorizontal = ((RadioButton) orientationGroup.getToggles().get(0)).isSelected();

        if ((isHorizontal && col + length > gridSize) || (!isHorizontal && row + length > gridSize)) {
            ReproductorSonido.reproducir("errorAudio.mpeg");
            turnLabel.setText("¡No cabe el barco en esa posición!");
            return;
        }

        for (int i = 0; i < length; i++) {
            int r = isHorizontal ? row : row + i;
            int c = isHorizontal ? col + i : col;
            if (playerBoard[r][c] == 1) {
                ReproductorSonido.reproducir("errorAudio.mpeg");
                turnLabel.setText("¡Ya hay un barco en esa posición!");
                return;
            }
        }

        for (int i = 0; i < length; i++) {
            int r = isHorizontal ? row : row + i;
            int c = isHorizontal ? col + i : col;
            playerBoard[r][c] = 1;
            Rectangle cellRect = (Rectangle) getNodeFromGridPane(gridPane, c, r);
            cellRect.setFill(Color.GRAY);
        }
        ReproductorSonido.reproducir("boat-horn-307462.mp3");

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
                turnLabel.setText("¡Todos los barcos colocados! Haz clic en Confirm.");
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

        if (MenuScreen.gameModeGlobal.equals("Vs Bot")) {
            autoPlaceBotShips();
            
           
            ReproductorSonido.reproducir("startGame.mpeg");

            BattleScreen battleScreen = new BattleScreen(gridSize, player1Board, player2Board, player1Name, player2Name, MenuScreen.gameModeGlobal);
            Stage battleStage = new Stage();
            battleScreen.start(battleStage);
            mainStage.close();
        } else {
            setupPlacementScreen();
        }

    } else {
        player2Board = playerBoard;

       
        ReproductorSonido.reproducir("startGame.mpeg");

        BattleScreen battleScreen = new BattleScreen(gridSize, player1Board, player2Board, player1Name, player2Name, MenuScreen.gameModeGlobal);
        Stage battleStage = new Stage();
        battleScreen.start(battleStage);
        mainStage.close();
    }
}


    private void autoPlaceBotShips() {
        player2Board = new int[gridSize][gridSize];
        Map<String, Integer> limits = new HashMap<>(shipLimits);
        Random rand = new Random();

        for (Map.Entry<String, Integer> entry : limits.entrySet()) {
            String type = entry.getKey();
            int count = entry.getValue();
            int length = switch (type) {
                case "Submarine" -> 1;
                case "Destroyer" -> 2;
                case "Cruiser" -> 3;
                case "Battleship" -> 4;
                default -> 1;
            };

            for (int i = 0; i < count; i++) {
                boolean placed = false;
                while (!placed) {
                    boolean horizontal = rand.nextBoolean();
                    int row = rand.nextInt(gridSize);
                    int col = rand.nextInt(gridSize);

                    if ((horizontal && col + length > gridSize) || (!horizontal && row + length > gridSize)) continue;

                    boolean canPlace = true;
                    for (int j = 0; j < length; j++) {
                        int r = horizontal ? row : row + j;
                        int c = horizontal ? col + j : col;
                        if (player2Board[r][c] == 1) {
                            canPlace = false;
                            break;
                        }
                    }

                    if (canPlace) {
                        for (int j = 0; j < length; j++) {
                            int r = horizontal ? row : row + j;
                            int c = horizontal ? col + j : col;
                            player2Board[r][c] = 1;
                        }
                        placed = true;
                    }
                }
            }
        }
    }

    private javafx.scene.Node getNodeFromGridPane(GridPane grid, int col, int row) {
        for (javafx.scene.Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) != null &&
                GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}
