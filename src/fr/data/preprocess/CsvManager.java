package fr.data.preprocess;

import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvManager {
	 public static void main(String[] args) throws Exception
	   {
		 String csv = "data.csv";
	      CSVWriter writer = new CSVWriter(new FileWriter(csv, true),',',CSVWriter.NO_QUOTE_CHARACTER);
	        
	      String [] record = "3,Bekem,Feezor,USA,40".split(",");
	        
	      writer.writeNext(record);
	        
	      writer.close();
	  
	   }

	 
	 public static CSVWriter initiliaze(String name)
	 {
		 CSVWriter writer=null;
		 try {
			writer = new CSVWriter(new FileWriter(name, true),',',CSVWriter.NO_QUOTE_CHARACTER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return writer;
		 
	 }
	 public static void closeCsv(CSVWriter writer)
	 {
		 try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public static void writeLine(CSVWriter writer,String line)
	 {
		  String [] record = line.split(",");
	        
	      writer.writeNext(record);
		 
	 }

}
