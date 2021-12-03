package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import comm.CommUdp;

public class ControlleurBase implements ActionListener, ListSelectionListener, WindowListener, Observer {

	private enum EtatModif {
		TERMINE, EN_COURS
	}

	private EtatModif etatModif;
	private VueBase vue;
	private CommUdp commUDP;
	private String lastPseudo;

	public ControlleurBase(VueBase vue, int portClient, int portServer, int[] portsOther) throws IOException {
		this.vue = vue;
		this.commUDP = new CommUdp(portClient,portServer, portsOther);
		this.commUDP.setObserver(this);
		this.commUDP.sendMessageConnecte();
		this.commUDP.sendMessageInfoPseudo();
		this.etatModif = EtatModif.TERMINE;
	}

	//- LISTSELECTION LISTENER OPERATIONS -//
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			JList<String> list = vue.getActiveUsersList();
			System.out.println(list.getSelectedValue());
		}
	}

	
	//- ACTION LISTENER OPERATIONS -//
	@Override
	public void actionPerformed(ActionEvent e) {
		if ((JButton) e.getSource() == this.vue.getButtonModifierPseudo()) {
			JButton modifierPseudo = (JButton) e.getSource();

			if (this.etatModif == EtatModif.TERMINE) {
				this.lastPseudo = Utilisateur.getSelf().getPseudo();
				modifierPseudo.setText("OK");
				this.etatModif = EtatModif.EN_COURS;
			} else {

				if (!CommUdp.containsUserFromPseudo(this.vue.getDisplayedPseudo())) {

					Utilisateur.getSelf().setPseudo(this.vue.getDisplayedPseudo());

					try {
						this.commUDP.sendMessageInfoPseudo();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					this.vue.setDisplayedPseudo(this.lastPseudo);
				}

				modifierPseudo.setText("Modifier");
				this.etatModif = EtatModif.TERMINE;
			}

			this.vue.toggleEditPseudo();
		}
		
		if((JButton) e.getSource() == this.vue.getButtonDeconnexion() ) {
			try {
				this.commUDP.sendMessageDelete();
				this.commUDP.removeAll();
				VueBase.userList.removeAllElements();
				Utilisateur.getSelf().setPseudo("");
				
				this.vue.toggleEnableButtonConnexion();
				this.vue.toggleEnableButtonDeconnexion();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
		
		
		if((JButton) e.getSource() == this.vue.getButtonConnexion() ) {
			try {
				Utilisateur.getSelf().setPseudo(this.vue.getDisplayedPseudo());
				this.commUDP.sendMessageConnecte();
				this.commUDP.sendMessageInfoPseudo();
				
				this.vue.toggleEnableButtonConnexion();
				this.vue.toggleEnableButtonDeconnexion();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
	}
	

	//---------- WINDOW LISTENER OPERATIONS ----------//

	@Override
	public void windowClosing(WindowEvent e) {
		
		try {
			this.commUDP.sendMessageDelete();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	//---------- OBSERVER OPERATIONS ----------//
	@Override
	public void update(Object o, Object arg) {
		ArrayList<Utilisateur> userList = (ArrayList<Utilisateur>) arg;	
		ArrayList<String> listPseudo = new ArrayList<String>();
		vue.resetListUsers();
		System.out.println("Updated list :");
		for (Utilisateur user : userList) {
			System.out.println(user.getPseudo());
			listPseudo.add(user.getPseudo());
		}
		vue.addListUsers(listPseudo);
	}

}
