package proyectobatallanaval;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.util.*;

public class BattleScreen extends Application {

    private int gridSize;
    private int[][] player1Board, player2Board;
    private String player1Name, player2Name;
    private String gameMode;
    private int currentPlayer = 1;
    private GridPane player1Grid, player2Grid;
    private Label turnLabel;
    private Stage mainStage;
    private final Set<String> botShots = new HashSet<>();
    private final Random random = new Random();
    private final List<int[]> targetQueue = new ArrayList<>();
    private VBox player1Box, player2Box;

    private final String[] mensajesTocado = {
        "🎯 ¡Pum! Gabriel estaría diciendo: 'Eso fue... apenas aceptable'.",
        "🚀 ¡Le diste justo! Hairol lo habría esquivado, pero no este barco.",
        "🔥 ¡Impacto confirmado! El Dr. Douglas anotó un '+1' mental.",
        "💥 ¡Crack! Como cuando Gabriel suelta un examen y todos lloran.",
        "🧠 ¡Preciso! Hasta Hairol frunció el ceño de respeto.",
        "🎯 ¡Directo al CPU enemigo! Douglas lo convertiría en pregunta de examen.",
        "🧠 ¡Impacto validado! Hairol dijo: 'Está bonito'.",
        "🧨 ¡Tocado! Gabriel lo vio... y no dijo que le faltaba lógica. Increíble.",
        "🔥 ¡Fino! Así se juega en la UNA, no con juegos de consola, sino de consola de texto.",
        "💻 ¡Precisión UNA! Solo los que han pasado con Douglas entienden este nivel.",
        "🤖 ¡Boom! Hairol lo dijo: 'Está bonito'... y eso significa nivel Dios."
    };

    private final String[] mensajesAgua = {
        "💦 ¡Splash! Fallaste más que el WiFi en la UNA.",
        "🐟 ¡Solo mojaste peces! Gabriel lo llama desperdicio de recursos.",
        "💧 ¡Agua! Douglas murmuró: 'eso es un sacrilegio'.",
        "🤿 ¡Fallaste! Como cuando olvidas poner punto y coma.",
        "🚱 Fallaste. Gabriel ya estaba sacando la rúbrica de evaluación.",
        "📉 Nada por aquí. Hairol miró el código y dijo: 'le falta lógica'.",
        "🧊 ¡Error! Douglas anotó eso como ejemplo de lo que no se hace.",
        "😐 Gabriel revisó el disparo... y repitió: 'le falta lógica'. Dos veces.",
        "🧠 ¡Nada! Ni Hairol te salvó con un 'está bonito'. Eso duele."
    };

    private final String[] mensajesHundido = {
        "🚢 ¡Hundido! Gabriel lo aceptó... pero dijo que aún puede mejorar.",
        "💣 ¡Boom! Hairol dijo: 'está bonito', y eso es oro puro.",
        "🧠 ¡Explosión precisa! Douglas lo demostraría con un teorema.",
        "⚓ ¡Barco al fondo! Gabriel no encontró errores. Inédito.",
        "🔥 ¡Acorazado destruido! Nivel de tesis con Douglas.",
        "⛴️ ¡Impacto total! Hairol ya quiere ver el pseudocódigo.",
        "📈 ¡Ejecutado con lógica! Gabriel no dijo su frase favorita.",
        "💥 ¡Hundido! Douglas simplemente dijo: 'Correcto'. Y eso es histórico.",
        "🎓 ¡Perfección UNA! El barco cayó con elegancia, como algoritmo bien escrito.",
        "📌 ¡Fin del barco! Hairol se rió... y dijo 'está bonito'."
    };

    public BattleScreen(int gridSize, int[][] player1Board, int[][] player2Board, String player1Name, String player2Name, String gameMode) {
        this.gridSize = gridSize;
        this.player1Board = player1Board;
        this.player2Board = player2Board;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.gameMode = gameMode;
    }

    @Override
    public void start(Stage primaryStage) {
        this.mainStage = primaryStage;
        setupGameScreen();
    }

    private void setupGameScreen() {
        turnLabel = new Label("Turno: " + getCurrentPlayerName());
        turnLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        player1Grid = createPlayerGrid(player1Board, false);
        player2Grid = createPlayerGrid(player2Board, false);

        player1Box = new VBox(new Label(player1Name + " (Tú)"), player1Grid);
        player2Box = new VBox(new Label(player2Name + (gameMode.equals("Vs Bot") ? " (Bot)" : " (Enemigo)")), player2Grid);
        HBox boards = new HBox(50, player1Box, player2Box);

        Button verTableroBot = new Button("Ver Tablero del Bot");
        verTableroBot.setOnAction(e -> mostrarTableroBot());
        if (!gameMode.equals("Vs Bot")) {
            verTableroBot.setVisible(false);
        }

        VBox layout = new VBox(15, turnLabel, boards, verTableroBot);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        Scene scene = new Scene(layout, 900, 650);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        mainStage.setTitle("Batalla Naval");
        mainStage.setScene(scene);
        mainStage.show();

        actualizarTableros(); 
    }

    private void mostrarTableroBot() {
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                info.append(player2Board[i][j] == 1 ? "■ " : "~ ");
            }
            info.append("\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, info.toString(), ButtonType.OK);
        alert.setTitle("Tablero del Bot");
        alert.setHeaderText("Ubicación de los barcos del bot:");
        alert.showAndWait();
    }

