import org.junit.*;
import org.junit.runner.*;
import org.junit.rules.*;
import static org.junit.Assert.*;
import mockit.*;
import mockit.integration.junit4.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

@RunWith(JMockit.class)
public class QueryProcessorTest
{
    @Mocked public PositionalIndex pi;
    @Mocked public PreProcessor pp;
    @Tested public QueryProcessor q;

    @Before
    public void setUp()
    {
        this.q = new QueryProcessor(pi, pp);
    }

    @Test
    public void topTest(@Mocked Map<String,Integer> map,@Mocked Set<String> s, @Mocked Iterator<String> it)
    {
        new NonStrictExpectations()
        {{
            pp.getDocumentList(); result=map;
            map.keySet(); result=s;
            s.iterator(); result=it;
            it.hasNext(); returns(true,true,true,true,true,true,true,true,true,true,false);
            it.next(); returns("doc0","doc1","doc2","doc3","doc4","doc5","doc6","doc7","doc8","doc9");
            pi.Relevance(anyString, anyString); returns(1d, 2d,3d,4d,5d,6d,7d,8d,9d,10d);
        }};

        ArrayList<String> ans = q.topKDocs("map", 5);

        for(int i =0; i < 5; i++)
        {
            assertEquals("doc" + (10-1-i), ans.get(i));
        }

        new VerificationsInOrder() {
            {
                pi.Relevance(anyString, anyString); times=10;
            }
        };
    }
}