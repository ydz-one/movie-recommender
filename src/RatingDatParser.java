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
public class RatingDatParser extends RatingParser {
	private String fileName;
		
	/**
	 * Constructor method.
	 */
	public RatingDatParser() {
	}
	
	/**
	 * Setter for specifying fileName to read
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * This method reads in the file
	 */
	public HashMap<Integer, User> parseRatings(HashMap<Integer, Movie> allMovies) {
		HashMap<Integer, User> allUsers = new HashMap<>();
		try {
			File inputFile = new File(fileName);
			Scanner in = new Scanner(inputFile);
			
			while (in.hasNextLine()) {
				String line = in.nextLine();
				//split the line of movie info and store them in an array
				String[] ratingInfo = line.split("::");
				
				int userID = Integer.parseInt(ratingInfo[0]);
				int movieID = Integer.parseInt(ratingInfo[1]);
				double ratingScore = Double.parseDouble(ratingInfo[2]);
				
				if (!allUsers.containsKey(userID)) {
					User user = new User(userID, new HashMap<>());
					allUsers.put(userID, user);
				}
				allUsers.get(userID).addRating(movieID, ratingScore);
				

				if (!allMovies.containsKey(movieID)) {
					System.out.println("a new movie");
					Movie movie = new Movie(movieID, "unknown title");
					allMovies.put(movieID, movie);
				}
				allMovies.get(movieID).addRating(userID, ratingScore);
			}
			
			in.close();
			
		} catch (FileNotFoundException fnfe) {
			System.out.println("ERROR: Specified file not found.");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Something went wrong when reading your file.");
			System.out.println("Please make sure it is a relevant file of movies.");
		}
		
		return allUsers;
	}
	
}