    private GridPane createPlayerGrid(int[][] board, boolean dummyFlag) {
        GridPane grid = new GridPane();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Rectangle cell = new Rectangle(30, 30);
                cell.setStroke(Color.BLACK);
                cell.setFill(getCellColor(board[row][col]));
                final int r = row, c = col;
                cell.setOnMouseClicked(e -> handlePlayerShot(cell, r, c, board));
                grid.add(cell, col, row);
            }
        }
        return grid;
    }

    private Color getCellColor(int value) {
        return switch (value) {
            case 2 -> Color.RED;
            case 3 -> Color.BLUE;
            default -> Color.LIGHTBLUE;
        };
    }

    private void handlePlayerShot(Rectangle cell, int row, int col, int[][] targetBoard) {
        
        if ((currentPlayer == 1 && targetBoard != player2Board) || (currentPlayer == 2 && targetBoard != player1Board)) {
            showMessage("¡No es tu turno!");
            return;
        }

        if (targetBoard[row][col] == 2 || targetBoard[row][col] == 3) {
            return; 
        }

        boolean hit = targetBoard[row][col] == 1;

        if (hit) {
            targetBoard[row][col] = 2;
            cell.setFill(Color.RED);
            ReproductorSonido.reproducir("distant-explosion-47562.mp3");
            showMessage(getMensajeRandom(mensajesTocado));

            if (isShipSunk(targetBoard)) {
                showMessage(getMensajeRandom(mensajesHundido));
            }
        } else {
            targetBoard[row][col] = 3;
            cell.setFill(Color.BLUE);
            ReproductorSonido.reproducir("water-splash-199583.mp3");
            showMessage(getMensajeRandom(mensajesAgua));
            switchTurn();
        }

        if (isGameOver()) {
            showWinner();
            return;
        }

        if (gameMode.equals("Vs Bot") && currentPlayer == 2) {
            botTurn();
        }
    }

    private String getMensajeRandom(String[] mensajes) {
        return mensajes[random.nextInt(mensajes.length)];
    }

    private void botTurn() {
        while (true) {
            int row = -1, col = -1;
            String diff = MenuScreen.difficultyGlobal;

            if (!targetQueue.isEmpty() && !diff.equals("Easy")) {
                int[] next = targetQueue.remove(0);
                row = next[0];
                col = next[1];
            } else {
                do {
                    row = random.nextInt(gridSize);
                    col = random.nextInt(gridSize);
                } while (botShots.contains(row + "," + col));
            }

            botShots.add(row + "," + col);

            if (player1Board[row][col] == 1) {
                player1Board[row][col] = 2;
                Rectangle cell = (Rectangle) getNodeFromGridPane(player1Grid, col, row);
                cell.setFill(Color.RED);
                ReproductorSonido.reproducir("distant-explosion-47562.mp3");
                showMessage("El bot dice: " + getMensajeRandom(mensajesTocado));

                if (!diff.equals("Easy")) {
                    for (int[] d : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                        int newRow = row + d[0];
                        int newCol = col + d[1];
                        if (isInBounds(newRow, newCol) && !botShots.contains(newRow + "," + newCol)) {
                            targetQueue.add(new int[]{newRow, newCol});
                        }
                    }
                }

                if (isShipSunk(player1Board)) {
                    showMessage("El bot dice: " + getMensajeRandom(mensajesHundido));
                    if (isGameOver()) {
                        showWinner();
                        return;
                    }
                }

                continue;
            } else if (player1Board[row][col] == 0) {
                player1Board[row][col] = 3;
                Rectangle cell = (Rectangle) getNodeFromGridPane(player1Grid, col, row);
                cell.setFill(Color.BLUE);
                ReproductorSonido.reproducir("water-splash-199583.mp3");
                showMessage("El bot dice: " + getMensajeRandom(mensajesAgua));
                switchTurn();
                break;
            } else {
                break;
            }
        }
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    private javafx.scene.Node getNodeFromGridPane(GridPane grid, int col, int row) {
        for (javafx.scene.Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private boolean isShipSunk(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 1) return false;
            }
        }
        return true;
    }

    private boolean isGameOver() {
        return isShipSunk(player1Board) || isShipSunk(player2Board);
    }

    private void showMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void showWinner() {
    String message;
    boolean p1Wins = isShipSunk(player2Board);
    boolean p2Wins = isShipSunk(player1Board);

    if (p1Wins && p2Wins) {
        message = "¡Empate!";
    } else if (p1Wins) {
        message = "¡Ganador: " + player1Name + "!";
    } else {
        message = "¡Ganador: " + player2Name + "!";
    }

    ReproductorSonido.reproducir("victorymale-version-230553.mp3");

    Alert alert = new Alert(Alert.AlertType.INFORMATION, message + "\n¿Jugar otra vez?", ButtonType.YES, ButtonType.NO);
    alert.setHeaderText(null);
    alert.showAndWait().ifPresent(resp -> {
        if (resp == ButtonType.YES) {
            MainScreen main = new MainScreen();
            Stage newStage = new Stage();
            main.start(newStage);
        }
        mainStage.close();
    });
}


    private void switchTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        turnLabel.setText("Turno: " + getCurrentPlayerName());

        FadeTransition ft = new FadeTransition(Duration.millis(500), turnLabel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        player1Box.setStyle("-fx-background-color: " + (currentPlayer == 1 ? "#e3f2fd" : "transparent"));
        player2Box.setStyle("-fx-background-color: " + (currentPlayer == 2 ? "#e3f2fd" : "transparent"));

        actualizarTableros();
    }

    private void actualizarTableros() {
        player1Grid = createPlayerGrid(player1Board, false);
        player2Grid = createPlayerGrid(player2Board, false);
        player1Box.getChildren().set(1, player1Grid);
        player2Box.getChildren().set(1, player2Grid);
    }

    private String getCurrentPlayerName() {
        return (currentPlayer == 1) ? player1Name : player2Name;
    }
    
}

