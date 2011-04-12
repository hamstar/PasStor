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

import java.util.ArrayList;

/**
 *
 * @author mcleodr
 */
public class UtilTest {

    static final String LS = System.getProperty("line.separator");

    public UtilTest() {
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

    /**
     * Test of encrypt_and_put method, of class Util.
     */
    @Test
    public void testEncrypt_and_put() {
        String filename = "test.txt";
        String contents = "example.com,Heman,password"+LS+"example.com,Heman,password"+LS+"example.com,Heman,password";
        Util.encrypt_and_put(filename, contents);

        assertEquals( contents, Util.get_and_decrypt(filename) );
    }

    @Test
    public void testGet_and_decrypt() {
        String filename = "test.txt";
        String expResult = "Blah blah blah";
        
        Util.encrypt_and_put(filename, expResult);
        
        assertEquals( expResult, Util.get_and_decrypt(filename));
    }

    @Test
    public void testFile_get_contents() {
        String filename = "test.txt";
        String contents = Util.file_get_contents(filename);
        System.out.println( Encryptor.decrypt( contents ) );
    }

    @Test
    public void testGetDataFromFileIsTheSame() {

        String filename = "test.txt";
        String contents = "Blah blah blah";
        Util.file_put_contents( filename, contents );
        
        assertEquals( contents, Util.file_get_contents( filename ) );

    }

    @Test
    public void testExplodeReturnsFullKeys() {
        String contents = ""+LS+LS+"Some actual words"+LS;

        ArrayList<String> list = Util.explode(LS, contents);

        assertEquals( 1, list.size() );
    }

    @Test
    public void testEncrypt_and_append() {

	String filename = "test.txt";
	Util.encrypt_and_put(filename, "Some text");

	Util.encrypt_and_append(filename, "Some test text to encrypt");
	String contents = Util.get_and_decrypt(filename);

	assertEquals( "Some textSome test text to encrypt", contents );

    }

}