import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * This class contains the main.
 * It handles retrieving input from the user and
 * providing results to the user via console.
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

		try (Scanner in = new Scanner(System.in)) {
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
				logger.writeToFile(input);
				
				if (input.equals("quit")) {
					break;
				} else if (!input.equals("1") && !input.equals("2")) {
					System.out.println("Invalid input, please try again\n");
					continue;
				}
				
				if (input.equals("1")) {
					System.out.println("Type in user ID:");
					String userID = in.nextLine();
					logger.writeToFile(userID);
					
					// keep prompting user for response if user ID is not numerical
					while(!userID.matches("\\d+")) {
						System.out.println("User ID must be a positive integer. Please try again.");
						userID = in.nextLine();
						logger.writeToFile(userID);
					}
					
					System.out.println("Type in movie ID:");
					String movieID = in.nextLine();
					logger.writeToFile(movieID);
					
					// keep prompting user for response if movie ID is not numerical
					while(!movieID.matches("\\d+")) {
						System.out.println("Movie ID must be a positive integer. Please try again.");
						movieID = in.nextLine();
						logger.writeToFile(movieID);
					}
					
					// convert strings to integers
					int userIDInt = Integer.parseInt(userID);
					int movieIDInt = Integer.parseInt(movieID);
					
					try {
						double result = recommender.predictPreference(userIDInt, movieIDInt);
						
						if (result == Double.MIN_VALUE) {
							System.out.println("No rating data available for this movie. Please try again.");
							continue;
						}
						
						System.out.println("The predicted movie preference rating for user #"
							+ userID + " and movie #" + movieID + " is: "
							+ result + "\n");
					} catch (IllegalStateException ise) {
						System.out.println("Unfortunately, movie preference prediction not possible for this user.");
						System.out.println("This is likely due to the user rating all movies to be the same score.");
						System.out.println("Please try again.\n");
						continue;
					} catch (IllegalArgumentException iae) {
						System.out.println("User has already rated movie, please pick another one.\n");
						continue;
					}
				} else {
					System.out.println("Type in user ID:");
					String userID = in.nextLine();
					logger.writeToFile(userID);
					
					// keep prompting user for response if user ID is not numerical
					while(!userID.matches("\\d+")) {
						System.out.println("User ID must be a positive integer. Please try again.");
						userID = in.nextLine();
						logger.writeToFile(userID);
					}
					
					System.out.println("Type in threshold:");
					String threshold = in.nextLine();
					logger.writeToFile(threshold);
					
					// keep prompting user for response if movie ID is not numerical
					while(!threshold.matches("\\d+")) {
						System.out.println("Threshold must be a positive integer. Please try again.");
						threshold = in.nextLine();
						logger.writeToFile(threshold);
					}
					
					// convert strings to integers
					int userIDInt = Integer.parseInt(userID);
					int thresholdInt = Integer.parseInt(threshold);
					
					// catch IllegalStateException for when user preference data not possible
					try {
						Map<Integer, Double> recommended = recommender.recommendMovies(userIDInt, thresholdInt);

						// print results
						System.out.println("Here are the recommended movies for user #" + userID + ":");
						
						for (Entry<Integer, Double> movie : recommended.entrySet()) {
							System.out.print("Movie #" + movie.getKey() + ", ");
							System.out.println("Predicted Rating: " + movie.getValue() + "\n");
						}
					} catch (IllegalStateException ise) {
						System.out.println("Unfortunately, movie recommendation not possible for this user.");
						System.out.println("This is likely due to the user rating all movies to be the same score.");
						System.out.println("Please try again.\n");
						continue;
					}
					
					// prompt user to try again or quit
					System.out.println("Would you like to try eagain? (y/n)");
					
					String response = in.nextLine();
					logger.writeToFile(response);
					
					while (!response.equals("y") && !response.equals("n")) {
						System.out.println("Please type only y or n");
						response = in.nextLine();
						logger.writeToFile(response);
					}
					
					if (response.equals("n")) {
						break;
					}
				}
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}