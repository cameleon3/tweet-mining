package fr.data.util;

import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import fr.data.preprocess.Tweet;

public class DataManager {
	

	
	public static boolean insertCleanTweet( Tweet tw){
		
		PreparedStatement prepareInsertCreate=null;

		try {

			prepareInsertCreate =(PreparedStatement) GetConnexion.getconnexion().prepareStatement("INSERT INTO tweets_clean (tweet_id, tweet_text_clean) VALUES(?,?);");
			
			
			prepareInsertCreate.setString(1, tw.getId());
			prepareInsertCreate.setString(2, tw.getText());
			
			
			
           prepareInsertCreate.executeUpdate();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	return true;
	}

}
