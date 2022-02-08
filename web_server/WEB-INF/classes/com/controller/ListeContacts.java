package com.controller;

import java.net.InetAddress;
import java.util.ArrayList;

public class ListeContacts {
	private static Contact user;
	private ArrayList<Contact> listeContact;
	
	
	//la liste doit être un singleton
	
	private static final ListeContacts instance = new ListeContacts();
	
	public ListeContacts() {
		this.listeContact = new ArrayList<Contact>();
	}
	
	public static final ListeContacts getInstance() {
		return instance;
	}
	
	public boolean contactExist(Contact contact) {
		for(Contact c : listeContact) {
			if(c.getPseudo().equals(contact.getPseudo())){
				return true;
			}
		}
		return false;
	}
	
	public boolean idExist (int id) {
		for(Contact c : listeContact) {
			if(c.getId()==id){
				return true;
			}
		}
		return false;
	}
	
	public boolean pseudoExist(String pseudo) {
		for(Contact c : listeContact) {
			if(c.getPseudo().equals(pseudo)){
				return true;
			}
		}
		return false;
	}
	
	public void addContact(Contact contact) {
		if(!contactExist(contact)){
			System.out.println("Pseudo : "+contact.getPseudo()+" ajouté\n");
			listeContact.add(contact);
		}
	}
	
	public void deleteContact(Contact contact) {
		if(contactExist(contact)){
			System.out.println("Pseudo : "+contact.getPseudo()+"supprimé\n");
			listeContact.remove(contact);
		}
	}
	
	public int length() {
		int n = 0;
		while(!listeContact.isEmpty()) {
			n++;
		}
		return n;
	}
	
	public Contact findContact(String pseudo) {
		Contact contact = null;
		for(Contact c : listeContact) {
			if(c.getPseudo().equals(pseudo)){
				contact = c;
				break;
			}
		}
		return contact;
	}
	
	
	public String actifUsers() {
		String users = "";
		for(Contact c : this.listeContact) {
			users += (c.getPseudo()+"\n");
		}
		return users;
	}

	public ArrayList<Contact> getListe() {
		return listeContact;
	}

	public void setListeContact(ArrayList<Contact> listeContact) {
		this.listeContact = listeContact;
	}

}
