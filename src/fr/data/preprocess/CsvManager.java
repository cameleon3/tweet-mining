package fr.data.preprocess;

import java.io.FileWriter;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvManager {
	 public static void main(String[] args) throws Exception
	   {
		 String csv = "data.csv";
	      CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
	        
	      String [] record = "3,David,Feezor,USA,40".split(",");
	        
	      writer.writeNext(record);
	        
	      writer.close();
	  
	   }

}
