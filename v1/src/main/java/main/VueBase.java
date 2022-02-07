package main;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;


import javax.swing.*;


public class VueBase extends Vue {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JList<String> activeUsersList;
	private JTextField pseudoSelf;
	private JButton modifierPseudo;
	private JButton seConnecter;
	private JButton seDeconnecter;
	private ControlleurBase c;
	public static DefaultListModel<String> userList = new DefaultListModel<String>();
	
	
	public VueBase(String title, int port, int clientPort, int[] portsOther) throws IOException {
		super(title);
		
		JPanel main = new JPanel(new GridBagLayout());
		main.setBackground(Color.black);
		
		JPanel left = new JPanel(new BorderLayout());
		left.setBackground(Color.pink);
		left.setPreferredSize(new Dimension(150, 150));
		
		JPanel chat = new JPanel();
		chat.setBackground(Color.LIGHT_GRAY);
		chat.setPreferredSize(new Dimension(300, 500));
		
		JPanel bottom = new JPanel(new GridLayout(1, 2));
		bottom.setBackground(Color.black);
		bottom.setPreferredSize(new Dimension(300, 150));
		
		
		
		this.c = new ControlleurBase(this, port, clientPort, portsOther);
		
		//--------Panel haut pseudo--------//
		JPanel self = new JPanel(new FlowLayout());
		
		this.pseudoSelf = new JTextField(Utilisateur.getSelf().getPseudo());
		this.pseudoSelf.setPreferredSize(new Dimension(100, 20));
		this.pseudoSelf.setEditable(false);
		
		this.modifierPseudo = new JButton("Modifier");
		this.modifierPseudo.addActionListener(this.c);
		
		self.add(new JLabel("Moi : "));
		self.add(this.pseudoSelf);
		self.add(this.modifierPseudo);
		
		//--------Panel milieu liste utilisateurs--------//
		this.activeUsersList = new JList<String>(VueBase.userList);
		this.activeUsersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.activeUsersList.setLayoutOrientation(JList.VERTICAL);
		this.activeUsersList.addListSelectionListener(this.c);
		
		JScrollPane listScroller = new JScrollPane(this.activeUsersList);
		listScroller.setPreferredSize(new Dimension(50,50));
		listScroller.setAlignmentX(LEFT_ALIGNMENT);
		listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listScroller.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createTitledBorder("Utilisateurs Actifs"), 
	            BorderFactory.createEmptyBorder(5,2,2,2)));
		
		
		//--------Panel bas deconnexion--------//
		JPanel deconnexion = new JPanel(new GridLayout(1, 2));
		
		this.seConnecter = new JButton("Se Connecter");
		this.seConnecter.setEnabled(false);
		this.seConnecter.addActionListener(this.c);
		
		this.seDeconnecter = new JButton("Se Déconnecter");
		this.seDeconnecter.addActionListener(this.c);
		
		deconnexion.add(this.seConnecter);
		deconnexion.add(this.seDeconnecter);
		
		//--------Ajout à la vue--------//
		left.add(self, BorderLayout.PAGE_START);
		left.add(listScroller, BorderLayout.CENTER);
		left.add(deconnexion, BorderLayout.PAGE_END);
		
		
		
		GridBagConstraints gridBagConstraint = new GridBagConstraints();
		
		gridBagConstraint.fill = GridBagConstraints.BOTH;
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 0;
		gridBagConstraint.gridwidth = 1;
		gridBagConstraint.gridheight = 4;
		gridBagConstraint.weightx = 0.33;
		gridBagConstraint.weighty = 1;
		
		main.add(left,gridBagConstraint);
		
		gridBagConstraint.fill = GridBagConstraints.BOTH;
		gridBagConstraint.gridx = 1;
		gridBagConstraint.gridy = 0;
		gridBagConstraint.gridwidth = 2;
		gridBagConstraint.gridheight = 3;
		gridBagConstraint.weightx = 0.66;
		gridBagConstraint.weighty = 0.66;
		
		main.add(chat,gridBagConstraint);
		
		gridBagConstraint.fill = GridBagConstraints.BOTH;
		gridBagConstraint.gridx = 1;
		gridBagConstraint.gridy = 3;
		gridBagConstraint.gridwidth = 2;
		gridBagConstraint.gridheight = 1;
		gridBagConstraint.weightx = 0.66;
		gridBagConstraint.weighty = 0.33;
		
		
		main.add(bottom,gridBagConstraint);
		
		this.add(main);
		
		this.setSize(900,900);
		this.setVisible(true);
		
		this.addWindowListener(c);
	}
	
	public JList<String> getActiveUsersList(){
		return this.activeUsersList;
	}
	
	
	protected JButton getButtonModifierPseudo() {
		return this.modifierPseudo;
	}
	
	protected JButton getButtonDeconnexion() {
		return this.seDeconnecter;
	}
	
	protected JButton getButtonConnexion() {
		return this.seConnecter;
	}
	
	protected String getDisplayedPseudo() {
		return this.pseudoSelf.getText();
	}
	
	protected void setDisplayedPseudo(String pseudo) {
		this.pseudoSelf.setText(pseudo);
	}
	
	protected void toggleEditPseudo() {
		this.pseudoSelf.setEditable(!this.pseudoSelf.isEditable());
	}
	
	protected void toggleEnableButtonDeconnexion() {
		this.seDeconnecter.setEnabled(!this.seDeconnecter.isEnabled());
	}
	
	protected void toggleEnableButtonConnexion() {
		this.seConnecter.setEnabled(!this.seConnecter.isEnabled());
	}
	
	//Update de la liste des utilisateurs//
	protected void resetListUsers() {
		VueBase.userList.removeAllElements();
	}
	
	protected void addListUsers (ArrayList<String> listPseudo) {
		VueBase.userList.addAll(listPseudo);
	}
	
}
