
package passtor;

import java.io.PrintStream;
import java.util.Scanner;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;


/**
 * Main class
 * @author Robert McLeod
 * @since 11 March 2011
 * @version Sprint1
 */
public class Main {

    private String unauthenticated_user;
    private String unauthenticated_password;

    private String currentUser;
    private PasswordList passwordList;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

	Scanner input = new Scanner(System.in);
        PrintStream output = new PrintStream( System.out );
	Main main = new Main();

	// Prompt for the user to login
	while ( main.getCurrentUser() == null ) {

            main.prompt4UserName( input, output );
            main.prompt4Password( input, output );
            
	    main.login(output);

        }

	PasswordList passwordList = new PasswordList( main.getCurrentUser() );
	main.setPasswordList( passwordList );
	boolean running = true;
	String cmd = "";

	// Take commands from the user and do stuff
	while ( running == true )  {

	    cmd = main.commandPrompt(input, output);

	    try {
		if ( cmd.equals("q") ) {
		    main.quit( output );
		    running = false;
		} else if ( cmd.equals("n")) {
		    main.addEntryPrompt(input, output);
		} else if ( cmd.equals("l")) {
		    main.displayPasswordList(output);
		} else if ( cmd.equals("d") ) {
		    main.deleteEntryPrompt( input, output );
		} else if ( cmd.equals("v") ) {
		    main.displayPassword(input, output);
		} else if ( cmd.equals("c") ) {
		    main.copyPasswordToClipboard( input, output );
		}
	    } catch( Exception e ) {
		output.println("Error: "+e.getMessage());
		output.println();
	    }

	}

    }

    public Main()
    {
    }

    public void quit( PrintStream out ) {
	out.println("Quitting...");
    }

    /**
     * Takes commands and sends them to the correct function
     * @param in
     * @param out
     * @param passtor
     */
    public String commandPrompt( Scanner in, PrintStream out )
    {
	out.print("Enter command: ");
	return in.next();
    }

    /**
     * Prompts for the username
     * @param in
     * @param out
     */
    public void prompt4UserName( Scanner in, PrintStream out )
    {
	out.print("Please enter your username: ");
	unauthenticated_user = in.next();
    }

    /**
     * Prompts for the password
     * @param in
     * @param out
     */
    public void prompt4Password( Scanner in, PrintStream out ) {
	out.print("Please enter your password: ");
	unauthenticated_password = in.next();
    }

    /**
     * Logs user in
     * @param out
     * @param passtor
     */
    public void login( PrintStream out ) {

	out.println();
	if ( unauthenticated_user == null && unauthenticated_password == null)
	    throw new IllegalArgumentException("The user and password cannot be null");

	// Check if the login went well
	if ( Login.validate( unauthenticated_user, unauthenticated_password ) ) {
	    this.currentUser = unauthenticated_user;
	    out.println( "Welcome to PasStor, " + unauthenticated_user ); // access gained
	} else {
	    out.println( "Sorry, unrecognized user or password" );
	}

    }

    public String getCurrentUser() {
	return currentUser;
    }

    public void setPasswordList( PasswordList pl ) {

	this.passwordList = pl;
    }

    public void addEntryPrompt(Scanner in, PrintStream out) {

	out.println();
	out.println("Adding a new password entry...");
	out.println();

	in.nextLine();

        String website = null;
        String user = null;
        String password = null;

        while ( website == null || website.trim().isEmpty() ) {
            out.print("Please enter the website: ");
            website = in.nextLine();
        }

        while ( user == null || user.trim().isEmpty() ) {
            out.print("Please enter the user: ");
            user = in.nextLine();
        }

        while ( password == null || password.trim().isEmpty() ) {
            out.print("Please enter the password: ");
            password = in.nextLine();
        }

	out.println();
	
	if ( passwordList.addEntry( new Entry( website, user, password ) ) ) {
	    out.println("Saved to database");
	} else {
	    out.println("Sorry, couldn't save that entry");
	}

    }

    public void deleteEntryPrompt( Scanner in, PrintStream out ) {

	out.println();
	out.print("Delete which entry: ");
	int delete = in.nextInt();

	out.println();

	if ( passwordList.deleteEntry(delete) ) {

	    out.println("Entry was deleted");
	} else {

	    out.println("Sorry, could not delete that entry");
	}

    }

    public void displayPasswordList( PrintStream out ) {

	if ( passwordList.size() > 0 ) {

	    out.println();
	    out.println("Showing password list");
	    out.println();

	    Entry entry;
	    int count = 0;
	    while ( count < passwordList.size() ) {
		count++;
		entry = passwordList.getEntry( count );
		out.println( count+": "+entry.getWebsite()+"\t\t"+entry.getUserName() );
	    }

	    out.println();

	} else {

	    out.println();
	    out.println("No passwords have been entered yet!");
	    out.println();

	}
    }

    public void displayPassword( Scanner in, PrintStream out ) {

        out.println();
        out.print("View password for which entry?: ");
        
        int index = in.nextInt();

        out.println();

        try {
            Entry entry = passwordList.getEntry( index );
            out.println("Password is: " + entry.getPassword() );
        } catch( Exception e ) {
            out.println( e.getMessage() );
        }

        out.println();

    }

    public void copyPasswordToClipboard( Scanner in, PrintStream out ) {

        out.println();
        out.print("Copy which password to clipboard?: ");
        int index = in.nextInt();

        out.println();

        try {
            Entry entry = passwordList.getEntry(index);
            StringSelection stringSelection = new StringSelection( entry.getPassword() );
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            out.println("Successfully copied to clipboard.");
        } catch( Exception e ) {
            out.println( e.getMessage() );
        }

        out.println();

    }

}
