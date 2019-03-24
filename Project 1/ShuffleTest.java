
package sjsu.kamel.cs146.project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 *  Takes the original list and compares it with the shuffled list and the target 
 *  file to see if it was shuffled correctly.
 * @author adham_kamel
 */
public class ShuffleTest
{
 
   Shuffle shuffleTester;									// creates instances from the shuffle class 
   String expected;											// Expected output for the @Test portions
   String[] originalList = new String[458];					// Array that holds lines of original playlist

   /**
    * Creates the instance of shuffle tester
    * @throws Exception
    */
   @Before
   public void setUp() throws Exception
   {
      shuffleTester = new Shuffle();
   }

   /**
    * Sets and checks if the expected line is equal to the actual via a buffered reader.
    * @throws IOException
    */
   @Test
   public void testSongPlaylist() throws IOException
   {
	  int i = 0;
      BufferedReader in = new BufferedReader (new FileReader ("Playlist.txt"));	 // Reads the input from the given file
      shuffleTester.addToArray();
      
      while ((expected = in.readLine()) != null) 
      {
         String actualLine = shuffleTester.originalOrder[i];// Expected output for this iteration of loop
         assertEquals(expected, actualLine);
         i++;
      }
      in.close();
   }

   /**
    * Checks to see if the shuffled playlist is the same to the expected line
    * after two buffered readers to check both the in and the out streams of the file
    * @throws IOException
    */
   @Test
   public void testShuffle() throws IOException
   {
	   shuffleTester.addToArray();
	   shuffleTester.shuffle();
	   BufferedReader out = new BufferedReader (new FileReader ("KamelAdhamPlaylist.txt"));		// reads output from shuffled file
	   BufferedReader in = new BufferedReader (new FileReader ("Target2.txt"));		// takes input from the file to check with the Shuffled file

	   while ((expected = in.readLine()) != null) 
	   {     
		   String actualLine = out.readLine();		// Saves the line bing checked for this iteration of the loop
		   System.out.println(expected);
		   System.out.println(actualLine);
		   assertEquals(expected, actualLine);    
	   }
	   out.close();
	   in.close();
   }
   
}