package connexion_interface;

import java.awt.*;
import javax.swing.*;

import main.Vue;

public class VueConnect extends Vue {
	
	private static final long serialVersionUID = 1L;
	
	//Graphical elements
	private JButton boutonValider;
	private JTextField inputUsername;
	private JPasswordField inputPassword;
	private JLabel labelUsername;
	private JLabel labelPassword;
	private JLabel connexionInfo;
	private JPanel main;
	private JPanel panelPassword;

	//Controller
	private Controlleur controle;
	
	/**
	 * Create and initialize the view SWING window that will be used during the connection phase.
	 * Doing so, it also creates the controller that will monitor all changes during the connection phase.
	 * 
	 * @param numtest : to be passed down to the controller
	 * 
	 */
	public VueConnect(int numtest) {
		super("Connexion");
		controle = new Controlleur(this, numtest);
		
		//Window creation
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		
	
		
		//Display window
		this.setVisible(true);
	}
	
}
	