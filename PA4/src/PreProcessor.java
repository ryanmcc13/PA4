import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//copied from pdf but check the list of symbols:
//Do not remove any STOP words. Every word is a term convert every word into lowercase. 
//Remove ONLY the following symbols from the text: · , ” “ ? [ ] ` { } : ; ( ) 
//However, do not remove period if it is part of a decimal number, 
//i.e, for example do not remove period from 9.23.

//To use PreProcessor call constructor, then use PreProcessor.getDictionary to get the dictionary
//the dft otherwise known as the number of documents in which the term appears can be found with PreProcessor.getDictionary.size()
public class PreProcessor {
	private Map<String, HashMap<Integer, ArrayList<Integer>>> positionalIndex;
	//ties document id to document title
	private Map<String, Integer> docList;
	private int NumberOfDocuments;
	
	public PreProcessor(String folder) {
		positionalIndex = new HashMap<String, HashMap<Integer, ArrayList<Integer>>>();
		docList = new HashMap<String,Integer>();
		File dir = new File(folder);
		File[] library = dir.listFiles();
		int docId = 0;
		if (library != null) {
		    for (File child : library) {
		    	 int wordPosition = 0;
		    	 docList.put(child.getName(),docId );
		    	 BufferedReader br = null;
		    	 String currentLine;
		    	 try {
					br = new BufferedReader(new FileReader(child));
		    	 while ((currentLine = br.readLine()) != null) {
		    	   //TODO figure out regex
		    		 //took out for testing: .replaceAll("regex expression to be added here", "")
		    	   String[] words = currentLine.toLowerCase().split("\\s+");
		    	   for(int i=0;i<words.length;i++) {
		    		   if(!positionalIndex.containsKey(words[i])) {
		    			   positionalIndex.put(words[i], new HashMap<Integer, ArrayList<Integer>>());
		    		   }
		    		   if(!positionalIndex.get(words[i]).containsKey(docId)) {
		    			   positionalIndex.get(words[i]).put(docId, new ArrayList<Integer>());
		    		   }
		    		   positionalIndex.get(words[i]).get(docId).add(wordPosition+i);
		    	   }
		    	   wordPosition += words.length;
		    	 }
		    	 br.close();
		    	 docId++;
				} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    NumberOfDocuments = docId+1;
		  } else {
			  System.out.println("The folder " + folder +" was not found");
		  }
		
	}
	
	public Map<String, HashMap<Integer, ArrayList<Integer>>> getDictionary(){
		return positionalIndex;
	}
	
	public Map<String, Integer> getDocumentList(){
		return docList;
	}
	
	public int getNumberOfDocuments(){
		return NumberOfDocuments;
	}

}
