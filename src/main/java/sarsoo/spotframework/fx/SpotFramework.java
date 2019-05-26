package sarsoo.spotframework.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpotFramework extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/RootPane.fxml"));
		
		Parent root = (Parent) loader.load();
		
		Scene scene = new Scene(root, 1000, 800);

		stage.setMinHeight(800);
		stage.setMinWidth(960);
		stage.setTitle("spotframework");
		stage.setScene(scene);
		stage.show();
	}

}
