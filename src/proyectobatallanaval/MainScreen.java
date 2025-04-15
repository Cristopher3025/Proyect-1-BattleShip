package proyectobatallanaval;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("Welcome to Battleship!");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button btnStart = new Button("Start Game");
        btnStart.setOnAction(e -> {
            MenuScreen menuScreen = new MenuScreen();
            menuScreen.start(new Stage());
            primaryStage.close();
        });

        Button aboutButton = new Button("Acerca de...");
        aboutButton.setOnAction(e -> showAbout());

        Label credit = new Label("Desarrollado por Cristopher Ureña – UNA");
        credit.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        VBox layout = new VBox(20, title, btnStart, aboutButton, credit);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca del Juego");
        alert.setHeaderText("Información del creador");
        alert.setContentText(
                "Este juego fue desarrollado por Cristopher Ureña.\n" +
                "Estudiante de Ingeniería en Sistemas en la Universidad Nacional de Costa Rica (UNA).\n\n" +
                "Apasionado por la filosofía, los libros con profundidad intelectual y el buen vino.\n" +
                "Con este proyecto, busca unir lógica, diseño y un toque personal.\n\n" +
                "Juego: Batalla Naval\n" +
                "Desarrollado con Java y JavaFX.\n\n" +
                "Gracias por probar esta experiencia."
        );
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

