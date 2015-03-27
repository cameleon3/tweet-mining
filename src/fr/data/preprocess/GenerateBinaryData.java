package fr.data.preprocess;


import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.mysql.jdbc.PreparedStatement;

import fr.data.util.GetConnexion;

public class GenerateBinaryData {
	
	
	private static  Map<String,String> listeMot=new LinkedHashMap<String,String>();

	public static void main(String[] args) throws IOException {
		
		   CSVReader reader = new CSVReader(new FileReader("data.csv"));
		
		   
		   ///charger l'entetedu fichier
   		String [] nextLine;
  			 while ((nextLine = reader.readNext()) != null)
  			 	 {
                  for(String valeur:nextLine)
                  		add(valeur);
   		    }
  			 
  			 reader.close();
  			 
  			System.out.println("la taille est : "+listeMot.size());
  			 
  			CSVWriter csvWriter= CsvManager.initiliaze("data.csv");

  			 
  			 
  			PreparedStatement statmentListe=null;
 			ResultSet resultat=null;
 			

 			try {
 				statmentListe =(PreparedStatement) GetConnexion.getconnexion().prepareStatement("select tweet_text_clean text from tweets_clean  order by created_at asc limit 0,2000;");
 				
 				resultat =  statmentListe.executeQuery();
 				
 				while(resultat.next()){
 					
 					String text=resultat.getString("text");
 					
 					writeLine(csvWriter, getLine(text));
 					  
 					
 				}
 				
 				CsvManager.closeCsv(csvWriter);
 			
 				
 			} 
 			catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}


		

	}
	
	
	
	public static String getLine(String text)
	{
            boolean h=false;
			String line="";
			Set<String> lesId=listeMot.keySet();
			
			for(String unId:lesId){
				String mot=listeMot.get(unId);
				
				if(!h){
					
					if(containWord(text, mot, true))
					{
						line+="y";
					}
					else
					{
						line+="n";
					}
					h=!h;
				}
				else{
					
					if(containWord(text, mot, true))
					{
						line+=",y";
					}
					else
					{
						line+=",n";
					}
					
				}
		
			}
			
			return line;
	 
		
	}
	
	
	/**
	 * ecriture d'une ligne dans le fichier csv
	 * @param csvWriter
	 * @param line
	 */
	public static void writeLine(CSVWriter csvWriter,String line){

		String [] record = line.split(",");
        
		csvWriter.writeNext(record);
		
	}
	
	/**
	 *  fonction de comparaison
	 * @param sentence
	 * @param word
	 * @param strict
	 * @return
	 */
	public static boolean containWord(String sentence,String word,boolean strict){
		
		if(strict)
			return containWordStrict(sentence, word);
		
		return containWordLarge(sentence, word);
	}
	
	
	/**
	 * egalité strict
	 * @param sentence
	 * @param word
	 * @return
	 */
public static boolean containWordStrict(String sentence,String word){
	
	StringTokenizer st = new StringTokenizer(sentence);

	String current;
	
	while (st.hasMoreElements()) {
		current=(String) st.nextElement();
		if(current.equals(word))
		return true;
	}
		
		return false;
	}

/**
 * egalité large
 * @param sentence
 * @param word
 * @return
 */
public static boolean containWordLarge(String sentence,String word){
	
	if(sentence.contains(word))
		return true;
	
	return false;
}
	
	
	
	
	
	
	
	public static void add(String mot)
	{
		
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

}