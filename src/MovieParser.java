import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * This is the abstract movie parser class.
 *
 */
public abstract class MovieParser {
	public abstract void setFileName(String fileName);
	public abstract HashMap<Integer, Movie> parseMovies() throws FileNotFoundException;
}
