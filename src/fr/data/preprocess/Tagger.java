package fr.data.preprocess;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mysql.jdbc.PreparedStatement;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import fr.data.util.DataManager;
import fr.data.util.GetConnexion;
import fr.uvsq.Lemmatizer;


/** This demo shows user-provided sentences (i.e., {@code List<HasWord>})
 *  being tagged by the tagger. The sentences are generated by direct use
 *  of the DocumentPreprocessor class.
 *
 *  @author Christopher Manning
 */
class Tagger {

  private Tagger() {}
	 public static  List<String> retenu=new ArrayList<String>();
	 public static  List<String> listTag=new ArrayList<String>();

  public static void main(String[] args) throws Exception {

	  retenu.add("NPP");
	  retenu.add("NC");
	  retenu.add("ADJ");
	  retenu.add("ADJWH");
	  retenu.add("ET");//foreign language
	  retenu.add("N");
	  retenu.add("V");
	  retenu.add("VS");
	  retenu.add("VPR");
	  retenu.add("VPP");
	  retenu.add("VINF");
	  retenu.add("VIMP");
	  
	  

	  
	  
	  
	  args=new String[2];
	  args[0]="models/french.tagger";
	  args[1]="input/sample-input.txt";
    if (args.length != 2) {
      System.err.println("usage: java TaggerDemo2 modelFile fileToTag");
      return;
    }
    MaxentTagger tagger = new MaxentTagger(args[0]);
    TokenizerFactory<CoreLabel> ptbTokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(),
									   "untokenizable=noneKeep");
    BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(args[1]), "utf-8"));
    PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out, "utf-8"));
    DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor(r);
    documentPreprocessor.setTokenizerFactory(ptbTokenizerFactory);
    for (List<HasWord> sentence : documentPreprocessor) {
      List<TaggedWord> tSentence = tagger.tagSentence(sentence);
      //pw.println(Sentence.listToString(tSentence, false));
    }
    
    
    PreparedStatement statmentListe=null;
	ResultSet resultat=null;
	
	 String text = "";
	
	try {
		statmentListe =(PreparedStatement) GetConnexion.getconnexion().prepareStatement("select tweet_text,tweet_id,created_at from tweets");
		
		resultat =  statmentListe.executeQuery();
		Tweet tweet=new Tweet();
		while(resultat.next()){
			text=resultat.getString("tweet_text");
			
			/**
			 * effacer les guillemé et quote pour eviter les incompatibilités en csv
			 */
			text=text.replaceAll("\"", " ");
			text=text.replaceAll("'", " ");
			text=text.replaceAll("’", " ");///eviter d’information
			text=text.replaceAll("%", " ");
			text=text.replaceAll(",", " ");
			
			
			
			
			String result="";
			
			   DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text));
			    for (List<HasWord> sentence : tokenizer) {
			  

			    
			      List<TaggedWord> tagged = tagger.tagSentence(sentence);
			      for (TaggedWord tw : tagged) {
			    
			    	  
			    	  
			    	
			    		 
						  if(existTag(tw.tag()))
						      {

					    	  if(tw.word().toLowerCase().startsWith("http")||
(tw.word().length()<3 && (!tw.word().toLowerCase().equals("fn")&&!tw.word().toLowerCase().equals("ps")&&!tw.word().toLowerCase().equals("an"))))
								  continue;
							  
							
//						    	 System.out.println(" "+tw.word()+" => "+tw.tag()+" ");
						    	result+=" "+Lemmatizer.getRacine(tw.word(), tw.tag());
						    			
						    	
						      }
						      else if(tw.tag().equals("DET") && isFalsePositive_det(tw.word()))
						      {
						    	  
						    	 result+=" "+tw.word();
						      }
						      else if(tw.tag().equals("ADV") && tw.word().toLowerCase().endsWith("ment")&&!tw.word().toLowerCase().startsWith("http"))
						      {
						    	  result+=" "+tw.word();  
						    	  
						      }
						        result=result.toLowerCase();
			        
			      }
			
			   
			    }
			// System.out.println(resultat.getString("created_at"));
			 
			 DateTime dt = new DateTime(); 
				dt= new DateTime(resultat.getString("created_at").replace(' ', 'T'));
				
			    tweet.setId(resultat.getString("tweet_id"));
			    
			    
			    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss" );
				
			    tweet.setCreate_at(dt.toString( formatter ));
			    tweet.setText(result);
			    if(!result.equals(""))
			    DataManager.insertCleanTweet(tweet);
		    
			  
			
		}
		
		
		
	} 
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    pw.close();
  }			
	
	
	
	
	
  
  
  public  static boolean existTag(String tag){
	  for(String current:retenu){
		  if(current.equals(tag)){
			  return true;
		  }
	  }
	 return false;
  }
  
  public static boolean isFalsePositive_det(String text)
  {
	  if((text.startsWith("#")||text.startsWith("@")) && text.length()>=3)
		  return true;
	  else if(text.startsWith("http"))
		  return false;
	  else if(text.equals("Assemblee")||text.equals("Snowden")||text.equals("Barbusse"))
		  return true;
	  
	  return false;
  }
  
 
  
  public static String getDate(Date date){
	  
	
		return  new DateTime(date).toString( DateTimeFormat.forPattern("yyyy-MM-dd" ) );
	  
  }
  

}
