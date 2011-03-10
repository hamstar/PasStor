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
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.Assert.*;

/**
 *
 * @author chumba
 */
public class MainTest {

    static final String LS = System.getProperty("line.separator");

    private Main main;
    private PasStor passtor;
    private ByteArrayOutputStream outputBuffer;
    private PrintStream out;
    private Scanner in;
    private Entry testEntry;

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
	passtor = new PasStor();
	outputBuffer = new ByteArrayOutputStream();
	out = new PrintStream(outputBuffer);
	in = null;
	testEntry = new Entry("example.com", "Heman82", "password");
    }

    @After
    public void tearDown() {
	main = null;
	passtor = null;
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
	in = new Scanner(PasStor.GOODUSER + LS);
	main.prompt4UserName( in, out );

	assertEquals( "Please enter your username:"+LS, outputBuffer.toString() );
    }

    @Test
    public void testPrompt4Password() {
	in = new Scanner("password" + LS);
	main.prompt4Password( in, out );

	assertEquals("Please enter your password:" + LS, outputBuffer.toString() );
    }

    private void simulateEntryOfLoginDetails( String username, String password )
    {
	in = new Scanner(username + LS);
	main.prompt4UserName( in, out );
	in = new Scanner(password + LS);
	main.prompt4Password( in, out );

    }

    private void simulateLogin() {
	simulateEntryOfLoginDetails( PasStor.GOODUSER, "password" );
	main.doUserLogin( out, passtor );
    }

    @Test
    public void testDoUserLogin()
    {

	simulateEntryOfLoginDetails( PasStor.GOODUSER, "password" );

	// make a new output buffer, we only want the latest bit printed
	outputBuffer.reset();

	main.doUserLogin( out, passtor );

	assertEquals( "Welcome to PasStor, " + PasStor.GOODUSER + LS, outputBuffer.toString() );

    }

    @Test
    public void testDoUserLoginBadPassword()
    {
	simulateEntryOfLoginDetails( PasStor.GOODUSER, "!password" );

	// make a new output buffer, we only want the latest bit printed
	outputBuffer.reset();

	main.doUserLogin( out, passtor );

	String prompt = "Sorry, unrecognized user or password" + LS
		+ "Please enter your username:" + LS;

	assertEquals( prompt, outputBuffer.toString() );
    }

    @Test
    public void testPromptAboutEmptyPasswordList()
    {
	simulateLogin();

	// make a new output buffer, we only want the latest bit printed
	outputBuffer.reset();

	in = new Scanner( "l" + LS );
	main.commandPrompt( in, out, passtor );

	assertEquals( "Enter command: No passwords have been entered yet!" + LS, outputBuffer.toString() );
    }

    @Test
    public void testPrintPasswordEntries()
    {
	simulateLogin();

	// Add the entry three times
	passtor.addEntry( testEntry );
	passtor.addEntry( testEntry );
	passtor.addEntry( testEntry );

	outputBuffer.reset();
	in = new Scanner("l" + LS);
	main.commandPrompt( in, out, passtor );

	String printOut = "Enter command: "
		+ "Printing passwords for " + passtor.getCurrentUser() + LS
		+ "0:\texample.com\tHeman82" + LS
		+ "1:\texample.com\tHeman82" + LS
		+ "2:\texample.com\tHeman82" + LS;
	
	assertEquals( printOut, outputBuffer.toString() );
    }

    @Test
    public void testViewPassword()
    {
	simulateLogin();

	// Add the entry three times
	passtor.addEntry( testEntry );

	in = new Scanner("v0" + LS);
	outputBuffer.reset();
	main.commandPrompt( in, out, passtor );

	assertEquals( "Enter command: Password: password" + LS, outputBuffer.toString() );
    }

    @Test
    public void testEntryForgottenFieldPromptsUser() {
	simulateLogin();

	outputBuffer.reset();

	in = new Scanner("n example.com Heman82" + LS);
	main.commandPrompt( in, out, passtor );

	assertEquals("Enter command: You forgot to enter a field!" + LS, outputBuffer.toString() );

    }

    @Test
    public void testEntryPasswordWithSpaces() {
	simulateLogin();
	outputBuffer.reset();

	in = new Scanner("n example.com Heman82 password and more password"+LS);
	main.commandPrompt(in, out, passtor);

	assertEquals("Enter command: Entry Saved"+LS, this.outputBuffer.toString() );
    }

    @Test
    public void testEntrySavedSuccessfully() {
	simulateLogin();

	outputBuffer.reset();

	in = new Scanner("n example.com Heman82 password" +LS);
	main.commandPrompt(in, out, passtor);

	assertEquals("Enter command: Entry Saved"+LS, this.outputBuffer.toString() );
	
    }

}