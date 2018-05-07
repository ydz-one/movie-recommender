import java.io.FileNotFoundException;
import java.util.Map;

/**
 * This class handles the storage and processing of the data
 * parsed from MovieDataParser and RatingDataParser and
 * provides access to them for other classes.
 *
 */
public class DataManager {
	private MovieParserFactory moviePF;
	private MovieParser movieParser;
	private RatingParserFactory ratingPF;
	private RatingParser ratingParser;
	private Map<Integer, User> users;
	private Map<Integer, Movie> movies;
	
	/**
	 * Constructor method
	 * @param movieDatafile
	 * @param ratingDataFile
	 * @throws FileNotFoundException 
	 */
	public DataManager(String movieDataFile, String ratingDataFile) throws FileNotFoundException {
		moviePF = new MovieParserFactory();
		movieParser = moviePF.getMovieParser("dat");
		movieParser.setFileName(movieDataFile);
		movies = movieParser.parseMovies();
		ratingPF = new RatingParserFactory();
		ratingParser = ratingPF.getRatingParser("dat");
		ratingParser.setFileName(ratingDataFile);
		users = ratingParser.parseRatings(movies);
	}

	/**
	 * Getter for users HashMap
	 * @return
	 */
	public Map<Integer, User> getUsers() {
		return users;
	}

	/**
	 * Getter for movies HashMap
	 * @return
	 */
	public Map<Integer, Movie> getMovies() {
		return movies;
	}
}