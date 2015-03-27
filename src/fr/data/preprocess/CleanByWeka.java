package fr.data.preprocess;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class CleanByWeka {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		   CSVReader reader = new CSVReader(new FileReader("belem.csv"));
		   reader.readNext();
   		String [] nextLine;
  			 while ((nextLine = reader.readNext()) != null)
  			 	 {
                  for(String valeur:nextLine)
                  		System.out.print (valeur+" # ");
                  	System.out.println ();
   		    }


	}


}
