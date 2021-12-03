package comm;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import message.*;

public class UdpClient {

	private DatagramSocket sockUDP;
	private InetAddress broadcast;
	
	public UdpClient(int port) throws SocketException, UnknownHostException {
		this.sockUDP = new DatagramSocket(port);
		InetAddress localHost = InetAddress.getLocalHost();
		NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
		this.broadcast = networkInterface.getInterfaceAddresses().get(0).getBroadcast();
	}
	
	
	//Send a message casted as string to the specified port on localhost
	protected void sendMessageUDP_local(Message message, int port, InetAddress clientAddress) throws IOException {
		String messageString=message.toString();
		DatagramPacket outpacket = new DatagramPacket(messageString.getBytes(), messageString.length(), clientAddress, port);
		this.sockUDP.send(outpacket);
		
	}
	
//	protected void sendMessageUDP_broadcast(String message, int port) throws IOException{
//		String messageString=message.toString();
//		DatagramPacket outpacket = new DatagramPacket(messageString.getBytes(), messageString.length(), this.broadcast, port);
//		this.sockUDP.send(outpacket);
//	}
	
}
