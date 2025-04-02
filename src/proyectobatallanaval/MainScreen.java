package proyectobatallanaval; 

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainScreen extends Application {

    @Override
public void start(Stage primaryStage) {
    System.out.println("JavaFX está iniciando...");

   
    Label title = new Label("Welcome to Battleship!");
    title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    
    Button btnStart = new Button("Start Game");
    btnStart.setOnAction(e -> {
    MenuScreen menuScreen = new MenuScreen();
    menuScreen.start(new Stage());
    primaryStage.close();
     });


    
    VBox layout = new VBox(20, title, btnStart);
    layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

    Scene scene = new Scene(layout, 400, 300);
    primaryStage.setTitle("Battleship");
    primaryStage.setScene(scene);
    primaryStage.show();
}

    


    public static void main(String[] args) {
        launch(args);
    }
}