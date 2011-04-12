/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package passtor;

/**
 *
 * @author http://www.ecestudents.ul.ie/Course_Pages/Btech_ITT/Modules/ET4263/More%20Samples/CEncrypt.java.html
 */
public class Encryptor {
    static final String key = "Encrypt"; // The key for 'encrypting' and 'decrypting'.

   public static String encrypt(String str)
   {
      StringBuilder sb = new StringBuilder(str);

      int lenStr = str.length();
      int lenKey = key.length();

      //
      // For each character in our string, encrypt it...
      for ( int i = 0, j = 0; i < lenStr; i++, j++ )
      {
         if ( j >= lenKey ) j = 0;  // Wrap 'round to beginning of key string.

         //
         // XOR the chars together. Must cast back to char to avoid compile error.
         //
         sb.setCharAt(i, (char)(str.charAt(i) ^ key.charAt(j)));
      }

      return sb.toString();
   }

   public static String decrypt(String str)
   {
      //
      // To 'decrypt' the string, simply apply the same technique.
      return encrypt(str);
   }

}
