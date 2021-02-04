/*Author: Andries Joubert
        Threaded Project Term 3
        Project Workshop 6(Java)
        Group: Group 3 */

package TravelDetailsDisplay;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TravelDetailsDisplay.fxml"));
        primaryStage.setTitle("Travel Experts Administration Form");
        primaryStage.setScene(new Scene(root, 700, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
