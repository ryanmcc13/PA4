import java.util.ArrayList;
import java.util.List;

public class PositionalIndex {
	
	PreProcessor proc;
	/**
	 * Gets the name of a folder containing document collection as parameter.
	 * @param folder
	 */
	public PositionalIndex(String folder) {
		proc = new PreProcessor(folder);
	}
	
	/**
	 * Returns the number of times term appears in doc.
	 * @param term
	 * @param Doc
	 * @return
	 */
	public int termFrequency(String term, String Doc) {
		int DocId = proc.getDocumentList().get(Doc);
		return proc.getDictionary().get(term).get(DocId).size();
	}
	
	/**
	 * returns the number of documents in which term appears. 
	 * @param term
	 * @return
	 */
	public int docFrequency(String term) {
		return proc.getDictionary().get(term).size();
	}
	
	/**
	 *  Returns string representation of the postings(t).
	 *   The returned String must be in following format.
	 *   [<DocName1 : pos1,pos2,···>,<DocName2 : pos1,pos2,···>···]
	 * @param t
	 * @return
	 */
	public String postingsList(String t) {
		//does this fit the format
		return proc.getDictionary().get(t).toString();
	}
	
	/**
	 * Returns the weight of term t in document d (as per the weighing mechanism described above). 
	 * @param t
	 * @param d
	 * @return
	 */
	public double weight(String t, String d) {
		//assuming frequency is like in the ir notes the number of times the term appears in the doc
		double TFij = (double)termFrequency(t,d);
		double N = (double)proc.getNumberOfDocuments();
		double dfti = (double)docFrequency(t);
		//formula from project pdf
		return Math.sqrt(TFij)*Math.log10(N/dfti);
	}
	
	/**
	 * Returns TPScore(query,doc). 
	 * @param query
	 * @param doc
	 * @return
	 */
	public double TPScore(String query, String doc) {
		if(!proc.getDocumentList().containsKey(doc)) {
			System.out.println("Document not found");
			return 0;
		}
		//split query into terms
		String[] terms = query.split("\\s+");
		//if query is size one then return 0
		if(terms.length<2) {
			return 0;
		}
		double sumDistance = 0;
		int DocId = proc.getDocumentList().get(doc); 
		for(int i=0;i<terms.length-2;i++) {
			sumDistance+=distance(terms[i],terms[i+1],DocId); 
		}
		return terms.length/sumDistance;
	}
	
	//helper method for TPScore that calculates distance
	private double distance(String t1, String t2, int doc) {
		
		ArrayList<Integer>postings1 = proc.getDictionary().get(t1).get(doc);
		ArrayList<Integer>postings2 = proc.getDictionary().get(t2).get(doc);
		return 0;
		
	}

	/**
	 * Returns V SScore(query,doc).
	 * @param query
	 * @param doc
	 * @return
	 */
	public double VSScore(String query, String doc) {
		return 0;
	}
	
	/**
	 * Returns 0.6×TSScore(query,doc)+0.4×V SScore(query,doc). 
	 * @param query
	 * @param doc
	 * @return
	 */
	public double Relevance(String query, String doc) {
		return 0.6*TPScore(query, doc) + 0.4 *VSScore(query, doc);
	}
}
