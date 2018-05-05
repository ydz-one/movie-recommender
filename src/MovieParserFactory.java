/**
 * This is the factory class for a movie parser.
 * It gets the appropriate movie parser depending on the file type.
 * For the assignment, we only have a parser for a "dat" file, but
 * our architecture is set up to incorporate other types of parsers.
 *
 */
public class MovieParserFactory {
	public MovieParser getMovieParser(String fileType) {
		if (fileType.equalsIgnoreCase("dat")) {
			return new MovieDatParser();
		}
		
		return null;
	}
}
