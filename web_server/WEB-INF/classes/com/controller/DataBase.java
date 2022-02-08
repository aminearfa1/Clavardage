package db;

import java.sql.*;
import java.util.ArrayList;


public class DataBase {
	
	
	private static DataBase instance = null;
	
	private static Connection connexion;
	
	private DataBase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		try {
			connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?characterEncoding=latin1", "root", "root");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DataBase getInstance() {
		
			DataBase db = instance;
			if(db == null) {
				db = new DataBase();
			}
			return db;
		
	}
	
	//Ajout d'un user 
	//retourne l'id associer au user ajouter
	public String addPseudo(String pseudo)  {
		String query = "INSERT INTO users (pseudo) VALUES (?)";
		PreparedStatement pStat = null;
		try {
			getInstance();
			pStat = connexion.prepareStatement(query);
			pStat.setString(1, pseudo);
			pStat.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		/*
		query = "SELECT * FROM users WHERE pseudo=?";
		int id =0;
		try {
			pStat = connexion.prepareStatement(query);
			pStat.setString(1, pseudo);
			ResultSet resId = pStat.executeQuery();
			if(resId.next()) {
				id = resId.getInt("id");
			}
			connexion.close();

		}catch(SQLException e) {
			e.printStackTrace();
		}
		*/
		}
		return pseudo;
	}
	
	
	
	
	public boolean checkPseudoIfExists(String pseudo)  {
		boolean exists=false;
		String query = "SELECT pseudo FROM users WHERE pseudo=?";
		PreparedStatement pStat = null;
		try {
			getInstance();
			pStat = connexion.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
			
			pStat.setString(1, pseudo);
			ResultSet resPseudo = pStat.executeQuery();
			if(resPseudo.first()) {
				pseudo = resPseudo.getString("pseudo");
				exists = true;
			}
			connexion.close();

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}
	
	public int getIdFromPseudo(String pseudo)  {
		int id = -1;
		String query = "SELECT * FROM users WHERE pseudo='" + pseudo + "' AND statut = 'En ligne'";
		PreparedStatement pStat = null;
		try {
			getInstance();
			pStat = connexion.prepareStatement(query);
			//pStat.setString(1, pseudo);
			ResultSet resId = pStat.executeQuery();
			if(resId.next()) {
				id = resId.getInt("id");
			}
			connexion.close();

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public boolean loginExiste(String login)  {
        String loginAux = null;
        String query = "SELECT * FROM users WHERE login =?";
        PreparedStatement pStat = null;
        try {
        	getInstance();
			pStat = connexion.prepareStatement(query);
		    pStat.setString(1, login);
	        ResultSet rs = pStat.executeQuery();
	        if (rs.next()) {
	            loginAux = rs.getString("login");
	        }
	    	connexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
        return !(loginAux==null);
	}
	
	public String getPseudoFromLogin(String login)  {
		String pseudo = null;
		String query = "SELECT * FROM users WHERE login=?";
		PreparedStatement pStat = null;
		try {
			getInstance();
			pStat = connexion.prepareStatement(query);
			pStat.setString(1, login);
			ResultSet resPseudo = pStat.executeQuery();
			if(resPseudo.next()) {
				pseudo = resPseudo.getString("pseudo");
			}
			connexion.close();

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return pseudo;
	}
	
	public int getIdFromLogin(String login)  {
		int id = -1;
		String query = "SELECT * FROM users WHERE login=?";
		PreparedStatement pStat = null;
		try {
			getInstance();
			pStat = connexion.prepareStatement(query);
			pStat.setString(1, login);
			ResultSet resPseudo = pStat.executeQuery();
			if(resPseudo.first()) {
				id = resPseudo.getInt("id");
			}
			connexion.close();

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public void deleteFromId(int id)  {
		String query = "DELETE FROM `users` WHERE `id` = ?";
		PreparedStatement pStat = null;
		try {
			getInstance();
			pStat = connexion.prepareStatement(query);
			pStat.setInt(1, id);
			pStat.executeUpdate();
			connexion.close();

		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updatePseudo(int id, String pseudo)  {
		String query = "UPDATE `users` SET `pseudo`=? WHERE id=?";
		PreparedStatement pStat = null;
		try {
			getInstance();
			pStat = connexion.prepareStatement(query);
			pStat.setString(1, pseudo);
			pStat.setInt(2, id);
			pStat.executeUpdate();
			connexion.close();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//chat table : 
	//nom : idSrcidDest
    //colonnes :
    //id (l'id unique du message)
    //idSrc et idDest : les id des deux users qui se parlent
    //message : le message envoy� 
    //date : la date d'envoi du msg
    public void newChatTable (int id1, int id2)  {
        int idSrc = id1;
        int idDest = id2;
        if(id1>id2) {
            idSrc = id2;
            idDest = id1;
        }
        String nameTable = idSrc+"_"+idDest;
        String query = "CREATE TABLE " +nameTable+" (\n" +
        "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, \n" +
        "idSrc INT NOT NULL,\n" + 
        "idDest INT NOT NULL,\n" +
        "message MEDIUMTEXT NOT NULL,\n" +
        "date_heure TIMESTAMP NOT NULL\n" +
        ");";
        PreparedStatement pStat = null;
        try {
        	getInstance();
            pStat = connexion.prepareStatement(query);
            pStat.executeUpdate();
        	connexion.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }
    
    public boolean tableChatExists(int id1, int id2)  {
        int idSrc = id1;
        int idDest = id2;
        boolean res = true;
        if(id1>id2) {
            idSrc = id2;
            idDest = id1;
        }
        String nameTable = idSrc+"_"+idDest;
        String query = "SELECT * FROM " + nameTable;
        PreparedStatement pStat = null;
        try {
        	getInstance();
            pStat = connexion.prepareStatement(query);
            pStat.executeQuery();
        	connexion.close();

        }catch(SQLException e) {
            res = false;
            //e.printStackTrace();
        }
        return res;
    }
    
    //il faut mettre dans l'ordre idSrc pour id1 et id Dest pour id2 dans les params
    public void addMessage(int id1, int id2, String message)  {
        int idSrc = id1;
        int idDest = id2;
        if(id1>id2) {
            idSrc = id2;
            idDest = id1;
        }
        String nameTable = idSrc+"_"+idDest;
        String query = "INSERT INTO " + nameTable + "(idSrc, idDest, message, date_heure) VALUES (?, ?, ?, CURRENT_TIME)";
        PreparedStatement pStat = null;
        try {
        	getInstance();
            pStat = connexion.prepareStatement(query);
            pStat.setInt(1, id1);
            pStat.setInt(2, id2);
            pStat.setString(3, message);
            pStat.executeUpdate();
        	connexion.close();

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
  
   public Timestamp getDateFromMessage(int id1, int id2, String message) {
       Timestamp date = null;
       int idSrc = id1;
    int idDest = id2;
    if(id1>id2) {
        idSrc = id2;
        idDest = id1;
    }
    String nameTable = idSrc+"_"+idDest;
    String query = "SELECT * FROM "+nameTable+" WHERE message=?";
    PreparedStatement pStat = null;
    try {
    	getInstance();
        pStat = connexion.prepareStatement(query);
        pStat.setString(1, message);
        ResultSet resPseudo = pStat.executeQuery();
        if(resPseudo.first()) {
            date = resPseudo.getTimestamp("date_heure");
        }
    	connexion.close();
    }catch(SQLException e) {
        e.printStackTrace();
    }
    return date;
}
   
   public void updateStatus(int id, String status) {
		String query = "UPDATE `users` SET `statut`=? WHERE id=?";
		PreparedStatement pStat = null;
		try {
			getInstance();
			pStat = connexion.prepareStatement(query);
			pStat.setString(1, status);
			pStat.setInt(2, id);
			pStat.executeUpdate();
			connexion.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}