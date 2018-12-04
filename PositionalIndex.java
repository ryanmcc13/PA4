import java.util.ArrayList;
import java.util.List;

public class PositionalIndex {
	
	/**
	 * Gets the name of a folder containing document collection as parameter.
	 * @param folder
	 */
	public PositionalIndex(String folder) {
		
	}
	
	/**
	 * Returns the number of times term appears in doc.
	 * @param term
	 * @param Doc
	 * @return
	 */
	public int termFrequency(String term, String Doc) {
		return 0;
	}
	
	/**
	 * returns the number of documents in which term appears. 
	 * @param term
	 * @return
	 */
	public int docFrequency(String term) {
		return 0;
	}
	
	/**
	 *  Returns string representation of the postings(t).
	 *   The returned String must be in following format.
	 *   [<DocName1 : pos1,pos2,···>,<DocName2 : pos1,pos2,···>···]
	 * @param t
	 * @return
	 */
	public List<ArrayList<Integer>> postingsList(String t) {
		return null;
	}
	
	/**
	 * Returns the weight of term t in document d (as per the weighing mechanism described above). 
	 * @param t
	 * @param d
	 * @return
	 */
	public double weight(String t, String d) {
		return 0;
	}
	
	/**
	 * Returns TPScore(query,doc). 
	 * @param query
	 * @param doc
	 * @return
	 */
	public double TPScore(String query, String doc) {
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
