# Multi-threaded Socket Programming - Chat Application with Java

- key packages: java.net.\* package and JavaFX
- Multi-threaded socket programming: it accepts up to 10 requests from clients.

### Server-side

- ChatServer: This is to build an application using JavaFX
- ChatService: This is to run a thread which reads stream from client
- StartServer: This is to start a server up to 10 threads.
- User: this class includes some helper methods to manage users and sendMsg method which sends messages toward DataOutputStream to Clients

### Client-side

- Client.java: it has ConnectServer class to open communication. ConnectServer implements Runnable as the thread has to be running to keep reading data from DataInputStream

```Java
@Override
public void run() {
    try {
        socket = new Socket("localhost", 8080);

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        out.writeUTF(Client.name); // send 'name' to Server
        System.out.println(Client.name + " : succesfully joined the chat room: ");

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
```

### Demo

##### 1. Run Server and two clients

Server-side Log

![Screen Shot 2019-05-02 at 7.47.19 PM.png](https://i.loli.net/2019/05/03/5ccb8596226b7.png)

 Clients Reqquest: When users enter their name, they request a connection to Server

![Screen Shot 2019-05-02 at 7.47.30 PM.png](https://i.loli.net/2019/05/03/5ccb85dca5c47.png)

![Screen Shot 2019-05-02 at 7.47.45 PM.png](https://i.loli.net/2019/05/03/5ccb8615d7c5b.png)

##### 2. Accept the connections and establish the chat room for clients.

Server-side Log 

![Screen Shot 2019-05-02 at 7.47.53 PM.png](https://i.loli.net/2019/05/03/5ccb86203ac56.png)

Server now sends data to clients throw data stream. If John says 'hi', server send this to each clients.

Example: Client 'Jane' Window 

![Screen Shot 2019-05-02 at 7.48.17 PM.png](https://i.loli.net/2019/05/03/5ccb86df0637a.png)

##### Server keeps every log and displays it

![Screen Shot 2019-05-02 at 7.48.20 PM.png](https://i.loli.net/2019/05/03/5ccb870374d5c.png)
