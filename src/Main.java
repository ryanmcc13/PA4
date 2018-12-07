import java.io.IOException;

public class Main {

	public static void main(String[]args) throws IOException {
//		PositionalIndex pi = new PositionalIndex("src/files/IR");
//		System.out.println(pi.docFrequency("ethiopian"));
//		System.out.println(pi.proc.getDictionary().get("ethiopian"));
		QueryProcessor qp = new QueryProcessor("src/other");
		qp.topKDocs("Zulu Tribe", 5);
		
	}

}
