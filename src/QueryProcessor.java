import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Iterator;
import java.util.PriorityQueue;


public class QueryProcessor {

    private PositionalIndex pi;
    private PreProcessor pp;

    public QueryProcessor(String folder)
    {
        this.pi = new PositionalIndex(folder);
        this.pp = pi.proc;
    }

    protected QueryProcessor(PositionalIndex pi, PreProcessor pp)
    {
        this.pi = pi;
        this.pp = pp;
    }

    /**
     *  gets a query and an integer k as parameter and returns an arraylist
     *  consisting of top k documents that are relevant to the query.
     * @param Query
     * @param k
     * @return
     */
    public ArrayList<String> topKDocs(String Query, int k){
        Map<String, Integer> docs = this.pp.getDocumentList();
        PriorityQueue<CompDoc> q = new PriorityQueue<CompDoc>(new CompDocCompare());
        Iterator<String> it = docs.keySet().iterator();
        ArrayList<String> ans = new ArrayList<String>();
        while(it.hasNext())
        {
            String doc = it.next();
            q.add(new CompDoc(doc, this.pi.Relevance(Query, doc)));
        }
        for(int i = 0; i < k; i++)
        {
            ans.add(q.poll().doc);
        }
        return ans;
    }

    private class CompDoc
    {
        public String doc;
        public double rel;
        public CompDoc(String doc, double rel)
        {
            this.doc = doc;
            this.rel = rel;
        }
    }

    public class CompDocCompare implements Comparator<CompDoc>
    {
        public int compare(CompDoc d1, CompDoc d2)
        {
            double a = d1.rel - d2.rel;
            if(a < 0)
                return 1;
            if(a > 0)
                return -1;
            return 0;
        }
    }
}
