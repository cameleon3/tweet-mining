package fr.data.util;

import java.sql.Date;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import fr.data.preprocess.Tweet;

public class DataManager {
	

	
	public static boolean insertCleanTweet( Tweet tw){
		
		PreparedStatement prepareInsertCreate=null;

		try {

			prepareInsertCreate =(PreparedStatement) GetConnexion.getconnexion().prepareStatement("INSERT INTO tweets_clean (tweet_id, tweet_text_clean, created_at) VALUES(?,?,?);");
			
			
			prepareInsertCreate.setString(1, tw.getId());
			prepareInsertCreate.setString(2, tw.getText());
			prepareInsertCreate.setString(3,tw.getCreate_at());          //(3, (Date) tw.getCreate_at());
			
			
			
           prepareInsertCreate.executeUpdate();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	return true;
	}
	
public static boolean updateTweetDate( Tweet tw){
		
		PreparedStatement prepareInsertCreate=null;

		try {

			prepareInsertCreate =(PreparedStatement) GetConnexion.getconnexion().prepareStatement("update tweets set tweet_date=? where tweet_id=?;");
			
			prepareInsertCreate.setString(1, tw.getDate());
			prepareInsertCreate.setString(2, tw.getId());
			
			
			
			
           prepareInsertCreate.executeUpdate();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	return true;
	}

}
