package chat.server;

import java.io.DataInputStream;
import java.net.Socket;

// Class ChatService is to run a thread which reads stream from client
//
public class ChatService implements Runnable {

	Socket socket;
	DataInputStream in;
	String name;
	User user = new User();

	public ChatService(User user, Socket socket) throws Exception {
		this.user = user;
		this.socket = socket;

		in = new DataInputStream(socket.getInputStream());
		// getInputStream from the socket to read data from the client who has been connected

		this.name = in.readUTF(); // To read a name
		user.AddClient(name, socket); // To add a new user (Management of users in the chat room)
	}

	public void run() {
		try {
			while (true) {
				String msg = in.readUTF(); // read stream from client
				user.sendMsg(msg, name);
			}
		} catch (Exception e) {
			user.RemoveClient(this.name);
		}
	}
}
