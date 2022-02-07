package comm;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import main.Observer;
import main.Utilisateur;
import main.VueBase;
import message.*;


public class CommUdp extends Thread {


	private UdpClient client;
	private int portServer;
	private ArrayList<Integer> portOthers;
	private static ArrayList<Utilisateur> users = new ArrayList<Utilisateur>();
	private Observer observer;

	public CommUdp(int portClient, int portServer, int[] portsOther) throws IOException {
		this.portServer = portServer;
		this.portOthers = this.getArrayListFromArray(portsOther);
		new UdpServer(portServer, this).start();
		this.client = new UdpClient(portClient);
	}
	
	public void setObserver (Observer obs) {
		this.observer=obs;
	}
	
	protected static boolean containsUserFromID(String id) {
		for(Utilisateur u : users) {
			if(u.getId().equals(id) ) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsUserFromPseudo(String pseudo) {
		for(Utilisateur u : users) {
			if(u.getPseudo().equals(pseudo) ) {
				return true;
			}
		}
		
		return false;
	}
	
	private static int getIndexFromID(String id) {
		for(int i=0; i < users.size() ; i++) {
			if(users.get(i).getId().equals(id) ) {
				return i;
			}
		}
		return -1;
	}
	
	private static int getIndexFromIP(InetAddress ip) {
		for(int i=0; i < users.size() ; i++) {
			if(users.get(i).getIp().equals(ip)) {
				return i;
			}
		}
		return -1;
	}
	
	
	protected synchronized void addUser(String idClient, String pseudoClient, InetAddress ipClient) throws IOException {
		users.add(new Utilisateur(idClient, pseudoClient, ipClient));
		observer.update(this, users);
		
	}
	
	protected synchronized void changePseudoUser(String idClient, String pseudoClient, InetAddress ipClient) {
		int index = getIndexFromID(idClient);
		users.get(index).setPseudo(pseudoClient);
		observer.update(this, users);
	}

	
	protected synchronized void removeUser(String idClient, String pseudoClient,InetAddress ipClient) {
		int index = getIndexFromIP(ipClient);
		if( index != -1) {
			users.remove(index);
		}
		observer.update(this, users);
	}
	
	public void removeAll(){
		int oSize = users.size();
		for(int i=0; i<oSize;i++) {
			users.remove(0);
		}
	}

	private ArrayList<Integer> getArrayListFromArray(int ports[]) {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		for (int port : ports) {
			tmp.add(port);
		}
		tmp.remove(Integer.valueOf(portServer));

		return tmp;
	}

	
	public void sendMessageConnecte() throws UnknownHostException, IOException {
		for(int port : this.portOthers) {
			try {
				this.client.sendMessageUDP_local(new MessageSysteme(Message.TypeMessage.JE_SUIS_CONNECTE), port, InetAddress.getLocalHost());
			} catch (MauvaisTypeMessageException e) {}
		}
	}
	
	

	//message de prévention de changement de pseudo
	public void sendMessageInfoPseudo() throws UnknownHostException, IOException {

		Utilisateur self = Utilisateur.getSelf();
		
		String pseudoSelf =self.getPseudo();
		String idSelf = self.getId();
		
		Message msout = null;
		try {
			msout = new MessageSysteme(Message.TypeMessage.INFO_PSEUDO, pseudoSelf, idSelf);
			for(int port : this.portOthers) {
				this.client.sendMessageUDP_local(msout, port, InetAddress.getLocalHost());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	

	public void sendMessageInfoPseudo(int portOther) throws UnknownHostException, IOException {
	
		Utilisateur self = Utilisateur.getSelf();
		try {
			Message msout = new MessageSysteme(Message.TypeMessage.INFO_PSEUDO, self.getPseudo(), self.getId());
			this.client.sendMessageUDP_local(msout, portOther, InetAddress.getLocalHost());
		} catch (MauvaisTypeMessageException e) {e.printStackTrace();}
	}


	public void sendMessageDelete() throws UnknownHostException, IOException {
		for(int port : this.portOthers) {
			try {
				this.client.sendMessageUDP_local(new MessageSysteme(Message.TypeMessage.JE_SUIS_DECONNECTE), port, InetAddress.getLocalHost());
			} catch (MauvaisTypeMessageException e) {}
		}
	}



}
