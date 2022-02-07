package main;
import java.io.Serializable;
import java.net.*;

public class Utilisateur implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String pseudo;
	private InetAddress ip;
	
	private static Utilisateur self;
	
	public Utilisateur(String id, String pseudo, InetAddress ip) throws UnknownHostException {
		this.id = id;
		this.pseudo = pseudo;
		this.ip = ip;
		/*System.out.println(InetAddress.getLocalHost())*/;
	}

	
	public String getId() {
		return id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public InetAddress getIp() {
		return ip;
	}
	
	public static void setSelf(String id, String pseudo,String host) throws UnknownHostException {
		if(Utilisateur.self == null) {
			Utilisateur.self = new Utilisateur(id, pseudo, InetAddress.getByName(host));
		}
	}
	
	public static Utilisateur getSelf() {
		return Utilisateur.self;
	}
}
