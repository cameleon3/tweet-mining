package fr.data.util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;


@SuppressWarnings("unused")
public class GetConnexion {

	public static Connection connexion = null;
public static Connection getconnexion(){
	
	/* Chargement du driver JDBC pour MySQL */
    try {
        
        Class.forName( "com.mysql.jdbc.Driver" );
      
    } catch ( ClassNotFoundException e ) {
      
    }

	String url = "jdbc:mysql://localhost:3306/data_mining";
	String utilisateur = "root";
	String motDePasse = "mohammad";

	try {
	    connexion = (Connection) DriverManager.getConnection( url, utilisateur, motDePasse );
	 //   System.out.println("connexion creer !!");
	    

		
	    
	} catch ( SQLException e ) {
	    /* G�rer les �ventuelles erreurs ici */
	}
	

	return connexion;
}

}
