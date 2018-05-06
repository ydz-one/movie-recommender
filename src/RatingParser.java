import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * This is the abstract rating parser class.
 *
 */
public abstract class RatingParser {
	public abstract HashMap<Integer, User> parseRatings(HashMap<Integer, Movie> allMovies) throws FileNotFoundException;
}