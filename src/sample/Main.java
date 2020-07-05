package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/sample.fxml"));
        primaryStage.setTitle("Weather");
        primaryStage.setScene(new Scene(root, 773, 447));
        primaryStage.setMaxHeight(447);
        primaryStage.setMaxWidth(773);
        primaryStage.show();
        System.out.println("Test");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
