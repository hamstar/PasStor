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
 * @author mcleodr
 */
public class EncryptorTest {

    static final String LS = System.getProperty("line.separator");

    public EncryptorTest() {
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
     * Test of encrypt method, of class Encryptor.
     */
    @Test
    public void testEncrypt() {
        String str = "Words of text";
        String result = Encryptor.encrypt(str);
        assertFalse(str.equals(result));
    }

    /**
     * Test of decrypt method, of class Encryptor.
     */
    @Test
    public void testDecrypt() {
        String expResult = "Blah Blah Blah";
        String str = Encryptor.encrypt(expResult);

        String result = Encryptor.decrypt(str);

        assertEquals( expResult, result );
    }

    @Test
    public void testDecryptWithNewlines() {
        String expResult = "Blah"+LS+"Blah"+LS+"Blah";
        String str = Encryptor.encrypt(expResult);

        String result = Encryptor.decrypt(str);

        assertEquals( expResult, result );
    }

    @Test
    public void testDecryptWithVeryLongString() {
        String expResult = "Blah"+LS+"Blah"+LS+"Blah asdasda asd asd as dasd as "
                +"saaaaaaaaaaas4u1987 9019 )(&)@#()!@#)!@*# 09&9092@(#)(  sjdhasdjh "
                +LS+"dada464 64 654 6a5s4das4da 4  *79* 9*&(*798^98^(*6*%5746$#^"
                +LS+"$3^ *5 8&^"+LS+LS+LS+"jdaoshd 07)(& )(&0(&()&)(*SDSDASJHlkh !@#$";
        String str = Encryptor.encrypt(expResult);

        String result = Encryptor.decrypt(str);

        assertEquals( expResult, result );
    }

}