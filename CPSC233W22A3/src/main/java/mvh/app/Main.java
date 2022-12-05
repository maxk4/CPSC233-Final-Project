// Maximilian Kaczmarek - UCID 30151219
// Cpsc 231 Tutorial 1 
// April 1 2022

package mvh.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

//import application.SetupController;

public class Main extends Application {

    public static final String version = "1.0";

    /**
     * A program-wide random number generator
     */
    public static Random random = new Random(12345);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        
        MainController mainController = fxmlLoader.getController();
        mainController.linkWithApplication(this);
        stage.setTitle("Monsters versus Heroes World Editor");
        stage.setScene(scene);
        stage.show();
    }

    public void aboutView() {
		FXMLLoader loader = new FXMLLoader();
		Stage about = new Stage();
		VBox comp = new VBox();
		Label aboutHeading = new Label("ABOUT");
		Label name = new Label("Author: Maximilian Kaczmarek");
		Label email = new Label("Email: maximilian.kaczmarek@ucalgary.ca");
		Label version = new Label("Version: v1.0");
		Label description = new Label("This is a graphical editor for the MvH game");
		comp.getChildren().add(aboutHeading);
		comp.getChildren().add(name);
		comp.getChildren().add(email);
		comp.getChildren().add(version);
		comp.getChildren().add(description);
		Scene stageScene = new Scene(comp, 300, 300);
		about.setTitle("About Info");
		about.setScene(stageScene);
		about.show();
	}
}
