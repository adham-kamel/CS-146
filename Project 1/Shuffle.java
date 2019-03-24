package sjsu.kamel.cs146.project1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Class to shuffle and input a playlist into a new file from the given playlist file
 * @author adham_kamel
 */
public class Shuffle
{
	public String[] originalOrder;		// array of strings for original order of songs
    private Random r = new Random();	// randomizer
	private String song;			// name of the given song
    int i;								// array iterator
    
    public Shuffle()
    {
 	   originalOrder = new String[458];
 	   song = "";
 	   i = 0;
    }
    
	/**
	 * Gets a file and takes each line and puts it into an array of strings.
	 * @return array with all the files
	 * @throws IOException 
	 */
   public String[] addToArray() throws IOException 
   {
	   BufferedReader in = new BufferedReader (new FileReader("Playlist.txt"));		// input stream that reads in info from given playlist file
	   
	   while((song = in.readLine()) != null)
	   {
		   originalOrder[i] = song;
		   i++;
	   }
	   
	   in.close();
	   
	   return originalOrder;
   }
   
   /**
    * takes array of strings and shuffles the songs via the Fisher-Yates shuffle algorithm.
    * @throws IOException if file cannot be created
    */
   public void shuffle() throws IOException
   {
       
	   r.setSeed(20);
       
       /*
        * Shuffle the array according to the Fisher Yates algorithm. 
        */
       for(int i = originalOrder.length - 1; i >= 1; i--)
       {
    	   int index = r.nextInt(i);
    	   String temp = originalOrder[index]; // temp variable of the current song so I can switch the two songs
    	   originalOrder[index] = originalOrder[i];
    	   originalOrder[i] = temp;
       }
       
       BufferedWriter recordedFile = null;	// A stream to record the shuffled playlist 
       
       recordedFile = new BufferedWriter(new FileWriter("PLaylist.txt"));
   
       for(int i = 0; i < originalOrder.length; i++)
       {
    	   recordedFile.write(originalOrder[i]);
    	   recordedFile.newLine();
       }
       
       recordedFile.close();
   }

 
}
