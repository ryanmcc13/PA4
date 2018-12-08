import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


public class QueryProcessor {

    private PositionalIndex pi;
    private PreProcessor pp;
    private int size;

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
        Iterator<String> it = docs.keySet().iterator();
        this.size = docs.keySet().size();
        PriorityBlockingQueue<CompDoc> q = new PriorityBlockingQueue<CompDoc>(size, new CompDocCompare());
        ExecutorService threads = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayList<String> ans = new ArrayList<String>();
        int count = 0;
        while(it.hasNext())
        {
        	count++;
        	System.out.print("\rfinding relivance for doc : " + count + "/" + size);
            String doc = it.next();
            threads.execute(new CompDoc(doc, Query, this.pi, q));
        }
        try
        {
            threads.shutdown();
            threads.awaitTermination(120, TimeUnit.MINUTES);
        }
        catch(Exception e)
        {
            System.out.println("there was a timeout answer is probably wrong");
        }

        int i = 0;
        while(q.size() > 0 && i < k)
        {
            try{
                CompDoc cd = q.take();
                ans.add(cd.doc);
                i++;
            } catch(Exception e)
            {

            }
        }


        return ans;
    }

    private class CompDoc implements Runnable
    {
        public String doc, query;
        public double rel;
        public PriorityBlockingQueue<CompDoc> q;
        public PositionalIndex pi;
        public CompDoc(String doc, String query, PositionalIndex pi, PriorityBlockingQueue<CompDoc> q)
        {
            this.doc = doc;
            this.query = query;
            this.pi = pi;
            this.q = q;
        }

        public void run()
        {
            System.out.print("\rFinding relevance for document: " + q.size() + "/"+ size);
            this.rel = pi.Relevance(query, doc);
            q.add(this);
        }

        public String toString()
        {
            return this.doc + ": " + rel;
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
