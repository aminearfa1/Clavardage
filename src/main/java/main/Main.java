package main;

import java.io.IOException;



public class Main {

	private static int portServers[] = {1526,1501,1551,1561};
	private static String ids[] = {"0", "1"};
	private static String pseudo[] = {"sana", "amine"};
	public static void main(String[] args) {
		
		Main.createApp(0);
		Main.createApp(1);

	}
	
	private static void createApp(int i) {
		try {
			Utilisateur.setSelf(Main.ids[i], Main.pseudo[i], "localhost");
			new VueBase("Application", Main.portServers[i]-1, Main.portServers[i], Main.portServers);	
		} catch (IOException e) {
			System.out.println(e.toString());	
		}
	}

}
