/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package passtor;
import java.util.ArrayList;

/**
 * Model for an Entry in the PasStor list
 * @author Robert McLeod
 * @since 11 March 2011
 * @version Sprint1
 */
public class Entry {

    private String website;
    private String userName;
    private String password;

    public Entry() {
	
    }

    /**
     * Takes an ArrayList of exactly three elements and assigns them to local
     * variables in the order of website, userName, password
     * @param entry
     */
    public Entry(ArrayList entry)
    {
	if ( entry.size() == 3 ) {
	    setWebsite((String)entry.get(0));
	    setUserName((String)entry.get(1));
	    setPassword((String)entry.get(2));
	} else {
	    System.err.println("Array was given that was not equal to three, empty Entry created");
	}
    }

    /**
     * Assigns the given parameters as local variables
     * @param _website
     * @param _userName
     * @param _password
     */
    public Entry( String _website, String _userName, String _password ) {
	setWebsite(_website);
	setUserName(_userName);
	setPassword(_password);
    }

    /**
     * Returns the website name
     * @return
     */
    public String getWebsite() {
	return this.website;
    }

    /**
     * Returns the username
     * @return
     */
    public String getUserName() {
	return this.userName;
    }

    /**
     * Returns the password
     * @return
     */
    public String getPassword() {
	return this.password;
    }

    /**
     * Sets the username
     * @param _userName
     * @return this object
     */
    public Entry setUserName(String _userName) {
	userName = _userName;
	return this;
    }

    /**
     * Sets the website
     * @param _website
     * @return this object
     */
    public Entry setWebsite(String _website) {
	website = _website;
	return this;
    }

    /**
     * Sets the password
     * @param _password
     * @return this object
     */
    public Entry setPassword(String _password) {
	password = _password;
	return this;
    }

}
