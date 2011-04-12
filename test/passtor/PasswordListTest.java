/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package passtor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author chumba
 */
public class PasswordListTest {

    private PasswordList pl;
    private Entry testEntry;
    private Entry testEntryAlt;

    public PasswordListTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    
	pl = new PasswordList("Heman");
	pl.flush();
	testEntry = new Entry("example.com", "Heman82", "password");
	testEntryAlt = new Entry("example2.com", "Heman82", "password");
    }

    @After
    public void tearDown() {

	pl = null;
    }

    /**
     * Test of size method, of class PasswordList.
     */
    @Test
    public void testZeroSize() {

	assertEquals( 0, pl.size() );
    }

    /**
     * Test of flush method, of class PasswordList.
     */
    @Test
    public void testFlush() {

	pl.addEntry( testEntry );
	pl.flush();

	assertEquals( 0, pl.size() );
    }

    @Test
    public void testAddEntryChangesSize() {

	pl.addEntry( testEntry );

	assertEquals( 1, pl.size() );
    }

    @Test
    public void testAddEntry() {

	assertTrue( pl.addEntry( testEntry ) );
    }

    @Test
    public void testGetEntry() {

	pl.addEntry( testEntry );

	assertTrue( pl.getEntry(1).equals(testEntry) );
    }

    @Test
    public void testAddMultipleEntries() {

	pl.addEntry( testEntry );
	pl.addEntry( testEntryAlt );
	pl.addEntry( testEntry );
	
	assertEquals( 3, pl.size() );
    }

    @Test
    public void testDeleteEntry() {

	pl.addEntry( testEntry );
	pl.addEntry( testEntryAlt );
	pl.addEntry( testEntry );

	pl.deleteEntry( 2 );

	assertEquals( 2, pl.size() );
    }

    @Test
    public void testSizeAlwaysReturnsIntWhenEmpty() {

	String zerolength = "";

	try {
	    zerolength = String.valueOf( pl.size() );
	} catch( Exception e ) {
	    zerolength = e.toString();
	}

	assertEquals( "0", zerolength );
    }

    @Test
    public void testSizeAlwaysReturnsIntWithOneEntry() {

	String onelength = "";

	pl.addEntry(testEntry);
	try {
	    onelength = String.valueOf( pl.size() );
	} catch( Exception e ) {
	    onelength = e.toString();
	}

	assertEquals( "1", onelength );
    }

}