import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class reads in a file of movies.
 * It provides methods to parse and clean the file
 * into a collection of Movie objects to be stored in DataManager.
 *
 */
public class RatingDatParser extends RatingParser {

	/**
	 * Constructor method.
	 */
	public RatingDatParser() {
		fileName = "";
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
	public Map<Integer, User> parseRatings(Map<Integer, Movie> allMovies) throws FileNotFoundException {
		Map<Integer, User> allUsers = new HashMap<>();
		File inputFile = new File(fileName);
		Scanner in = new Scanner(inputFile);

		while (in.hasNextLine()) {
			String line = in.nextLine();
			// split the line of movie info and store them in an array
			String[] ratingInfo = line.split("::");

			int userID = Integer.parseInt(ratingInfo[0]);
			int movieID = Integer.parseInt(ratingInfo[1]);
			double ratingScore = Double.parseDouble(ratingInfo[2]);

			// populate allUsers map with key: userID, value: User obj
			if (!allUsers.containsKey(userID)) {
				User user = new User(userID);
				allUsers.put(userID, user);
			}
			allUsers.get(userID).addRating(movieID, ratingScore);

			// just in case, if there is a movie in ratings file not listed in movies file
			if (!allMovies.containsKey(movieID)) {
				Movie movie = new Movie(movieID, "unknown title");
				allMovies.put(movieID, movie);
			}
			allMovies.get(movieID).addRating(userID, ratingScore);
		}

		in.close();
		return allUsers;
	}

}
