/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package passtor;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author mcleodr
 */
public class Util {

    static final String LS = System.getProperty("line.separator");

    public static void check_filename( String filename ) {
	if ( filename.trim().isEmpty() || filename == null ) {
	    throw new IllegalArgumentException("Filename is not set properly");
	}
    }

    public static String file_get_contents( String filename ) {

	Util.check_filename( filename );

        String contents = "";

        try {
            contents = new Scanner( new FileInputStream( filename) ).useDelimiter("\\Z").next();
        } catch ( FileNotFoundException e ) {
            System.out.println( "Couldn't find file "+filename );
            System.out.println( "\t" +e.toString() );
        } catch ( java.util.NoSuchElementException e ) {
	    // File was empty
	    contents = "";
	}

        return contents;
    }

    public static void file_put_contents( String filename, String contents ) {

	Util.check_filename( filename );

        try {
            BufferedWriter out = new BufferedWriter( new FileWriter( filename ) );
            out.write( contents );
            //if ( contents != null ) out.newLine();
            out.close();
        } catch ( Exception e ) {
	    System.out.println( e.toString() );
        }
    }

    public static String get_and_decrypt( String filename ) {

	Util.check_filename( filename );

        String contents = Util.file_get_contents( filename );
        return Encryptor.decrypt(contents);
    }

    public static void encrypt_and_put( String filename, String contents ) {

	Util.check_filename( filename );

        contents = Encryptor.encrypt(contents);
        Util.file_put_contents( filename, contents );
    }

    public static void encrypt_and_append( String filename, String contents ) {

	Util.check_filename( filename );

        String fileContents = Util.get_and_decrypt( filename );
        fileContents += contents;
        Util.encrypt_and_put(filename, fileContents);
    }

    public static ArrayList<String> explode( String delimeter, String contents ) {

        ArrayList<String> lines;

        lines = new ArrayList( Arrays.asList( contents.split( delimeter ) ) );
        ArrayList<String> fullLines = new ArrayList();

        for ( String line: lines ) {
            if ( line.isEmpty() || line == null || line.equals("") ) {
                continue;
            }
	    fullLines.add( line );
        }

        return fullLines;
    }

}
