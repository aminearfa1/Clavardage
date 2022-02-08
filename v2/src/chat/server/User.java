package chat.server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import javafx.application.Platform;

//this class includes some helper methods to manage users and
//	sendMsg method which sends messages toward DataOutputStream to Clients
//
public class User {
	//String : User name, DataOutputStream <-- socket.getOutputStream()
	//	When addCLient() is called, 
	//	a user will be put into this HashMap with its own DataOutputStream
	HashMap<String, DataOutputStream> clientmap = new HashMap<String, DataOutputStream>();

	String str; //this is to update ChatServer.logs
	
	public synchronized void AddClient(String name, Socket socket) {
		try {
			clientmap.put(name, new DataOutputStream(socket.getOutputStream()));
			sendMsg(name + " joined.", "Server");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public synchronized void RemoveClient(String name) {
		try {
			clientmap.remove(name);
			sendMsg(name + " exit.", "Server");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public synchronized void sendMsg(String msg, String name) throws Exception {
		// server-side: update log
		str = name + " : " + msg;
		Platform.runLater(() -> { //runLater ---> to update a node after it is loaded.
			ChatServer.logs.setText(ChatServer.logs.getText() + "\n" + str);
		});
		
		// client-side: send stream to clients
		Iterator iterator = clientmap.keySet().iterator();
		while (iterator.hasNext()) {
			String clientname = (String) iterator.next();
			clientmap.get(clientname).writeUTF(name + " : " + msg); // client-side
		}
	}
}

