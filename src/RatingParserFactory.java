/**
 * This is the factory class for a rating parser.
 * It gets the appropriate rating parser depending on the file type.
 * For the assignment, we only have a parser for a "dat" file, but
 * our architecture is set up to incorporate other types of parsers.
 *
 */
public class RatingParserFactory {
	public RatingParser getRatingParser(String fileType) {
		if (fileType.equalsIgnoreCase("dat")) {
			return new RatingDatParser();
		}
		
		return null;
	}
}
