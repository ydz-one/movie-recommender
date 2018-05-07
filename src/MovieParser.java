import java.io.FileNotFoundException;
import java.util.Map;

/**
 * This is the abstract movie parser class.
 *
 */
public abstract class MovieParser {
	public abstract void setFileName(String fileName);
	public abstract Map<Integer, Movie> parseMovies() throws FileNotFoundException;
}
