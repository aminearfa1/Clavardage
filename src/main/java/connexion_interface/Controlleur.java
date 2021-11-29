package connexion_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;


import main.Utilisateur;


public class Controlleur{

	//Controller state : either DEBUT at initialization or ID_OK if the user has signed in
	private enum Etat {DEBUT, ID_OK};
	private VueConnect vue;
	private Etat etat;
	private int portTCP;
	private String username;

	
}
