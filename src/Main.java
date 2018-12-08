import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[]args) throws IOException {
        if(args.length < 2)
        {
            System.out.println("you must supply directoy, and at least one query");
            System.exit(-1);
        }
        long time = System.currentTimeMillis();
        QueryProcessor q = new QueryProcessor(args[0]);
        for(int i = 1; i < args.length; i++)
        {
            ArrayList<String> top = q.topKDocs(args[i], 10);
            for(int j = 0; j < top.size(); j++)
                System.out.print("\n" + top.get(j) +
                    "\n\tVS= " + q.pi.VSScore(args[i], top.get(j)) +
                    "\n\tTP= " + q.pi.TPScore(args[i], top.get(j)) +
                    "\n\tRelevance= " + q.pi.Relevance(args[i], top.get(j))
                );
        }
        System.out.println("\nTime Taken: " + ((System.currentTimeMillis() - time)/60000));
	}

}
