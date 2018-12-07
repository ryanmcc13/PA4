import org.junit.*;
import org.junit.runner.*;
import org.junit.rules.*;
import static org.junit.Assert.*;
import mockit.*;
import mockit.integration.junit4.*;
import java.util.*;

@RunWith(JMockit.class)
public class PreProcessorTest
{
    PreProcessor proc;

    @Test
    public void extractTerms()
    {
        String[] terms = PreProcessor.extractTerms("Brett and Willie Mays—are also members of the 3,000 hit club,[5] and Mays ");
        System.out.println(Arrays.toString(terms));
    }

    @Test
    public void extractTerms2() {
        String[] terms = PreProcessor
                .extractTerms("Mays—are 3333212. .asdkfjh the. asdf.123 32.12 3,000 hit club,[5] and Mays ");
        System.out.println(Arrays.toString(terms));
    }
}