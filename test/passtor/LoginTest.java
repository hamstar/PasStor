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
public class LoginTest {

    public LoginTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // AT 1.1
    @Test
    public void testGoodUserCanLogin() {

	assertTrue( Login.validate( "Heman", "password") );
    }

    // AT1.2
    @Test
    public void testBadUserCantLogin() {

	assertFalse( Login.validate( "Skeletor", "password" ) );
    }

}