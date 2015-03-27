package fr.data.preprocess;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mysql.jdbc.PreparedStatement;

import fr.data.util.DataManager;
import fr.data.util.GetConnexion;

public class Statistiques {

	public static void main(String[] args) {
		 PreparedStatement statmentListe=null;
			ResultSet resultat=null;
			

			try {
				statmentListe =(PreparedStatement) GetConnexion.getconnexion().prepareStatement("select created_at,tweet_id from tweets");
				
				resultat =  statmentListe.executeQuery();
				Tweet tweet=new Tweet();
				
				
				while(resultat.next()){
					tweet.setId(resultat.getString("tweet_id"));
					//tweet.setDate(resultat.getString("created_at"));
					Date date=resultat.getDate("created_at");
					DateTime dt=new DateTime(date);
					
					
					DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMM");
		
					tweet.setDate(dt.toString( formatter ));
					
				
					//System.out.println(" date1 : "+tweet.getDate());
					     
					    
					    tweet.setId(resultat.getString("tweet_id"));
					    
					    DataManager.updateTweetDate(tweet);
					    
					  
					
				}
			
				
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}

}
