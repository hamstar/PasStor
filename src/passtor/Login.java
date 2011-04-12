/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package passtor;

/**
 *
 * @author chumba
 */
public class Login {

    public static final String USERDB = "users.db";

    public static boolean validate( String user, String password ) {

	boolean validated = false;

	if ( user.trim().equals("Heman") && password.trim().equals("password") ) {
	    validated = true;
	}

	return validated;
    }

}
