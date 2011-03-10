
package passtor;
import java.util.ArrayList;
import java.io.*;

/**
 * PasStor the password storage
 * @author Robert McLeod
 * @since 11 March 2011
 * @version Sprint1
 */
public class PasStor {

    static final String GOODUSER = "Heman";
    static final String BADUSER = "Skeletor";
    static final String LISTDB = "list.db";

    private String currentUser;
    private ArrayList passwordList;

    public PasStor() {
	currentUser = "nobody";
	passwordList = new ArrayList();
    }

    /**
     * Tries to log in the user
     * @param user
     * @param password
     */
    public void UserLogin( String user, String password )
    {
	if ( user.equals( GOODUSER ) && password.equals("password") ) {
	    currentUser = user;
	} else {
	    currentUser = "nobody";
	}
    }

    /**
     * Returns the user that is currently logged in
     * @return the currently logged in user
     */
    public String getCurrentUser()
    {
	return currentUser;
    }

    /**
     * Adds an entry to the database
     * @param entry The entry to add
     * @return true if the addition succeeded
     */
    public boolean addEntry( Entry entry )
    {
	Boolean allgood = false;
	allgood = passwordList.add( entry );

	try
	{
	    FileWriter fstream = new FileWriter(PasStor.LISTDB, true);
	    BufferedWriter out = new BufferedWriter(fstream);
	    out.write(entry.getWebsite() + "," +entry.getUserName() + "," + entry.getPassword());
	    out.newLine();
	    out.close();
	}
	catch ( Exception e )
	{
	    System.err.println(e.toString());
	    allgood = false;
	}

	return allgood;
    }

    /**
     * Returns the password list
     * @return the password list
     */
    public ArrayList getPasswordList()
    {
	return passwordList;
    }

    /**
     * Returns the entry as specified by the
     * @param index
     * @return
     */
    public Entry getEntry( int index )
    {
	return (Entry)passwordList.get( index );
    }

    /**
     * Flushes all the passwords from the list
     */
    public void flushList() {
	passwordList = new ArrayList();
	File listDb = new File(PasStor.LISTDB);
	listDb.delete();
    }

}
