/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package passtor;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author chumba
 */
public class PasswordList {

    static final String LS = System.getProperty("line.separator");
    private static final String LISTDB = "list.db";
    private String user;
    private String passwordFile;

    PasswordList( String user ) {

        this.user = user;
	this.passwordFile = user + "." + LISTDB;
    }

    public int size() {

	ArrayList listdb = new ArrayList();

	if ( !Util.file_get_contents( passwordFile ).trim().isEmpty() ) {
	    listdb = Util.explode(LS, Util.get_and_decrypt( passwordFile ) );
	}

	return listdb.size();
    }

    /**
     * Flushes all the passwords from the list
     */
    public void flush() {

        Util.file_put_contents( passwordFile, "");

    }

    /**
     * Adds an entry to the database
     * @param entry The entry to add
     * @return true if the addition succeeded
     */
    public boolean addEntry( Entry entry ) {
        boolean saved = false;

        try {
            Util.encrypt_and_append( passwordFile, entry.toString()+LS );
            saved = true;
        } catch( Exception e ) {
            saved = false;
        }

        return saved;
    }

    /**
     * Returns the entry as specified by the
     * @param index
     * @return
     */
    public Entry getEntry( int index )
    {

	if ( index <= 0 || index > this.size() )
	    throw new IllegalArgumentException("Cannot find entry "+index);

        Entry entry = null;
        try {
            String contents = Util.get_and_decrypt( passwordFile );
            entry = new Entry( Util.explode(LS, contents).get( index-1 ) );
        } catch ( Exception e ) {
	    System.out.println( e.toString() );
        }

        if ( entry == null ) {
            throw new IndexOutOfBoundsException("Cannot find entry "+index);
        }

	return entry;

    }

    public boolean deleteEntry( int index ) {

	boolean deleted = false;

	if ( index <= 0 || index > size() )
	    throw new IllegalArgumentException("Cannot find entry "+index);

	try {
	    String contents = Util.get_and_decrypt(passwordFile);
	    ArrayList<String> entries = Util.explode( LS, contents );
	    String newContents = "";

	    int count = 0;
	    for ( String entry: entries ) {
		if ( count != ( index - 1 ) ) {
		    newContents += entry +LS;
		}
		count++;
	    }
	    
	    Util.encrypt_and_put(passwordFile, newContents);
	    deleted = true;

	} catch ( Exception e ) {
	    System.out.println( e.toString() );
	    deleted = false;
	}

	return deleted;

    }

}
