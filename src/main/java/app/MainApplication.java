package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/view/Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 800);
        scene.getStylesheets().add(MainApplication.class.getResource("/css/Style.css").toExternalForm());
        stage.getIcons().add(new Image(MainApplication.class.getResource("/images/icon.png").openStream()));
        stage.setTitle("Tic-Tac-Toe");
        stage.setScene(scene);
        stage.show();
        
    }


    public static void main(String[] args) {
        launch();
    }
}