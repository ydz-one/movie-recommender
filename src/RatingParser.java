import java.io.FileNotFoundException;
import java.util.Map;

/**
 * This is the abstract rating parser class.
 *
 */
public abstract class RatingParser {
	public abstract void setFileName(String fileName);
	public abstract Map<Integer, User> parseRatings(Map<Integer, Movie> allMovies) throws FileNotFoundException;
}