
package passtor;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Main class
 * @author Robert McLeod
 * @since 11 March 2011
 * @version Sprint1
 */
public class Main {

    private String unauthenticated_user;
    private String unauthenticated_password;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
	System.out.println("Hello World");
    }

    public Main()
    {
    }

    /**
     * Takes commands and sends them to the correct function
     * @param in
     * @param out
     * @param passtor
     */
    public void commandPrompt( Scanner in, PrintStream out, PasStor passtor )
    {
	out.print("Enter command: ");
	String cmd = in.next();

	if ( cmd.toLowerCase().equals("l") ) {
	    printPasswordList( out, passtor );
	} else if ( cmd.startsWith("v") ) {
	    int index = Integer.parseInt(cmd.substring(1));
	    printPasswordForEntry( index, out, passtor );
	} else if ( cmd.startsWith("n") ) {
	    addEntry( in, out, passtor);
	}
	
    }

    /**
     * Prompts for the username
     * @param in
     * @param out
     */
    public void prompt4UserName( Scanner in, PrintStream out )
    {
	out.println("Please enter your username:");
	unauthenticated_user = in.next();
    }

    /**
     * Prompts for the password
     * @param in
     * @param out
     */
    public void prompt4Password( Scanner in, PrintStream out ) {
	out.println("Please enter your password:");
	unauthenticated_password = in.next();
    }

    /**
     * Logs user in
     * @param out
     * @param passtor
     */
    public void doUserLogin( PrintStream out, PasStor passtor ) {
	Boolean nogood = false;

	if ( unauthenticated_user == null && unauthenticated_password == null) {
	    nogood = true;
	}

	passtor.UserLogin( unauthenticated_user, unauthenticated_password );
	if ( !passtor.getCurrentUser().equals( unauthenticated_user ) ) {
	    nogood = true;
	}

	// Check if the login went well
	if ( nogood ) {
	    out.println( "Sorry, unrecognized user or password" );
	    prompt4UserName( new Scanner( passtor.getCurrentUser() ), out ); // sent back to username prompt
	} else {
	    out.println( "Welcome to PasStor, " + passtor.getCurrentUser() ); // access gained
	}
    }

    /**
     * Print the list of password entries but don't show the passwords in
     * plain text.
     * @param out
     * @param passtor
     */
    public void printPasswordList( PrintStream out, PasStor passtor )
    {
	ArrayList passwordList = passtor.getPasswordList();

	if ( passwordList.isEmpty() ) {
	    out.println("No passwords have been entered yet!");
	} else {
	    out.println("Printing passwords for " + passtor.getCurrentUser() );
	    
	    for ( int index = 0; index < passwordList.size(); index++ ) {
		Entry entry = (Entry)passwordList.get(index);
		out.println( index + ":\t" + entry.getWebsite() + "\t" + entry.getUserName() );
	    }
	}

    }

    /**
     * Print out the password in plaintext
     * @param index the entries index in the password list
     * @param out
     * @param passtor
     */
    public void printPasswordForEntry( int index, PrintStream out, PasStor passtor )
    {
	String password = (String)passtor.getEntry( index ).getPassword();
	out.println("Password: "+password);
    }

    /**
     * Adds an entry to the database
     * @param in
     * @param out
     * @param passtor
     */
    public void addEntry( Scanner in, PrintStream out, PasStor passtor )
    {
	ArrayList ArrEntry = new ArrayList();
	for ( int i = 0; i <= 50; i++ ) {
	    try {
		if ( i < 3 ) {
		    ArrEntry.add( in.next() );
		} else {
		    ArrEntry.set( 2, ArrEntry.get(2)+" "+in.next() ); // make sure passwords with spaces are saved
		}
	    } catch ( java.util.NoSuchElementException e ) {
		break;
	    }
	}

	if ( ArrEntry.size() < 3 ) {
	    out.println("You forgot to enter a field!");
	} else if ( ArrEntry.size() == 3 ) {

	    if ( passtor.addEntry( new Entry( ArrEntry ) ) ) {
		out.println("Entry Saved");
	    } else {
		out.println("Entry not saved");
	    }
	}
    }

}
