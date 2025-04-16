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
        Label title = new Label("🚢 Welcome to Battleship!");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: darkblue;");

        Button btnStart = new Button("🎮 Iniciar Juego");
        btnStart.setStyle("-fx-font-size: 14px;");
        btnStart.setOnAction(e -> {
            MenuScreen menuScreen = new MenuScreen();
            menuScreen.start(new Stage());
            primaryStage.close();
        });

        Button aboutButton = new Button("ℹ️ Acerca de");
        aboutButton.setOnAction(e -> showAbout());

        Label credit = new Label("🛠️ Desarrollado por Cristopher Ureña – UNA");
        credit.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        VBox layout = new VBox(20, title, btnStart, aboutButton, credit);
        layout.setStyle("-fx-padding: 25px; -fx-alignment: center; -fx-background-color: #f0f8ff;");

        Scene scene = new Scene(layout, 420, 300);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("🚀 Battleship - Menú Principal");
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
                "Apasionado por la filosofía, los libros con profundidad intelectual y el buen vino.\n\n" +
                "Juego: Batalla Naval\nDesarrollado con Java y JavaFX.\n\n" +
                "Gracias por probar esta experiencia."
        );
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
