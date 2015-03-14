package fr.data.preprocess;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class BooleanData {
	private static  Map<String,String> listeMot=new HashMap<String,String>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	
	public static Map getWorkList()
	{
		
		
		
		return listeMot;
	}
	
	public void add(String mot)
	{
		if(!exist(mot))
		listeMot.put(mot,mot);
	}
	
	public String findById(String id){
		
		return listeMot.get(id);
	}
	
	public  boolean exist(String id)
	{
		return listeMot.get(id)!=null;
	}
	
	public void afficheTout(){
		Set<String> lesId=listeMot.keySet();
		
		for(String unId:lesId){
			String mot=listeMot.get(unId);
			System.out.println(mot);
					
		}
			
	}

}
