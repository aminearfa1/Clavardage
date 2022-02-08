package com.sdzee.servlets;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.controller.*;

public class Test extends HttpServlet {

	private ListeContacts listActifs;
	public void init() {
		listActifs = ListeContacts.getInstance();
	}
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
			System.out.println("j'ai reçu la requete");
			String login;
			String pseudo;
			String res;
			String adr;
			String port;
			String type = request.getParameter("type");
			
			switch (type) {
			case "loginExiste":
				login = request.getParameter("login");
				boolean loginOK = DataBase.getInstance().loginExiste(login);
				res = Boolean.toString(loginOK);
				response.setHeader("result", res);
				break;
				
			case "estActif":
				login = request.getParameter("login");
				boolean existe = DataBase.getInstance().loginExiste(login);
				boolean estActif = false;
				if(existe) {
					String status = DataBase.getInstance().getStatus(login);
					
					if(status.equals("En ligne")) {
						estActif = true;
					}
				}
				
				res = Boolean.toString(estActif);
				response.setHeader("result", res);
				break;
				
			case "pseudoOK":
				boolean pseudoOK = true;
				pseudo = request.getParameter("pseudo");
				for(Contact c : listActifs.getListe()) {
					if(c.getPseudo().equals(pseudo)){
						pseudoOK = false;
					}
				}
				res = Boolean.toString(pseudoOK);
				response.setHeader("result", res);
				break;
			case "addUser":
				pseudo = request.getParameter("pseudo");
				adr = request.getParameter("adr");
				String adr2= adr.substring(1);
				System.out.println(adr2);
				port = request.getParameter("port");
				InetAddress address = InetAddress.getByName(adr2);
				int portInt = Integer.parseInt(port);
				Contact user = new Contact(pseudo, address, portInt);
				listActifs.addContact(user);
				break;
				
			case "pseudoChanged":
				pseudo = request.getParameter("pseudo");
				String ancienPseudo = request.getParameter("pseudoVieux");
				System.out.println("ancien pseudo :"+ancienPseudo+"  et nouveau :"+pseudo);
				for(Contact c : listActifs.getListe()) {
					if(c.getPseudo().equals(ancienPseudo)){
						c.setPseudo(pseudo);
				}
				}
				System.out.println("actifs :"+listActifs.actifUsers());
				break;
			
			case "deconnexion":
				pseudo = request.getParameter("pseudo");
				String idRequest = request.getParameter("id");
				System.out.println("actifs :"+listActifs.actifUsers());
				int id = Integer.parseInt(idRequest);
				Contact c = listActifs.findContact(pseudo);
				listActifs.deleteContact(c);
				DataBase.getInstance().updateStatus(id, "Hors Ligne");
				break;
			
			
			
			
			case "getUser":
				pseudo = request.getParameter("pseudo");
				Contact dest = listActifs.findContact(pseudo);
				response.setHeader("address", dest.getAddress().toString());
				response.setHeader("port", Integer.toString(dest.getPort()));
				
				break;
			
			case "actifs":
				String actifs = listActifs.actifUsers();
				//String actifs = "ceci sont les actifs";
				response.setHeader("actifs", actifs);				
				break;
			
			}
			
			
	}
}
