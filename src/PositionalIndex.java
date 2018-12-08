import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;
import java.util.HashMap;

public class PositionalIndex {

    PreProcessor proc;

    //HashMap<String, ArrayList<Double>> docVector = new HashMap<String, ArrayList<Double>>();
    HashMap<String, ArrayList<Double>> queryVector = new HashMap<String, ArrayList<Double>>();

    /**
     * Gets the name of a folder containing document collection as parameter.
     * @param folder
     */
    public PositionalIndex(String folder) {
        proc = new PreProcessor(folder);
        //this.makeDocVectors();
    }

    /**
     * Returns the number of times term appears in doc.
     * @param term
     * @param Doc
     * @return
     */
    public int termFrequency(String term, String Doc) {
    	if(!proc.getDocumentList().containsKey(Doc)) {
    		return 0;
    	}
        int DocId = proc.getDocumentList().get(Doc);
        if(!proc.getDictionary().containsKey(term)) {
        	return 0;
        }
        if(!proc.getDictionary().get(term).containsKey(DocId)) {
        	return 0;
        }
        return proc.getDictionary().get(term).get(DocId).size();
    }

    /**
     * returns the number of documents in which term appears.
     * @param term
     * @return
     */
    public int docFrequency(String term) {
    	if(!proc.getDictionary().containsKey(term)) {
    		return 0;
    	}
        return proc.getDictionary().get(term).size();
    }

    /**
     * Returns string representation of the postings(t). The returned String must be
     * in following format. [<DocName1 : pos1,pos2,иии>,<DocName2 :
     * pos1,pos2,иии>иии]
     *
     * @param t
     * @return
     */
    public String postingsList(String t) {
        // does this fit the format
        return proc.getDictionary().get(t).toString();
    }

    /**
     * Returns the weight of term t in document d (as per the weighing mechanism described above).
     * @param t
     * @param d
     * @return
     */
    public double weight(String t, String d) {
        // assuming frequency is like in the ir notes the number of times the term
        // appears in the doc
        double TFij = (double) termFrequency(t, d);
        double N = (double) proc.getNumberOfDocuments();
        double dfti = (double) docFrequency(t);
        // formula from project pdf
        return Math.sqrt(TFij) * Math.log10(N / dfti);
    }

    /**
     * Returns TPScore(query,doc).
     * @param query
     * @param doc
     * @return
     */
    public double TPScore(String query, String doc) {
        if (!proc.getDocumentList().containsKey(doc)) {
            System.out.println("Document not found");
            // what should error return be?
            return 0;
        }
        // split query into terms
        String[] terms = PreProcessor.extractTerms(query);
        // if query is size one then return 0
        if (terms.length < 2) {
            return 0;
        }
        double sumDistance = 0;
        int DocId = proc.getDocumentList().get(doc);
        for (int i = 0; i < terms.length - 1; i++) {
            sumDistance += distance(terms[i], terms[i + 1], DocId);
        }
        return terms.length / sumDistance;
    }

    // helper method for TPScore that calculates distance
    private double distance(String t1, String t2, int doc) {
        //check if dictionary has both terms
    	if (!proc.getDictionary().containsKey(t1) || !proc.getDictionary().containsKey(t2)) {
            return 17;
        }
    	//check if the document is int the postings list for each term
        if(!proc.getDictionary().get(t1).containsKey(doc) || !proc.getDictionary().get(t2).containsKey(doc)) {
        	return 17;
        }
        ArrayList<Integer> postings1 = proc.getDictionary().get(t1).get(doc);
        ArrayList<Integer> postings2 = proc.getDictionary().get(t2).get(doc);
        int min = -1;
        for (int i = 0; i < postings1.size(); i++) {
            for (int j = 0; j < postings2.size(); j++) {
                if (postings1.get(i) < postings2.get(j)) {
                    if (postings2.get(j) - postings1.get(i) < min || min == -1) {
                        min = postings2.get(j) - postings1.get(i);
                    }
                    break;
                }
            }
        }
        if (min == -1) {
            return 17;
        } else {
            return min;
        }

    }

    // public void makeDocVectors()
    // {
    //     Iterator<String> docs = proc.getDocumentList().keySet().iterator();
    //     while(docs.hasNext())
    //     {
    //         String doc = docs.next();
    //         if (!proc.getDocumentList().containsKey(doc)) {
    //             System.out.println("Document not found");
    //             // what should error return be?
    //             return;
    //         }
    //         Set<String> allterms = proc.getDictionary().keySet();
    //         ArrayList<Double> vectorDoc = new ArrayList<Double>();
    //         Iterator<String> itForDoc = allterms.iterator();
    //         while (itForDoc.hasNext()) {
    //             String term = itForDoc.next();
    //             vectorDoc.add(weight(term, doc));
    //         }
    //         this.docVector.put(doc, vectorDoc);
    //     }
    // }

    /**
     * Returns V SScore(query,doc).
     * @param query
     * @param doc
     * @return
     */
    public double VSScore(String query, String doc) {
        // calculate vector of the query and the vector of the doc and then cosine
        // similarity them
    	if (!proc.getDocumentList().containsKey(doc)) {
            System.out.println("Document not found");
            // what should error return be?
            return 0;
        }
        boolean q = false;
        ArrayList<Double> vectorQuery = this.queryVector.get(query);
        ArrayList<Double> vectorDoc = new ArrayList<Double>();
        Set<String> allterms = proc.getDictionary().keySet();
        Iterator<String> itForDoc = allterms.iterator();
        if(vectorQuery == null)
        {
            vectorQuery = new ArrayList<Double>();
            q = true;
        }
        while (itForDoc.hasNext()) {
            String term = itForDoc.next();
            if(q)
                vectorQuery.add(weightForQuery(term, query));
            vectorDoc.add(weight(term, doc));
        }
        if(q)
            this.queryVector.put(query, vectorQuery);

        return cosineSimilarity(vectorQuery, vectorDoc);
    }

    // helpermethod for getting cosine similarity
    public static double cosineSimilarity(ArrayList<Double> vector1, ArrayList<Double> vector2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        for (int i = 0; i < vector1.size(); i++) {
            dotProduct += vector1.get(i) * vector2.get(i);
            norm1 += Math.pow(vector1.get(i), 2);
            norm2 += Math.pow(vector2.get(i), 2);
        }
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    // helper for determining weight from a query
    public double weightForQuery(String t, String q) {
        String[] queryTerms = PreProcessor.extractTerms(q);
        double weight = 0;
        for (int i = 0; i < queryTerms.length; i++) {
            if (t.equals(queryTerms[i])) {
                weight++;
            }
        }
        return weight;
    }

    /**
     * Returns 0.6ОTSScore(query,doc)+0.4ОV SScore(query,doc).
     * @param query
     * @param doc
     * @return
     */
    public double Relevance(String query, String doc) {
    	//return TPScore(query,doc);
        return 0.6*TPScore(query, doc) + 0.4 *VSScore(query, doc);
    }
}
