package fr.data.preprocess;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import au.com.bytecode.opencsv.CSVWriter;

import com.mysql.jdbc.PreparedStatement;

import fr.data.util.GetConnexion;


public class GetAllWord {
	private static  Map<String,String> listeMot=new LinkedHashMap<String,String>();

	public static void main(String[] args) {

		
		 PreparedStatement statmentListe=null;
			ResultSet resultat=null;
			

			try {
				statmentListe =(PreparedStatement) GetConnexion.getconnexion().prepareStatement("select tweet_text_clean text from tweets_clean  order by created_at asc limit 0,20000;");
				
				resultat =  statmentListe.executeQuery();
				
				while(resultat.next()){
					
					String text=resultat.getString("text");
					
					StringTokenizer st = new StringTokenizer(text);
					
					while (st.hasMoreElements()) {
				
						add((String) st.nextElement());
					}
			 
					  
					
				}
				
				
				SaveMap();
				System.out.println("la taille est : "+listeMot.size());
				//afficheTout();
			
				
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}

	
		
	
	
	
	
	public static Map<String,String> getWorkList()
	{
		
		
		
		return listeMot;
	}
	
	public static void add(String mot)
	{
		if(mot.contains("inf"))
			System.out.println(" "+mot);
	
		if(!exist(mot.trim())&&!mot.contains("'"))
		listeMot.put(mot.trim(),mot.trim());
	}
	
	public static String findById(String id){
		
		return listeMot.get(id);
	}
	
	public static  boolean exist(String id)
	{
		return listeMot.get(id)!=null;
	}
	
	public static void afficheTout(){
		
		Set<String> lesId=listeMot.keySet();
		
		for(String unId:lesId){
			String mot=listeMot.get(unId);
			
			System.out.println(mot);
					
		}
			
	}
	
	public static void SaveMap(){
		CSVWriter csvWriter= CsvManager.initiliaze("data.csv");
		
		CsvManager.writeLine(csvWriter,MapToString());
		CsvManager.closeCsv(csvWriter);
		
	}
	
	public static String MapToString(){
Set<String> lesId=listeMot.keySet();

String line="";
boolean h=true;
		
		for(String unId:lesId){
			if(h)
			{
				line+=listeMot.get(unId);
				h=!h;
			}else{
				line+=","+listeMot.get(unId);
			}
		
					
		}
		return line;
		
	}
	


	 

}
