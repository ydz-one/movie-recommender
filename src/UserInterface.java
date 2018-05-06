import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * This class contains the main.
 * It handles retrieving input from the user and
 * providing results to the user via the console.
 *
 */
public class UserInterface {

	public static void main(String[] args) {
		// initialize inputs
		String loggerFilename = "logger.txt";
		String movieFilename = "movies.dat";
		String ratingFilename = "ratings.dat";
		SimilarityAlgorithm pearson = new PearsonCorrelation();
		int neighborhoodSize = 20;

		try (Scanner in = new Scanner(System.in)){
			// set up logger
			Logger logger = Logger.getInstance();
			logger.setOutputFile(loggerFilename);
			
			// set up recommender
			System.out.println("Importing data...");
			Recommender recommender = new Recommender(movieFilename, ratingFilename,
					pearson, neighborhoodSize);
			
			while(true) {
				System.out.println("Type \"1\" for movie preference prediction.");
				System.out.println("Type \"2\" for movie recommendation.");
				System.out.println("Type \"quit\" to quit.");
				
				String input = in.nextLine();
				
				if (input.equals("quit") || input.equals("quit\n")) {
					break;
				} else if (!input.equals("1") && !input.equals("2")) {
					System.out.println("Invalid input, please try again\n");
					continue;
				}
				
				if (input.equals("1")) {
					System.out.println("Type in user ID:");
					String userID = in.nextLine();
					
					// keep prompting user for response if user ID is not numerical
					while(!userID.matches("\\d+")) {
						System.out.println("User ID must be a positive integer. Please try again.");
						userID = in.nextLine();
					}
					
					System.out.println("Type in movie ID:");
					String movieID = in.nextLine();
					
					// keep prompting user for response if movie ID is not numerical
					while(!movieID.matches("\\d+")) {
						System.out.println("Movie ID must be a positive integer. Please try again.");
						movieID = in.nextLine();
					}
					
					// convert strings to integers
					int userIDInt = Integer.parseInt(userID);
					int movieIDInt = Integer.parseInt(movieID);
					
					System.out.println("The predicted movie preference rating for user #"
							+ userID + " and movie #" + movieID + " is: "
							+ recommender.predictPreference(userIDInt, movieIDInt));
				} else {
					System.out.println("Type in user ID:");
					String userID = in.nextLine();
					
					// keep prompting user for response if user ID is not numerical
					while(!userID.matches("\\d+")) {
						System.out.println("User ID must be a positive integer. Please try again.");
						userID = in.nextLine();
					}
					
					System.out.println("Type in threshold:");
					String threshold = in.nextLine();
					
					// keep prompting user for response if movie ID is not numerical
					while(!threshold.matches("\\d+")) {
						System.out.println("Threshold must be a positive integer. Please try again.");
						threshold = in.nextLine();
					}
					
					// convert strings to integers
					int userIDInt = Integer.parseInt(userID);
					int thresholdInt = Integer.parseInt(threshold);
					
					Map<Integer, Double> recommended = recommender.recommendMovies(userIDInt, thresholdInt);
					
					// print results
					System.out.println("Here are the recommended movies for user #" + userID + ":");
					
					for (Entry<Integer, Double> movie : recommended.entrySet()) {
						System.out.print("Movie #" + movie.getKey() + ", ");
						System.out.println("Predicted Rating: " + movie.getValue());
					}
				}
			}
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (IllegalStateException ise) {
			ise.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}