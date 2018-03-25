package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class FXMain extends Application {
	
	static String ip = "localhost";
	static int port = 1099;
	static final String NAME = "Game";
	
	static void tryReadArgs(String args[]){
		if(args.length > 0) {
			ip = args[0];
		}
		if(args.length > 1) {
			port = Integer.parseInt(args[1]);
		}
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ultimate_board.fxml"));
        primaryStage.setTitle("RMI Ultimate Tic Tac Toe");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    public static void main(String[] args) {
    	tryReadArgs(args);
        launch(args);
    }
}
