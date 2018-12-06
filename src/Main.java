import java.io.IOException;

public class Main {

	public static void main(String[]args) throws IOException {
		PreProcessor proc = new PreProcessor("src/stuff");
		System.out.println(proc.getDictionary().get("this").get(0));
		System.out.println(proc.getDictionary().get("this").get(1));
		System.out.println(proc.getDictionary().get("multiple").get(0));
		System.out.println(proc.getDictionary().get("multiple").get(1));
		
	}

}
