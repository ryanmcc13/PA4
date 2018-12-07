import java.io.IOException;

public class Main {

	public static void main(String[]args) throws IOException {
        if(args.length < 2)
        {
            System.out.println("you must supply directoy, and at least one query");
            System.exit(-1);
        }
		QueryProcessor q = new QueryProcessor(args[0]);

	}

}
