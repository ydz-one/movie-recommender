import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class reads in a file of movies.
 * It provides methods to parse and clean the file
 * into a collection of Movie objects to be stored in DataManager.
 *
 */
public class MovieDataParser {
	private String fileName;
	
	/**
	 * Constructor method.
	 * @param file (the file to read)
	 */
	public MovieDataParser(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * This method reads in the file
	 */
	public HashMap<Integer, Movie> parseMovies() {
		HashMap<Integer, Movie> allMovies = new HashMap<>();
		try {
			File inputFile = new File(fileName);
			Scanner in = new Scanner(inputFile);
			
			while (in.hasNextLine()) {
				String line = in.nextLine();
				//split the line of movie info and store them in an array
				String[] movieInfo = line.split("::");
				
				//create new movie object (index 0 is movie id, index 1 is title)
				Movie movie = new Movie(Integer.parseInt(movieInfo[0]), movieInfo[1]);
				
				//add the state to the allStates HashMap (HashMap will prevent repeats)
				allMovies.put(movie.getId(), movie);
			}
			
			in.close();
			
		} catch (FileNotFoundException fnfe) {
			System.out.println("ERROR: Specified file not found.");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Something went wrong when reading your file.");
			System.out.println("Please make sure it is a relevant file of movies.");
		}
		
		return allMovies;
	}
	
}
