
package passtor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.Assert.*;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.Toolkit;


/**
 *
 * @author chumba
 */
public class MainTest {

    static final String LS = System.getProperty("line.separator");

    private Main main;
    private ByteArrayOutputStream outputBuffer;
    private PrintStream out;
    private Scanner in;
    private Entry testEntry;
    private Entry testEntryAlt;
    private PasswordList passwordList;

    public MainTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
	main = new Main();
	outputBuffer = new ByteArrayOutputStream();
	out = new PrintStream(outputBuffer);
	in = null;
	testEntry = new Entry("example.com", "Heman82", "password");
        testEntryAlt = new Entry("example2.com", "Heman82", "password");
    }

    @After
    public void tearDown() {
	main = null;
	testEntry = null;
	in = null;
	out = null;
	outputBuffer.reset();
	outputBuffer = null;
    }

    /**
     * Test of main method, of class Main.
     *
    public void testMain() {
	System.out.println("main");
	String[] args = null;
	Main.main(args);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }*/

    @Test
    public void testPrompt4UserName() {
	in = new Scanner( "Heman" + LS);
	main.prompt4UserName( in, out );

	assertEquals( "Please enter your username: ", outputBuffer.toString() );
    }

    @Test
    public void testPrompt4Password() {
	in = new Scanner("password" + LS);
	main.prompt4Password( in, out );

	assertEquals("Please enter your password: ", outputBuffer.toString() );
    }

    private void simulateEntryOfLoginDetails( String username, String password )
    {
	in = new Scanner(username + LS);
	main.prompt4UserName( in, out );
	in = new Scanner(password + LS);
	main.prompt4Password( in, out );

    }

    private void simulateLogin() {
	simulateEntryOfLoginDetails( "Heman", "password" );
	main.login( out );
	passwordList = new PasswordList( "Heman" );
        passwordList.flush();
        main.setPasswordList(passwordList);
    }

    // AT 1.1
    @Test
    public void testDoUserLogin()
    {

	simulateEntryOfLoginDetails( "Heman", "password" );

	// make a new output buffer, we only want the latest bit printed
	outputBuffer.reset();

	main.login( out );

	assertEquals( LS+ "Welcome to PasStor, Heman" + LS, outputBuffer.toString() );

    }

    // AT 1.2, 1.3
    @Test
    public void testDoUserLoginBadPassword()
    {
	simulateEntryOfLoginDetails( "Heman", "!password" );

	// make a new output buffer, we only want the latest bit printed
	outputBuffer.reset();

	main.login( out );

	String prompt = LS+"Sorry, unrecognized user or password" + LS;

	assertEquals( prompt, outputBuffer.toString() );
    }

    // AT 2.2
    @Test
    public void testPromptAboutEmptyPasswordList()
    {
	simulateLogin();

	// make a new output buffer, we only want the latest bit printed
	outputBuffer.reset();

        main.displayPasswordList(out);

	assertEquals( LS +"No passwords have been entered yet!" + LS+LS, outputBuffer.toString() );
    }


    // AT 2.1
    @Test
    public void testPrintPasswordEntries()
    {
	simulateLogin();

	// Add the entry three times
	passwordList.addEntry( testEntry );
	passwordList.addEntry( testEntryAlt );
	passwordList.addEntry( testEntry );

	outputBuffer.reset();

        main.displayPasswordList(out);

	String printOut = LS + "Showing password list" + LS+LS
		+ "1: example.com\t\tHeman82" + LS
		+ "2: example2.com\t\tHeman82" + LS
		+ "3: example.com\t\tHeman82" + LS+LS;
	
	assertEquals( printOut, outputBuffer.toString() );
    }

    // AT 3.1
    @Test
    public void testViewPassword()
    {
	simulateLogin();

	// Add the entry three times
	passwordList.addEntry( testEntry );

	outputBuffer.reset();

        in = new Scanner("1"+LS);
        main.displayPassword(in, out);

	assertEquals( LS+"View password for which entry?: " +LS
                +"Password is: password" + LS+LS, outputBuffer.toString() );
    }

    // AT 1.2
    @Test
    public void testEntryInvalidValueKeepsPrompting() {
	simulateLogin();

	outputBuffer.reset();

	in = new Scanner(LS+" " + LS+LS+"example.com"+LS+" "+LS+LS+"Heman"+LS+" "+LS+LS+"password"+LS);
	main.addEntryPrompt( in, out );

        String expectedOutput = LS+"Adding a new password entry..."+LS+LS
                +"Please enter the website: Please enter the website: Please enter the website: "
                +"Please enter the user: Please enter the user: Please enter the user: "
                +"Please enter the password: Please enter the password: Please enter the password: "+LS
                +"Saved to database" + LS;

	assertEquals( expectedOutput, outputBuffer.toString() );

    }

    // AT 4.3
    @Test
    public void testEntryPasswordWithSpaces() {
	simulateLogin();
	outputBuffer.reset();

	in = new Scanner(LS+"example.com"+LS+"Heman82"+LS+"password and more password"+LS);
	main.addEntryPrompt(in, out);

        Entry entry = passwordList.getEntry(1);

	assertEquals("password and more password", entry.getPassword() );
    }

    // AT 4.1
    @Test
    public void testEntrySavedSuccessfully() {
	simulateLogin();

	outputBuffer.reset();

	in = new Scanner(LS+testEntry.getWebsite()+LS+testEntry.getUserName()+LS+testEntry.getPassword()+LS);
	main.addEntryPrompt(in, out);

        Entry entry = passwordList.getEntry(1);

	assertTrue( entry.equals(testEntry) );
	
    }

    // AT 5.1
    @Test
    public void testCopyToClipboard() {

        simulateLogin();

        passwordList.addEntry( testEntry );

        outputBuffer.reset();

        in = new Scanner("1"+LS);
        main.copyPasswordToClipboard(in, out);

        String clipboardPassword = null;
        try {
            Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                clipboardPassword = (String) t.getTransferData(DataFlavor.stringFlavor);
            }
        } catch( Exception e ) {
            System.out.println( e.toString() );
            clipboardPassword = e.toString();
        }

        assertEquals( testEntry.getPassword(), clipboardPassword);

    }

    // AT 5.2
    @Test
    public void testUserCanExit() {
	simulateLogin();

	outputBuffer.reset();

	main.quit(out);

	assertEquals("Quitting..."+LS, outputBuffer.toString() );
    }

    // AT 5.3
    @Test
    public void testUserCanDeleteEntry() {

	simulateLogin();

        passwordList.addEntry( testEntry );

        outputBuffer.reset();

        in = new Scanner("1"+LS);
        main.deleteEntryPrompt( in, out );

	assertEquals(LS+"Delete which entry: "+LS+"Entry was deleted"+LS, outputBuffer.toString() );

    }
}