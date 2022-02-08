package com.controller;

import java.io.*;
import java.net.InetAddress;

public class Contact {
	
	private String pseudo;
	private String status;
	private int port;
	private int id;
	
	private InetAddress address;
	
	
	public Contact(String pseudo, InetAddress address, int port) {
		this.pseudo = pseudo;
		this.address = address;
		this.status = "";
		this.port = port;
	}
	
	public String getPseudo() {
		return this.pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public InetAddress getAddress() {
		return this.address;
	}
	
	public void setAddress(InetAddress address) {
		this.address = address;
	}
	
	public String getStatut() {
		return this.status;
	}
	
	public void setStatut(String status) {
		this.status = status;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String toString() {
		return "Pseudo : " + pseudo + " status : " + status + " \n";
	}
}
