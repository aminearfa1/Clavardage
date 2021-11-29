package main;

import javax.swing.JFrame;


// General class from which all VueX class derivate

public class Vue extends JFrame{

	private static final long serialVersionUID = 1L;

	public Vue(String title) {
		super(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void reduireAgent() {}
	
	public void close() {
		this.dispose();
	}
}
