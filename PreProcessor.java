import java.util.HashMap;
import java.util.Map;

//copied from pdf but check the list of symbols:
//Do not remove any STOP words. Every word is a term convert every word into lowercase. 
//Remove ONLY the following symbols from the text: · , ” “ ? [ ] ` { } : ; ( ) 
//However, do not remove period if it is part of a decimal number, 
//i.e, for example do not remove period from 9.23.

public class PreProcessor {
	Map<String, Integer[]> positionalIndex;
	//ties document id to document title
	Map<Integer, String> docList;
	
	
	public PreProcessor(String folder) {
		
	}
	
	public static HashMap<String, Integer[]> process(String folder){
		return null;
		
	}

}
