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

    private static final int GRID_SIZE = 10; 
    private static final int CELL_SIZE = 30; 

    @Override
    public void start(Stage primaryStage) {
        // Título
        Label title = new Label("Place Your Ships");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        
        GridPane grid = new GridPane();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
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
            MainScreen mainScreen = new MainScreen();
            mainScreen.start(new Stage());
            primaryStage.close();
        });

        
        VBox layout = new VBox(20, title, grid, btnBack);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        
        Scene scene = new Scene(layout, 400, 450);
        primaryStage.setTitle("Ship Placement");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
