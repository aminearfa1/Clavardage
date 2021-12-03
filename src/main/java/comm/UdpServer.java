package comm;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

import message.*;



public class UdpServer extends Thread {

	private DatagramSocket sockUDP;
	private CommUdp commUDP;
	private byte[] buffer;

	public UdpServer(int port, CommUdp commUDP) throws SocketException {
		this.commUDP = commUDP;
		this.sockUDP = new DatagramSocket(port);
		this.buffer = new byte[256];
	}

	@Override
	public void run() {
		while (true) {

			try {
				
				DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
				this.sockUDP.receive(inPacket);
				String msgString = new String(inPacket.getData(), 0, inPacket.getLength());
				Message msg = Message.stringToMessage(msgString);
				
				switch(msg.getTypeMessage()) {
				case JE_SUIS_CONNECTE :	
					int portClient = inPacket.getPort();
					int portServer = portClient+1;
					
					this.commUDP.sendMessageInfoPseudo(portServer);
					break;
					
				case INFO_PSEUDO :
					
					if (CommUdp.containsUserFromID(((MessageSysteme) msg).getId())) {
						commUDP.changePseudoUser(((MessageSysteme) msg).getId(), ((MessageSysteme) msg).getPseudo(), inPacket.getAddress()); 
					}
					else {
						
						commUDP.addUser(((MessageSysteme) msg).getId(), ((MessageSysteme) msg).getPseudo(), inPacket.getAddress());
					}
					break;
					
				case JE_SUIS_DECONNECTE :
					commUDP.removeUser( ((MessageSysteme) msg).getId() , ((MessageSysteme) msg).getPseudo(), inPacket.getAddress() );
					break;
					
				default :
				}

			} catch (IOException e) {
				System.out.println("receive exception");
			}

		}
	}
}
