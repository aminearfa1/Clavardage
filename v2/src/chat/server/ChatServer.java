package chat.server;

import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChatServer extends Application {

	public static Label logs = new Label("[Chat Server Logs]");
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//create new thread to start Server 
		Thread startServer = new Thread(new StartServer());
		startServer.start();
		
		ScrollPane layout = new ScrollPane();
		layout.setPrefSize(400, 600);
		layout.setContent(logs);
		
		primaryStage.setTitle("Chat Server");
		primaryStage.setScene(new Scene(layout));
		primaryStage.show();
	}

}