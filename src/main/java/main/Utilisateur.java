package main;
import java.net.*;

public class Utilisateur{


	private static final long serialVersionUID = 1L;
	
	private String id;
	private String pseudo;
	private InetAddress ip;
	private int port;
	
	//Represents the user that is currently using the application
	private static Utilisateur uid;
	
	
	/* création d'un objet représentant un utilisateur*/
	
	public Utilisateur(String id, String pseudo, InetAddress ip, int port) throws UnknownHostException {
		this.id = id;
		this.pseudo = pseudo;
		this.ip = ip;
		this.port = port;

	}
	
	// GETTERS POUR RECUPERER LES INFORMATIONS DES UTILISATEURS//
	
	
	public String getId() {
		return id;
	}
	
	
	public String getPseudo() {
		return pseudo;
	}
	
	
	
	public InetAddress getIp() {
		return ip;
	}
	
	
	public int getPort() {
		return port;
	}
	
	
	public static Utilisateur getUid() {
		return Utilisateur.uid;
	}
	
	
	// SETTERS POUR CHANGER LES INFORMATIONS DES UTILISATEURS//

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	
	
	
	
	
}
