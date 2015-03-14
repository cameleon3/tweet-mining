package fr.data.preprocess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mysql.jdbc.PreparedStatement;

import fr.data.util.GetConnexion;

public class Main {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		
		 PreparedStatement statmentListe=null;
			ResultSet resultat=null;
			Date date=null;
			
		
        try {
			statmentListe =(PreparedStatement) GetConnexion.getconnexion().prepareStatement("select created_at from tweets limit 1");
			resultat =  statmentListe.executeQuery();
			System.out.println("toto");
			
			while(resultat.next()){
				date=resultat.getDate("created_at");
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(" la date est : "+getDate(date));
		
	}
	
	  public static String getDate(Date date){
		  
			
			return  new DateTime(date).toString( DateTimeFormat.forPattern("yyyy-MM-dd" ) );
		  
	  }
	  

}
