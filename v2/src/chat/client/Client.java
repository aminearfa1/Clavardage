package chat.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import chat.server.ChatServer;
import chat.server.StartServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Client extends Application {

	public static Label logs = new Label("[Chat Room Begins]");
	private TextField enterName = new TextField();
	public static TextField enterMessage = new TextField();
	private Scene scene1, scene2;
	private Button submitName = new Button("Join Chatroom");

	public static String name = "defualt name";

	@Override
	public void start(Stage primaryStage) throws Exception {

		// scene1: enter name first
		GridPane root1 = new GridPane();
		root1.setPrefSize(400, 200);
		root1.setPadding(new Insets(0, 20, 20, 20)); // add 10px padding
		root1.setVgap(15); // set vertical gap
		root1.setHgap(5); // set horizontal gap
		root1.setAlignment(Pos.CENTER);
		root1.add(new Label("Enter your name here: "), 0, 0);
		root1.add(enterName, 0, 1);
		root1.add(submitName, 1, 1);

		scene1 = new Scene(root1);
		primaryStage.setScene(scene1);
		primaryStage.setTitle("Chat Room");
		primaryStage.show();

		ConnectServer connectServer = new ConnectServer();

		// scene1 -- submitName Button
		submitName.setOnAction(e -> {
			// getText
			name = enterName.getText();
			Thread connectServerThread = new Thread(connectServer);
			connectServerThread.start();

			// Switch to scene2: after submitName button clicked
			ScrollPane layout = new ScrollPane();
			layout.setPrefSize(400, 600);
			layout.setContent(logs);

			BorderPane root2 = new BorderPane();
			root2.setPrefSize(350, 400);
			root2.setCenter(layout);
			root2.setBottom(enterMessage);

			scene2 = new Scene(root2);
			primaryStage.setScene(scene2);
		});

		// scene2 -- enterMessage
		// when user enters 'enter key', get the message and send it to Server.
		Client.enterMessage.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				DataOutputStream out = connectServer.getDataOuputStream();
				String msg = Client.enterMessage.getText();
				try {
					out.writeUTF(msg); //send msg to ChatServer through DataOutputStream
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				enterMessage.setText(""); //clear the TextField
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}

class ConnectServer implements Runnable {
	Socket socket = null; // To open communication
	DataInputStream in = null; // To read data from Server
	DataOutputStream out = null; // To send data to Server

	public DataOutputStream getDataOuputStream() {
		return out;
	}

	@Override
	public void run() {

		try {
			socket = new Socket("localhost", 8080);

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			out.writeUTF(Client.name); // send 'name' to Server
			System.out.println(Client.name + " : succesfully joined the chat room: ");

		} catch (IOException e) {
		}

		try {
			// This loop is to keep reading data from Server
			while (true) {
				String str = in.readUTF();
				Platform.runLater(() -> {
					Client.logs.setText(Client.logs.getText() + "\n" + str);
				});
			}
		} catch (IOException e) {
		}

	}
}