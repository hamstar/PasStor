
package passtor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.io.*;

/**
 *
 * @author chumba
 */
public class PasStorTest {

    static final String LS = System.getProperty("line.separator");

    private PasStor passtor;
    private Entry testEntry;

    public PasStorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
	passtor = new PasStor();
	testEntry = new Entry("example.com", "Heman82", "password");
    }

    @After
    public void tearDown() {
	passtor = null;
    }

    @Test
    public void testGoodUserCanLogin() {
	passtor.UserLogin( PasStor.GOODUSER, "password" );
	assertEquals( PasStor.GOODUSER, passtor.getCurrentUser() );
    }

    @Test
    public void testBadUserCantLogin() {
	passtor.UserLogin( PasStor.BADUSER, "password" );
	assertNotSame( PasStor.BADUSER, passtor.getCurrentUser() );
    }

    @Test
    public void testGetEmptyPasswordList() {
	ArrayList list = passtor.getPasswordList();
	
	assertEquals( list.size(), 0 );
    }

    @Test
    public void testAddPassword()
    {
	assertTrue( passtor.addEntry( testEntry ) );
    }

    @Test
    public void testPasswordListHasEntries() {

	// Add the entry three times
	passtor.addEntry( testEntry );
	passtor.addEntry( testEntry );
	passtor.addEntry( testEntry );

	ArrayList list = passtor.getPasswordList();

	assertEquals( list.size(), 3 );

    }

    @Test
    public void testPasswordWasSavedToFile()
    {
	// Flush any exisiting lists
	passtor.flushList();

	// Add the entry three times
	passtor.addEntry( testEntry );
	passtor.addEntry( testEntry );
	passtor.addEntry( testEntry );

	String expectedFileContent = "example.com,Heman82,password" + LS
		+ "example.com,Heman82,password" + LS
		+ "example.com,Heman82,password" + LS;

	String fileContents = "";
	try {
	    BufferedReader input =  new BufferedReader(new FileReader( PasStor.LISTDB ));
	    String line = null; //not declared within while loop

	    try
	    {
		while (( line = input.readLine()) != null)
		{
		    fileContents += line+LS;
		}
	    }
	    finally {
		input.close();
	    }
	}
	catch (IOException e)
	{
	    System.err.println( e.getMessage() );
	}

	assertEquals( expectedFileContent, fileContents );
    }

}