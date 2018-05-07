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
public class MovieDatParser extends MovieParser {
	private String fileName;

	/**
	 * Constructor method.
	 */
	public MovieDatParser() {
	}

	/**
	 * Setter for specifying fileName to read
	 * @param fileName
	 */
	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * This method reads in the file
	 * @throws FileNotFoundException 
	 */
	@Override
	public HashMap<Integer, Movie> parseMovies() throws FileNotFoundException {
		HashMap<Integer, Movie> allMovies = new HashMap<>();
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
		return allMovies;
	}

}
