import java.util.HashMap;

/**
 * This class handles the storage and processing of the data
 * parsed from MovieDataParser and RatingDataParser and
 * provides access to them for other classes.
 *
 */
public class DataManager {
	private MovieDatParser movieDP;
	private RatingDatParser ratingDP;
	private HashMap<Integer, User> users;
	private HashMap<Integer, Movie> movies;
	
	/**
	 * Constructor method
	 * @param movieDatafile
	 * @param ratingDataFile
	 */
	public DataManager(String movieDatafile, String ratingDataFile) {
		movieDP = new MovieDatParser();
		movieDP.setFileName(movieDatafile);
		movies = movieDP.parseMovies();
		ratingDP = new RatingDatParser();
		ratingDP.setFileName(ratingDataFile);
		users = ratingDP.parseRatings(movies);
	}

	/**
	 * Getter for users HashMap
	 * @return
	 */
	public HashMap<Integer, User> getUsers() {
		return users;
	}

	/**
	 * Getter for movies HashMap
	 * @return
	 */
	public HashMap<Integer, Movie> getMovies() {
		return movies;
	}
	
	
	
}