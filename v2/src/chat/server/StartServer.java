package chat.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;

// This is to start a server up to 10 threads.
//
public class StartServer implements Runnable {
	Socket socket = null; // To communicate with the client who has accessed Server
	User user = new User(); // To manage users who is in the chat room
	ServerSocket server_socket = null; // To accept a client

	int count = 0;
	Thread thread[] = new Thread[10];
	// this server is designed to accept up to 10 connections from the clients.

	@Override
	public void run() {
		try {
			server_socket = new ServerSocket(8080);
			while (true) {
				socket = server_socket.accept(); // wait for the request from clients
				Date d1 = new Date();
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
				String formattedDate = df.format(d1);
				
				Platform.runLater(() -> {
					ChatServer.logs.setText(ChatServer.logs.getText() + "\nNew client connected. " 
							+ formattedDate);
				});

				thread[count] = new Thread(new ChatService(user, socket));
				thread[count].start();
				count++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
