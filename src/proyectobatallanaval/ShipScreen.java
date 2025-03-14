package proyectobatallanaval;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShipScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
       
        Label title = new Label("Place Your Ships");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        
        Label info = new Label("Here you will place your ships (coming soon).");

       
        Button btnBack = new Button("Back to Main Menu");
        btnBack.setOnAction(e -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.start(new Stage());
            primaryStage.close(); 
        });

        
        VBox layout = new VBox(20, title, info, btnBack);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Ship Placement");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

